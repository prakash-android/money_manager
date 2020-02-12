package com.money.manger.view.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import com.money.manger.R;
import com.money.manger.model.MyListData;

import java.util.ArrayList;


public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

    private ArrayList<MyListData> listdata;
    private Context mContext;

    // RecyclerView recyclerView;
    public MyListAdapter(Context context , ArrayList<MyListData> listdata) {
        this.listdata = listdata;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }


    /**
     * @param holder, position
     * pass data via listener to class
     * get id n pass in position here
     *
     */
    @Override
    public void onBindViewHolder(@NonNull MyListAdapter.ViewHolder holder, int position) {
        final MyListData myListData = listdata.get(position);
        holder.textViewItem.setText(listdata.get(position).getItem());
        holder.textViewAmt.setText(listdata.get(position).getAmt());
        holder.imageButtonOverflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(mContext, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.overflow_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new MyMenuItemClickListener(mContext,myListData, position+1));
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewItem;
        public TextView textViewAmt;
        public ImageButton imageButtonOverflow;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewItem = (TextView) itemView.findViewById(R.id.textViewItem);
            this.textViewAmt = (TextView) itemView.findViewById(R.id.textViewAmount);
            this.imageButtonOverflow = (ImageButton) itemView.findViewById(R.id.overflow_menu);
        }
    }
}
