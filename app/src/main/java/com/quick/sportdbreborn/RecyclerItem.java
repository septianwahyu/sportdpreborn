package com.quick.sportdbreborn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerItem extends RecyclerView.Adapter<RecyclerItem.ViewHolderItem> {
    Context mContext;
    ItemClickListener mItemClickListen;
    ArrayList<DataRowAL> mleagueArrayList;
    dbHelp helper;

    public RecyclerItem(Context context, ArrayList<DataRowAL> leagueArrayList, ItemClickListener listener) {
        mContext = context;
        mItemClickListen = listener;
        mleagueArrayList = leagueArrayList;
        helper = new dbHelp(mContext);
    }

    interface ItemClickListener {
        void OnItemClick(int position, View itemView);
    }

    @Override
    public int getItemCount() {
        return mleagueArrayList.size();
    }

    @Override
    public ViewHolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_list_item, parent, false);
        return new ViewHolderItem(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderItem holder, final int position) {
        final DataRowAL dataRowAL = mleagueArrayList.get(position);
        holder.tv_urut.setText(dataRowAL._id);
        holder.tv_league_name.setText(dataRowAL.M_LEAGUE_NAME);
        holder.tv_alt_league_name.setText(dataRowAL.M_LEAGUE_ALT_NAME);
        holder.tv_sport_type.setText(dataRowAL.M_SPORT_TYPE);

    }

    class ViewHolderItem extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {
        CardView cv_data;
        TextView tv_urut;
        TextView tv_league_name, tv_alt_league_name, tv_sport_type;

        public ViewHolderItem(View view) {
            super(view);
            cv_data = (CardView) view.findViewById(R.id.cv_data);
            tv_urut = (TextView) view.findViewById(R.id.tv_urut);
            tv_league_name = (TextView) view.findViewById(R.id.tv_league_name);
            tv_sport_type = (TextView) view.findViewById(R.id.tv_sport_type);
            tv_alt_league_name = (TextView) view.findViewById(R.id.tv_alt_league_name);

            cv_data.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemClickListen.OnItemClick(getAdapterPosition(), v);
        }


    }
}