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
 * selected {positioned listData is sent here} card data is sent here, here we separate items n apply
 */
class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

    Context mContext;
    DbHelper dbHelper;
    MyListData dummyData;
    String item;
    String amt;
    int position;


    public MyMenuItemClickListener(Context context, MyListData listData, int pos) {
        mContext = context;
        dbHelper = new DbHelper(mContext);
        dummyData = listData;
        item = listData.getItem();
        amt = listData.getAmt();
        position = pos;
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
        Toast.makeText(mContext,  item + " "+ amt + " " + position, Toast.LENGTH_SHORT).show();
    }

    private void deleteAction() {
        dbHelper.deleteItem(position);

        //update view, causes error
//        DailyExpensesActivity d = new DailyExpensesActivity();
//        d.displayListValues();
    }
}