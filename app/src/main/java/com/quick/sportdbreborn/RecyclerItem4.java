package com.quick.sportdbreborn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerItem4 extends RecyclerView.Adapter<RecyclerItem4.ViewHolderItem> {
    Context mContext;
    ItemClickListener mItemClickListen;
    ArrayList<DataRowSK> mKlasemenArrayList;
    dbHelp helper;

    public RecyclerItem4(Context context, ArrayList<DataRowSK> KlasemenArrayList, ItemClickListener listener) {
        mContext = context;
        mItemClickListen = listener;
        mKlasemenArrayList = KlasemenArrayList;
        helper = new dbHelp(mContext);
    }

    interface ItemClickListener {
        void OnItemClick(int position, View itemView);
    }

    @Override
    public int getItemCount() {
        return mKlasemenArrayList.size();
    }

    @Override
    public ViewHolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_list_item4, parent, false);
        return new ViewHolderItem(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderItem holder, final int position) {
        final DataRowSK DataRowSK = mKlasemenArrayList.get(position);
        holder.tv_urut.setText(DataRowSK._id);
        holder.tv_rank.setText(DataRowSK.M_RANK);
        Glide.with(mContext)
                .load(DataRowSK.M_TEAM_BADGE)
                .placeholder(R.drawable.no_image)
                .into(holder.iv_team_badge_s);
        holder.tv_team_name_s.setText(DataRowSK.M_TEAM_NAME);
        holder.tv_mp.setText(DataRowSK.M_PLAYED);
        holder.tv_w.setText(DataRowSK.M_WIN);
        holder.tv_d.setText(DataRowSK.M_DRAW);
        holder.tv_l.setText(DataRowSK.M_LOSS);
        holder.tv_gf.setText(DataRowSK.M_GOALS_FOR);
        holder.tv_ga.setText(DataRowSK.M_GOALS_AGAINST);
        holder.tv_gd.setText(DataRowSK.M_GOAL_DIFFERENCE);
        holder.tv_pts.setText(DataRowSK.M_POINTS);
        holder.tv_form.setText(DataRowSK.M_FORM);

    }

    class ViewHolderItem extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {
        TextView tv_urut;
        TextView tv_rank, tv_team_name_s, tv_mp, tv_w, tv_d, tv_l, tv_gf, tv_ga, tv_gd, tv_pts, tv_form;
        ImageView iv_team_badge_s;

        public ViewHolderItem(View view) {
            super(view);
            tv_urut = (TextView) view.findViewById(R.id.tv_urut);
            tv_rank = (TextView) view.findViewById(R.id.tv_rank);
            iv_team_badge_s = (ImageView) view.findViewById(R.id.iv_team_badge_s);
            tv_team_name_s = (TextView) view.findViewById(R.id.tv_team_name_s);
            tv_mp = (TextView) view.findViewById(R.id.tv_mp);
            tv_w = (TextView) view.findViewById(R.id.tv_w);
            tv_d = (TextView) view.findViewById(R.id.tv_d);
            tv_l = (TextView) view.findViewById(R.id.tv_l);
            tv_gf = (TextView) view.findViewById(R.id.tv_gf);
            tv_ga = (TextView) view.findViewById(R.id.tv_ga);
            tv_gd = (TextView) view.findViewById(R.id.tv_gd);
            tv_pts = (TextView) view.findViewById(R.id.tv_pts);
            tv_form = (TextView) view.findViewById(R.id.tv_form);
        }

        @Override
        public void onClick(View v) {
            mItemClickListen.OnItemClick(getAdapterPosition(), v);
        }


    }
}