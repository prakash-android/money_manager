package com.money.manger.view.ui.adapter;

import android.content.Context;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;

import com.money.manger.R;
import com.money.manger.model.MyListData;
import com.money.manger.view.database.DbHelper;
import com.money.manger.view.ui.activity.DailyExpensesActivity;

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


    public MyMenuItemClickListener(Context context, MyListData listData) {
        mContext = context;
        dbHelper = new DbHelper(mContext);
        dummyData = listData;
        item = listData.getItem();
        amt = listData.getAmt();
        id = listData.getId();
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
        resultQuery = dbHelper.deleteItem(id);

        if(resultQuery){
            Toast.makeText(mContext, "expenses deleted", Toast.LENGTH_LONG).show();
            //update ui
//            mylistdata.remove(getAdapterPosition());
//            MyListAdapter.notifyDataSetChanged();
        }else{
            Toast.makeText(mContext, "error occurred", Toast.LENGTH_LONG).show();
        }
    }
}