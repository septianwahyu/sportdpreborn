package com.quick.sportdbreborn;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.stetho.Stetho;

import java.sql.Connection;
import java.util.ArrayList;

public class FragmentDetails extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentDetails() {
        // Required empty public constructor
    }

    dbHelp helper;
    TextView tv_league_name, tv_alt_league_name, tv_cur_season, tv_formed_year, tv_first_event, tv_country, tv_website, tv_facebook, tv_twitter, tv_youtube, tv_desc;
    ScrollView parentScrollView, childScrollView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDetails newInstance(String param1, String param2) {
        FragmentDetails fragment = new FragmentDetails();
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
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        parentScrollView = (ScrollView) view.findViewById(R.id.parentScrollView);
        childScrollView = (ScrollView) view.findViewById(R.id.childScrollView);

        tv_league_name = (TextView) view.findViewById(R.id.tv_league_name);
        tv_alt_league_name = (TextView) view.findViewById(R.id.tv_alt_league_name);
        tv_cur_season = (TextView) view.findViewById(R.id.tv_cur_season);
        tv_formed_year = (TextView) view.findViewById(R.id.tv_formed_year);
        tv_first_event = (TextView) view.findViewById(R.id.tv_first_event);
        tv_country = (TextView) view.findViewById(R.id.tv_country);
        tv_website = (TextView) view.findViewById(R.id.tv_website);
        tv_facebook = (TextView) view.findViewById(R.id.tv_facebook);
        tv_twitter = (TextView) view.findViewById(R.id.tv_twitter);
        tv_youtube = (TextView) view.findViewById(R.id.tv_youtube);
        tv_desc = (TextView) view.findViewById(R.id.tv_desc);

        helper = new dbHelp(getActivity());
        setData();
        Stetho.initialize(
                Stetho.newInitializerBuilder(getActivity())
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(getActivity()))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(getActivity()))
                        .build()
        );

        parentScrollView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                view.findViewById(R.id.childScrollView).getParent()
                        .requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });

        childScrollView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        return view;
    }

    void setData(){
        Cursor c = helper.selectLD();
        if (c.moveToFirst()) {
            tv_league_name.setText(c.getString(2));
            tv_alt_league_name.setText(c.getString(3));
            tv_cur_season.setText(c.getString(4));
            tv_formed_year.setText(c.getString(5));
            tv_first_event.setText(c.getString(6));
            tv_country.setText(c.getString(7));
            tv_website.setText(c.getString(8));
            tv_facebook.setText(c.getString(9));
            tv_twitter.setText(c.getString(10));
            tv_youtube.setText(c.getString(11));
            tv_desc.setText(c.getString(12));
        }
    }

}