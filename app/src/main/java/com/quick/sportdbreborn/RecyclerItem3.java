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
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class RecyclerItem3 extends RecyclerView.Adapter<RecyclerItem3.ViewHolderItem> {
    Context mContext;
    ItemClickListener mItemClickListen;
    ArrayList<DataRowATDIAL> mTeamArrayList;
    dbHelp helper;

    public RecyclerItem3(Context context, ArrayList<DataRowATDIAL> teamArrayList, ItemClickListener listener) {
        mContext = context;
        mItemClickListen = listener;
        mTeamArrayList = teamArrayList;
        helper = new dbHelp(mContext);
    }

    interface ItemClickListener {
        void OnItemClick(int position, View itemView);
    }

    @Override
    public int getItemCount() {
        return mTeamArrayList.size();
    }

    @Override
    public ViewHolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_list_item3, parent, false);
        return new ViewHolderItem(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderItem holder, final int position) {
        final DataRowATDIAL DataRowATDIAL = mTeamArrayList.get(position);
        holder.tv_urut.setText(DataRowATDIAL._id);
        Glide.with(mContext)
                .load(DataRowATDIAL.M_TEAM_BADGE)
                .placeholder(R.drawable.no_image)
                .into(holder.iv_team_badge);
        holder.tv_team_name.setText(DataRowATDIAL.M_TEAM_NAME);

    }

    class ViewHolderItem extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {
        CardView cv_data;
        TextView tv_urut;
        TextView tv_team_name;
        ImageView iv_team_badge;

        public ViewHolderItem(View view) {
            super(view);
            cv_data = (CardView) view.findViewById(R.id.cv_data);
            tv_urut = (TextView) view.findViewById(R.id.tv_urut);
            iv_team_badge = (ImageView) view.findViewById(R.id.iv_team_badge);
            tv_team_name = (TextView) view.findViewById(R.id.tv_team_name);

            cv_data.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemClickListen.OnItemClick(getAdapterPosition(), v);
        }


    }
}