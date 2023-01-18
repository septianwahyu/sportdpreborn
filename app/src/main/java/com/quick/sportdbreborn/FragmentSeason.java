package com.quick.sportdbreborn;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.stetho.Stetho;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.quick.sportdbreborn.config.Config.ALL_LEAGUES;
import static com.quick.sportdbreborn.config.Config.BASE_URL;
import static com.quick.sportdbreborn.config.Config.LOOKUP_TABLE;

public class FragmentSeason extends Fragment implements RecyclerItem2.ItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentSeason() {
        // Required empty public constructor
    }

    dbHelp helper;
    RecyclerView rv_item;
    RecyclerItem2 adapter;
    ArrayList<DataRowAS> mSeasonList;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSeason newInstance(String param1, String param2) {
        FragmentSeason fragment = new FragmentSeason();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_season, container, false);
        AndroidNetworking.initialize(getActivity());

        rv_item = (RecyclerView) view.findViewById(R.id.rv_item2);

        helper = new dbHelp(getActivity());

        mSeasonList = getDataSet();
        adapter = new RecyclerItem2(getActivity(), mSeasonList, (RecyclerItem2.ItemClickListener) this);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
        rv_item.hasFixedSize();
        rv_item.setLayoutManager(layoutManager);
        rv_item.setAdapter(adapter);

        showMessage();

        return view;
    }

    void showMessage() {
        mSeasonList.clear();
        mSeasonList.addAll(getDataSet());
        adapter.notifyDataSetChanged();
    }

    ArrayList<DataRowAS> getDataSet() {
        ArrayList<DataRowAS> dataSet = new ArrayList<>();

        Cursor c = helper.selectLS();
        while (c.moveToNext()) {
            DataRowAS data = new DataRowAS();
            data.setData(
                    c.getString(c.getColumnIndex(helper.SEASON)),
                    c.getString(c.getColumnIndex(helper._id))
            );
            dataSet.add(data);
        }
        return dataSet;
    }

    @Override
    public void OnItemClick(int position, View itemView) {
        String league_id = "", season = "", league_name = "";
        Cursor c;
        c = helper.selectLD();
        if (c.moveToFirst()) {
            league_id = c.getString(0);
            league_name = c.getString(2);
        }
        c = helper.selectLS();
        if (c.moveToPosition(position)) {
            season = c.getString(1);
        }
        helper.deleteSK();
        getDataSK(league_id, season);
        LeagueDetailsActivity.showLoading(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String season = "", league_name = "";
                Cursor c;
                c = helper.selectLD();
                if (c.moveToFirst()) {
                    league_name = c.getString(2);
                }
                c = helper.selectLS();
                if (c.moveToPosition(position)) {
                    season = c.getString(1);
                }
                LeagueDetailsActivity.showLoading(false);
                Intent i = new Intent(getActivity(), StandingsActivity.class);
                i.putExtra("LEAGUE_NAME", league_name);
                i.putExtra("LEAGUE_SEASON", season);
                startActivity(i);
            }
        },30000);
    }

    public void getDataSK(String league_id, String season){
        String url = BASE_URL+LOOKUP_TABLE+"l="+league_id+"&s="+season;
        Log.e("cek url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray data = response.getJSONArray("table");
                            for (int a=0; a<data.length(); a++){
                                JSONObject c = data.getJSONObject(a);
                                ContentValues values = new ContentValues();
                                values.put("RANK", c.getString("intRank"));
                                values.put("TEAM_BADGE_S", c.getString("strTeamBadge"));
                                values.put("TEAM_NAME_S", c.getString("strTeam"));
                                values.put("PLAYED", c.getString("intPlayed"));
                                values.put("WIN", c.getString("intWin"));
                                values.put("DRAW", c.getString("intDraw"));
                                values.put("LOSS", c.getString("intLoss"));
                                values.put("GOALS_FOR", c.getString("intGoalsFor"));
                                values.put("GOALS_AGAINST", c.getString("intGoalsAgainst"));
                                values.put("GOAL_DIFFERENCE", c.getString("intGoalDifference"));
                                values.put("POINTS", c.getString("intPoints"));
                                values.put("FORM", c.getString("strForm"));

                                helper.insertSK(values);

                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        Log.e("FragmentSeason", "onError: "  + error.getErrorDetail() );
                    }
                });
    }
}