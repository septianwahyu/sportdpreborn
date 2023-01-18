package com.quick.sportdbreborn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.stetho.Stetho;
import com.mohamedabulgasem.loadingoverlay.LoadingAnimation;
import com.mohamedabulgasem.loadingoverlay.LoadingOverlay;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.quick.sportdbreborn.config.Config.ALL_LEAGUES;
import static com.quick.sportdbreborn.config.Config.ALL_SEASONS_IN_A_LEAGUE;
import static com.quick.sportdbreborn.config.Config.ALL_TEAMS_DETAILS_IN_A_LEAGUE;
import static com.quick.sportdbreborn.config.Config.BASE_URL;
import static com.quick.sportdbreborn.config.Config.LEAGUES_DETAILS;

public class HomeActivity extends AppCompatActivity implements RecyclerItem.ItemClickListener {

    dbHelp helper;
    RecyclerView rv_item;
    RecyclerItem adapter;
    ArrayList<DataRowAL> mleagueArrayList;
    SwipeRefreshLayout srl_refresh;
    int posisi;
    String nSearch = "";
    LoadingOverlay loadingOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        AndroidNetworking.initialize(getApplicationContext());

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build()
        );

        rv_item = (RecyclerView) findViewById(R.id.rv_item);
        srl_refresh = (SwipeRefreshLayout) findViewById(R.id.srl_refresh);

        loadingOverlay = LoadingOverlay.Companion.with(HomeActivity.this, new LoadingAnimation(R.raw.loading_sport, 300), 0.5F, false, null, null, null);

        helper = new dbHelp(this);
        helper.deleteAL();
        mleagueArrayList = getDataSet("");

        getDataAL();

        loadingOverlay.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingOverlay.dismiss();
            }
        },20000);

        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showRecycler();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(srl_refresh.isRefreshing()) {
                            srl_refresh.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });

        setRecycler();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Search League Name");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
