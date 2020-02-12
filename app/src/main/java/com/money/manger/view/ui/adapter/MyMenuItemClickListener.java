package com.money.manger.view.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.money.manger.R;
import com.money.manger.model.MyListData;
import com.money.manger.view.database.DbHelper;
import com.money.manger.view.ui.activity.DailyExpensesActivity;

import java.util.ArrayList;

/**
 * overflow menu item listener class set to recyclerview adapter
 * handling action selection in overflow menu here
 * selected {with row id} card data is sent here, here we separate items n apply
 * update ui after delete
 */
class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

    Context mContext;
    DbHelper dbHelper;
    MyListData dummyData;
    String item;
    String amt;
    int id;
    int position;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<MyListData> allList;


    public MyMenuItemClickListener(Context context, MyListData dummy, ArrayList<MyListData> original, int pos) {
        mContext = context;
        dbHelper = new DbHelper(mContext);
        dummyData = dummy;
        item = dummyData.getItem();
        amt = dummyData.getAmt();
        id = dummyData.getId();
        allList = original;
        position = pos;
        // Initialize a new instance of RecyclerView Adapter instance
         mAdapter = new MyListAdapter(mContext,allList);
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_edit:
                editAction();
                return true;
            case R.id.action_delete:
                deleteAction();
                return true;
            default:
        }
        return false;
    }

    private void editAction() {
        Toast.makeText(mContext,  item + " "+ amt + " " + id, Toast.LENGTH_SHORT).show();
    }

    private void deleteAction() {
        boolean resultQuery = false;
        Log.e("list", String.valueOf(allList) + " " + position);
        resultQuery = dbHelper.deleteItem(id);

        if(resultQuery){
            // remove from listdata n update to adapter
            allList.remove(position);
            mAdapter.notifyItemRemoved(position);
            //mAdapter.notifyDataSetChanged();
            //mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
            Log.e("list", String.valueOf(allList));
            Toast.makeText(mContext, "expenses deleted", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(mContext, "error occurred", Toast.LENGTH_LONG).show();
        }
    }
}