package com.money.manger.view.ui.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import com.money.manger.R;
import com.money.manger.model.MyListData;
import com.money.manger.view.database.DbHelper;
import com.money.manger.view.ui.activity.EditExpensesActivity;
import com.money.manger.view.utils.Utils;

import java.util.ArrayList;


public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

    private ArrayList<MyListData> listdata;
    private Context mContext;
    DbHelper dbHelper;


    // RecyclerView recyclerView;
    public MyListAdapter(Context context , ArrayList<MyListData> listdata) {
        this.listdata = listdata;
        mContext = context;
        dbHelper = new DbHelper(mContext);
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
     * send listData to listener
     * always check datatypes in db, adapter, view setters.
     */
    @Override
    public void onBindViewHolder(@NonNull MyListAdapter.ViewHolder holder, int position) {
        final MyListData myListData = listdata.get(position);
        holder.textViewItem.setText(listdata.get(position).getItem());
        holder.textViewAmt.setText(String.valueOf(listdata.get(position).getAmt()));
        //if list has image, return image (else show default image preview)
        if(listdata.get(position).getImg() != null){
            holder.itemImageView.setImageBitmap(Utils.getImage(listdata.get(position).getImg()));
        }


        holder.itemImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog mDialog = new Dialog(mContext);
                mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                mDialog.getWindow().setBackgroundDrawable(
                        new ColorDrawable(Color.TRANSPARENT)
                );
                mDialog.setContentView(R.layout.image_view_layout);
                mDialog.setCancelable(false);
                TextView cancelExitApp = mDialog.findViewById(R.id.cancel_text);
                ImageView fullImageView = mDialog.findViewById(R.id.full_image);

                if(listdata.get(position).getImg() != null) {
                    fullImageView.setImageBitmap(Utils.getImage(listdata.get(position).getImg()));
                } else {
                    fullImageView.setImageResource(R.drawable.ic_picture_preview);
                }

                cancelExitApp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                mDialog.show();
            }
        });

        holder.overflowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(mContext, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.overflow_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_edit:
                                editAction(listdata.get(position).getId(), listdata.get(position).getItem(), listdata.get(position).getAmt(), listdata.get(position).getDate());
                                return true;
                            case R.id.action_delete:
                                deleteAction(position, listdata.get(position).getId());
                                return true;
                            default:
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }



    // removes the row, beware using id with ID
    public void deleteAction(int position, int ID) {

            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Confirm");

            builder.setMessage("Do you want to delete?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            //execute db delete operation
                            boolean resultQuery = dbHelper.deleteItem(ID);
                            if(resultQuery) {
                                listdata.remove(position);
                                notifyItemRemoved(position);
                                Toast.makeText(mContext, "expenses deleted", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(mContext, "error occurred", Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                           dialog.dismiss();
                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            alert.setCancelable(false);
            alert.show();

    }


    //edit the values
    public void editAction(int id, String item, int amt, String date) {

        Intent intent = new Intent(mContext, EditExpensesActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name", item);
        intent.putExtra("amt", amt);
        intent.putExtra("date", date);
        mContext.startActivity(intent);
        //overridePendingTransition(R.anim.enter_right_to_left, R.anim.exit_left_to_right);
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewItem;
        public TextView textViewAmt;
        public ImageView overflowImageView;
        public ImageView itemImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewItem = (TextView) itemView.findViewById(R.id.textViewItem);
            this.textViewAmt = (TextView) itemView.findViewById(R.id.textViewAmount);
            this.overflowImageView = (ImageView) itemView.findViewById(R.id.overflow_image);
            this.itemImageView = (ImageView) itemView.findViewById(R.id.item_imageView);
        }
    }
}
