package com.quick.sportdbreborn;

import android.database.Cursor;
import android.os.Bundle;
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

import com.facebook.stetho.Stetho;

import java.util.ArrayList;

public class FragmentTeamList extends Fragment implements RecyclerItem3.ItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentTeamList() {
        // Required empty public constructor
    }

    dbHelp helper;
    RecyclerView rv_item;
    RecyclerItem3 adapter;
    ArrayList<DataRowATDIAL> mTeamList;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTeamList newInstance(String param1, String param2) {
        FragmentTeamList fragment = new FragmentTeamList();
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
        View view = inflater.inflate(R.layout.fragment_team_list, container, false);

        rv_item = (RecyclerView) view.findViewById(R.id.rv_item3);

        helper = new dbHelp(getActivity());

        mTeamList = getDataSet();
        adapter = new RecyclerItem3(getActivity(), mTeamList, (RecyclerItem3.ItemClickListener) this);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        rv_item.hasFixedSize();
        rv_item.setLayoutManager(layoutManager);
        rv_item.setAdapter(adapter);

        showMessage();

        return view;
    }

    void showMessage() {
        mTeamList.clear();
        mTeamList.addAll(getDataSet());
        adapter.notifyDataSetChanged();
    }

    ArrayList<DataRowATDIAL> getDataSet() {
        ArrayList<DataRowATDIAL> dataSet = new ArrayList<>();

        Cursor c = helper.selectDT();
        while (c.moveToNext()) {
            DataRowATDIAL data = new DataRowATDIAL();
            data.setData(
                    c.getString(c.getColumnIndex(helper.TEAM_NAME)),
                    c.getString(c.getColumnIndex(helper.TEAM_BADGE)),
                    c.getString(c.getColumnIndex(helper._id))
            );
            dataSet.add(data);
        }
        return dataSet;
    }

    @Override
    public void OnItemClick(int position, View itemView) {
        Log.i("FragmentSeason", "OnItemClick: Do something");
    }
}