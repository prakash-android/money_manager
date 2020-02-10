package com.money.manger.view.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.money.manger.R;
import com.money.manger.model.MyListData;
import com.money.manger.view.ui.adapter.MyListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DailyExpensesActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    MenuItem amtMenu;

    @BindView(R.id.add_btn)
    Button addButton;

    String dateString = "";
    String uiDateString = "";
    SQLiteDatabase sqLiteDatabaseObj;
    private ArrayList<String> date = new ArrayList<String>();
    private ArrayList<String> itemName = new ArrayList<String>();
    private ArrayList<String> Amount = new ArrayList<String>();
    ArrayList<MyListData> myListData = new ArrayList<MyListData>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_expenses);
        ButterKnife.bind(this);

        setToolbar();
        Intent intent = getIntent();
        getIntentValues(intent);
        displayListValues();
    }


    /**
     * onCreateOptionMenu is called at point onCreate call finishes
     * set initial value in onCreateOptionMenu
     * update values using onPrepareOptionMenu
     * MenuItem is decalred at top (without binding)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.daily_toolbar_menu, menu);
        amtMenu = menu.findItem(R.id.ddamt_item);
        amtMenu.setTitle("dummy");
        return true;
    }

    private void setToolbar() {
        toolbar.setTitle("title here");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        amtMenu.setTitle("0");
        return true;
    }



    public void displayListValues() {

        getData();

        // set value to recyclerview
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_list);
        MyListAdapter adapter = new MyListAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    //get value from db
    private void getData() {

        sqLiteDatabaseObj = openOrCreateDatabase("MoneyDataBase", Context.MODE_PRIVATE, null);
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS Expenses(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, item_name VARCHAR, amount VARCHAR, date VARCHAR);");

        Cursor cursor = sqLiteDatabaseObj.rawQuery("SELECT * FROM  Expenses WHERE date = ?",new String[] { dateString });
        Amount.clear();
        itemName.clear();
        myListData.clear();
        String t1, t2;
        if (cursor.moveToFirst()) {
            do {
                t1 = cursor.getString(cursor.getColumnIndex("item_name"));
                t2 = cursor.getString(cursor.getColumnIndex("amount"));
                myListData.add(new MyListData(t1,t2));
                itemName.add(cursor.getString(cursor.getColumnIndex("item_name")));
                Amount.add(cursor.getString(cursor.getColumnIndex("amount")));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }



    @OnClick(R.id.add_btn)
    public void addButton() {
        Intent i = new Intent(this, NewExpensesActivity.class);
        i.putExtra("date", dateString);
        startActivity(i);
        overridePendingTransition(R.anim.enter_right_to_left, R.anim.exit_left_to_right);
    }


    public void getIntentValues(Intent intent) {
        uiDateString = "" + intent.getStringExtra("uidate");
        dateString = "" + intent.getStringExtra("date");
        setIntentValues();
    }

    public void setIntentValues() {
        toolbar.setTitle(uiDateString);
    }



    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(R.anim.back_left_to_right, R.anim.back_right_to_left);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.back_left_to_right, R.anim.back_right_to_left);
    }

    @Override
    protected void onResume() {
        displayListValues();
        super.onResume();
    }

}
