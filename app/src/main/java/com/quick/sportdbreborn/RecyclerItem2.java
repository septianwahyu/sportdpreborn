package com.quick.sportdbreborn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerItem2 extends RecyclerView.Adapter<RecyclerItem2.ViewHolderItem> {
    Context mContext;
    ItemClickListener mItemClickListen;
    ArrayList<DataRowAS> mseasonArrayList;
    dbHelp helper;

    public RecyclerItem2(Context context, ArrayList<DataRowAS> seasonArrayList, ItemClickListener listener) {
        mContext = context;
        mItemClickListen = listener;
        mseasonArrayList = seasonArrayList;
        helper = new dbHelp(mContext);
    }

    interface ItemClickListener {
        void OnItemClick(int position, View itemView);
    }

    @Override
    public int getItemCount() {
        return mseasonArrayList.size();
    }

    @Override
    public ViewHolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_list_item2, parent, false);
        return new ViewHolderItem(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderItem holder, final int position) {
        final DataRowAS DataRowAS = mseasonArrayList.get(position);
        holder.tv_urut.setText(DataRowAS._id);
        holder.tv_season.setText(DataRowAS.M_SEASON);

    }

    class ViewHolderItem extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {
        CardView cv_data;
        TextView tv_urut;
        TextView tv_season;

        public ViewHolderItem(View view) {
            super(view);
            cv_data = (CardView) view.findViewById(R.id.cv_data);
            tv_urut = (TextView) view.findViewById(R.id.tv_urut);
            tv_season = (TextView) view.findViewById(R.id.tv_season);

            cv_data.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemClickListen.OnItemClick(getAdapterPosition(), v);
        }


    }
}