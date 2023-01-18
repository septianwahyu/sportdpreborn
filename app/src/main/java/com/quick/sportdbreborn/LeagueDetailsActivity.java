package com.quick.sportdbreborn;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.stetho.Stetho;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.mohamedabulgasem.loadingoverlay.LoadingAnimation;
import com.mohamedabulgasem.loadingoverlay.LoadingOverlay;
import com.stfalcon.imageviewer.StfalconImageViewer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.quick.sportdbreborn.config.Config.ALL_LEAGUES;
import static com.quick.sportdbreborn.config.Config.ALL_SEASONS_IN_A_LEAGUE;
import static com.quick.sportdbreborn.config.Config.BASE_URL;
import static com.quick.sportdbreborn.config.Config.LEAGUES_DETAILS;

public class LeagueDetailsActivity extends AppCompatActivity {

    dbHelp helper;
    String league_id;
    TextView tv_league_name;
    ImageView iv_league_badge;
    TabLayout tabLayout;
    ViewPager2 pager2;
    FragmentAdapter fadapter;
    private static LoadingOverlay loadingOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_details);
        getSupportActionBar().hide();
        AndroidNetworking.initialize(getApplicationContext());

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build()
        );

        loadingOverlay = LoadingOverlay.Companion.with(LeagueDetailsActivity.this, new LoadingAnimation(R.raw.loading_sport, 300), 0.5F, false, null, null, null);

        helper = new dbHelp(this);

        iv_league_badge = (ImageView) findViewById(R.id.iv_league_badge);
        tv_league_name = (TextView) findViewById(R.id.tv_league_name);

        tabLayout = findViewById(R.id.tab_layout);
        pager2 = findViewById(R.id.view_pager2);

        setData();

        FragmentManager fm = getSupportFragmentManager();
        fadapter = new FragmentAdapter(fm, getLifecycle());
        pager2.setAdapter(fadapter);

        tabLayout.addTab(tabLayout.newTab().setText("Details"));
        tabLayout.addTab(tabLayout.newTab().setText("Season"));
        tabLayout.addTab(tabLayout.newTab().setText("Teams"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        iv_league_badge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor c = helper.selectLD();
                if (c.moveToFirst()) {
                    List<String> uri = new ArrayList<String>();
                    uri.add(c.getString(14));
                    new StfalconImageViewer.Builder<>(LeagueDetailsActivity.this, uri, (imageView, image) ->
                            Glide.with(LeagueDetailsActivity.this).load(image).into(imageView))
                            .withTransitionFrom(iv_league_badge).show();
                }
            }
        });

    }

    public static void showLoading(boolean show) {
        if (show) loadingOverlay.show();
        else loadingOverlay.dismiss();
    }

    void setData(){
        Cursor c = helper.selectLD();
        if (c.moveToFirst()) {
            Glide.with(this)
                    .load(c.getString(14))
                    .placeholder(R.drawable.no_image)
                    .into(iv_league_badge);
            tv_league_name.setText(c.getString(2));
        }
    }
}
