package com.money.manger.view.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.money.manger.R;
import com.money.manger.model.MyListData;

import java.util.ArrayList;


public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

    private ArrayList<MyListData> listdata;

    // RecyclerView recyclerView;
    public MyListAdapter( ArrayList<MyListData> listdata) {
        this.listdata = listdata;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyListAdapter.ViewHolder holder, int position) {
        final MyListData myListData = listdata.get(position);
        holder.textViewItem.setText(listdata.get(position).getItem());
        holder.textViewAmt.setText(listdata.get(position).getAmt());
//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(),"click on item: "+myListData.getDescription(),Toast.LENGTH_LONG).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewItem;
        public TextView textViewAmt;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewItem = (TextView) itemView.findViewById(R.id.textViewItem);
            this.textViewAmt = (TextView) itemView.findViewById(R.id.textViewAmount);
//            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }
}