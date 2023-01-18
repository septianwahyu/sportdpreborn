package com.quick.sportdbreborn;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.facebook.stetho.Stetho;

import java.util.ArrayList;

public class StandingsActivity extends AppCompatActivity implements RecyclerItem4.ItemClickListener {

    dbHelp helper;
    RecyclerView rv_item;
    RecyclerItem4 adapter;
    ArrayList<DataRowSK> mStandingsList;
    String league_name, league_season;
    TextView tv_league_name, tv_league_season;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standings);
        getSupportActionBar().hide();
        AndroidNetworking.initialize(getApplicationContext());

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build()
        );

        league_name = getIntent().getStringExtra("LEAGUE_NAME");
        league_season = getIntent().getStringExtra("LEAGUE_SEASON");

        rv_item = (RecyclerView) findViewById(R.id.rv_item4);
        tv_league_name = (TextView) findViewById(R.id.tv_league_name);
        tv_league_season = (TextView) findViewById(R.id.tv_league_season);

        helper = new dbHelp(this);

        tv_league_name.setText(league_name);
        tv_league_season.setText(league_season);

        mStandingsList = getDataSet();
        adapter = new RecyclerItem4(StandingsActivity.this, mStandingsList, (RecyclerItem4.ItemClickListener) this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_item.hasFixedSize();
        rv_item.setLayoutManager(layoutManager);
        rv_item.setAdapter(adapter);


        showMessage();
    }

    void showMessage() {
        mStandingsList.clear();
        mStandingsList.addAll(getDataSet());
        adapter.notifyDataSetChanged();
    }

    ArrayList<DataRowSK> getDataSet() {
        ArrayList<DataRowSK> dataSet = new ArrayList<>();

        Cursor c = helper.selectSK();
        while (c.moveToNext()) {
            DataRowSK data = new DataRowSK();
            data.setData(
                    c.getString(c.getColumnIndex(helper.RANK)),
                    c.getString(c.getColumnIndex(helper.TEAM_NAME_S)),
                    c.getString(c.getColumnIndex(helper.TEAM_BADGE_S)),
                    c.getString(c.getColumnIndex(helper.PLAYED)),
                    c.getString(c.getColumnIndex(helper.WIN)),
                    c.getString(c.getColumnIndex(helper.DRAW)),
                    c.getString(c.getColumnIndex(helper.LOSS)),
                    c.getString(c.getColumnIndex(helper.GOALS_FOR)),
                    c.getString(c.getColumnIndex(helper.GOALS_AGAINST)),
                    c.getString(c.getColumnIndex(helper.GOAL_DIFFERENCE)),
                    c.getString(c.getColumnIndex(helper.POINTS)),
                    c.getString(c.getColumnIndex(helper.FORM)),
                    c.getString(c.getColumnIndex(helper._id5))
            );
            dataSet.add(data);
        }
        return dataSet;
    }

    @Override
    public void OnItemClick(int position, View itemView) {

    }
}