//                searchView.getQuery(showRecyclerSearch(s));
                nSearch = searchView.getQuery().toString();
                showRecyclerSearch(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    void setRecycler(){
        adapter = new RecyclerItem(HomeActivity.this, mleagueArrayList, (RecyclerItem.ItemClickListener) HomeActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
        rv_item.hasFixedSize();
        rv_item.setLayoutManager(layoutManager);
        rv_item.setAdapter(adapter);
    }

    @Override
    public void OnItemClick(int position, View itemView) {
        String league_id, league_name, noSpaceLN;
        helper.deleteLD();
        helper.deleteLS();
        helper.deleteDT();
        if(nSearch.length() > 0){
            posisi = position;
            Log.e("CEK", "ONCLICK");
            Cursor c = helper.selectALSearch(nSearch);
            c.moveToPosition(posisi);
            league_id = c.getString(c.getColumnIndexOrThrow("ID_LEAGUE"));
            league_name = c.getString(c.getColumnIndexOrThrow("LEAGUE_NAME"));
            noSpaceLN = league_name.replaceAll(" ","%20");
            getDataLD(league_id);
            getDataLS(league_id);
            getDataATDIAL(noSpaceLN);
            loadingOverlay.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingOverlay.dismiss();
                    Intent i = new Intent(HomeActivity.this, LeagueDetailsActivity.class);
                    startActivity(i);
                }
            },30000);
        } else {
            posisi = position;
            Log.e("CEK", "ONCLICK");
            Cursor c = helper.selectAL();
            c.moveToPosition(posisi);
            league_id = c.getString(c.getColumnIndexOrThrow("ID_LEAGUE"));
            league_name = c.getString(c.getColumnIndexOrThrow("LEAGUE_NAME"));
            noSpaceLN = league_name.replaceAll(" ","%20");
            getDataLD(league_id);
            getDataLS(league_id);
            getDataATDIAL(noSpaceLN);
            loadingOverlay.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingOverlay.dismiss();
                    Intent i = new Intent(HomeActivity.this, LeagueDetailsActivity.class);
                    startActivity(i);
                }
            },30000);
        }
    }

    public void getDataAL(){
        AndroidNetworking.get(BASE_URL+ALL_LEAGUES)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray data = response.getJSONArray("leagues");
                            for (int a=0; a<data.length(); a++){
                                JSONObject c = data.getJSONObject(a);
                                String id = c.getString("idLeague");
                                ContentValues values = new ContentValues();
                                values.put("ID_LEAGUE", c.getString("idLeague"));
                                values.put("LEAGUE_NAME", c.getString("strLeague"));
                                values.put("SPORT_TYPE", c.getString("strSport"));
                                values.put("LEAGUE_ALT_NAME", c.getString("strLeagueAlternate"));

                                helper.insertAL(values);
                                showRecycler();

                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    public void getDataLD(String league_id){
        AndroidNetworking.get(BASE_URL+LEAGUES_DETAILS+"id="+league_id)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray data = response.getJSONArray("leagues");
                            for (int a=0; a<data.length(); a++){
                                JSONObject c = data.getJSONObject(a);
                                String id = c.getString("idLeague");
                                ContentValues values = new ContentValues();
                                values.put("ID_LEAGUE_D", c.getString("idLeague"));
                                values.put("SPORT_TYPE_D", c.getString("strSport"));
                                values.put("LEAGUE_NAME_D", c.getString("strLeague"));
                                values.put("LEAGUE_ALT_NAME_D", c.getString("strLeagueAlternate"));
                                values.put("CURRENT_SEASON", c.getString("strCurrentSeason"));
                                values.put("FORMED_YEAR", c.getString("intFormedYear"));
                                values.put("DATE_FIRST_EVENT", c.getString("dateFirstEvent"));
                                values.put("COUNTRY", c.getString("strCountry"));
                                values.put("WEBSITE", c.getString("strWebsite"));
                                values.put("FACEBOOK", c.getString("strFacebook"));
                                values.put("TWITTER", c.getString("strTwitter"));
                                values.put("YOUTUBE", c.getString("strYoutube"));
                                values.put("DESCRIPTION", c.getString("strDescriptionEN"));
                                values.put("BANNER", c.getString("strBanner"));
                                values.put("BADGE", c.getString("strBadge"));
                                values.put("LOGO", c.getString("strLogo"));

                                helper.insertLD(values);

                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    public void getDataATDIAL(String noSpaceLN){
        AndroidNetworking.get(BASE_URL+ALL_TEAMS_DETAILS_IN_A_LEAGUE+"l="+noSpaceLN)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray data = response.getJSONArray("teams");
                            for (int a=0; a<data.length(); a++){
                                JSONObject c = data.getJSONObject(a);
                                ContentValues values = new ContentValues();
                                values.put("ID_TEAM", c.getString("idTeam"));
                                values.put("TEAM_NAME", c.getString("strTeam"));
                                values.put("TEAM_NAME_SHORT", c.getString("strTeamShort"));
                                values.put("TEAM_ALT_NAME", c.getString("strAlternate"));
                                values.put("DT_FORMED_YEAR", c.getString("intFormedYear"));
                                values.put("DT_SPORT_TYPE", c.getString("strSport"));
                                values.put("LEAGUE_1", c.getString("strLeague"));
                                values.put("LEAGUE_2", c.getString("strLeague2"));
                                values.put("LEAGUE_3", c.getString("strLeague3"));
                                values.put("LEAGUE_4", c.getString("strLeague4"));
                                values.put("LEAGUE_5", c.getString("strLeague5"));
                                values.put("STADIUM", c.getString("strStadium"));
                                values.put("KEYWORDS", c.getString("strKeywords"));
                                values.put("RSS", c.getString("strRSS"));
                                values.put("STADIUM_THUMB", c.getString("strStadiumThumb"));
                                values.put("STADIUM_DESC", c.getString("strStadiumDescription"));
                                values.put("STADIUM_LOC", c.getString("strStadiumLocation"));
                                values.put("STADIUM_CAP", c.getString("intStadiumCapacity"));
                                values.put("DT_WEBSITE", c.getString("strWebsite"));
                                values.put("DT_FACEBOOK", c.getString("strFacebook"));
                                values.put("DT_TWITTER", c.getString("strTwitter"));
                                values.put("INSTAGRAM", c.getString("strInstagram"));
                                values.put("DESC_EN", c.getString("strDescriptionEN"));
                                values.put("DT_COUNTRY", c.getString("strCountry"));
                                values.put("TEAM_BADGE", c.getString("strTeamBadge"));
                                values.put("TEAM_JERSEY", c.getString("strTeamJersey"));
                                values.put("TEAM_BANNER", c.getString("strTeamBanner"));

                                helper.insertDT(values);

                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    public void getDataLS(String league_id){
        AndroidNetworking.get(BASE_URL+ALL_SEASONS_IN_A_LEAGUE+"id="+league_id)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray data = response.getJSONArray("seasons");
                            for (int a=0; a<data.length(); a++){
                                JSONObject c = data.getJSONObject(a);
                                ContentValues values = new ContentValues();
                                values.put("SEASON", c.getString("strSeason"));

                                helper.insertLS(values);

                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    void showRecycler() {
        mleagueArrayList.clear();
        mleagueArrayList.addAll(getDataSet(""));
        adapter.notifyDataSetChanged();
    }

    void showRecyclerSearch(String search) {
        mleagueArrayList.clear();
        mleagueArrayList.addAll(getDataSet(search));
        adapter.notifyDataSetChanged();
    }

    ArrayList<DataRowAL> getDataSet(String search) {
        ArrayList<DataRowAL> dataSet = new ArrayList<>();
        if (search.length() == 0){
            Cursor c = helper.selectAL();
            while (c.moveToNext()) {
                DataRowAL data = new DataRowAL();
                data.setData(
                        c.getString(c.getColumnIndex(helper.ID_LEAGUE)),
                        c.getString(c.getColumnIndex(helper.LEAGUE_NAME)),
                        c.getString(c.getColumnIndex(helper.SPORT_TYPE)),
                        c.getString(c.getColumnIndex(helper.LEAGUE_ALT_NAME)),
                        c.getString(c.getColumnIndex(helper._id))
                );
                dataSet.add(data);
            }
        } else if (search.length() > 0 ){
            Cursor c = helper.selectALSearch(search);
            while (c.moveToNext()) {
                DataRowAL data = new DataRowAL();
                data.setData(
                        c.getString(c.getColumnIndex(helper.ID_LEAGUE)),
                        c.getString(c.getColumnIndex(helper.LEAGUE_NAME)),
                        c.getString(c.getColumnIndex(helper.SPORT_TYPE)),
                        c.getString(c.getColumnIndex(helper.LEAGUE_ALT_NAME)),
                        c.getString(c.getColumnIndex(helper._id))
                );
                dataSet.add(data);
            }
        }
        return dataSet;
    }
}