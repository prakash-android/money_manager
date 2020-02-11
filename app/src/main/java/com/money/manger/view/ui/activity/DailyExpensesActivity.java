package com.money.manger.view.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.money.manger.R;
import com.money.manger.model.MyListData;
import com.money.manger.view.database.DbHelper;
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
    Double dailyTotal = 0.0;

    DbHelper dbhelper;
    ArrayList<MyListData> myListData = new ArrayList<MyListData>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_expenses);
        ButterKnife.bind(this);

        setToolbar();
        Intent intent = getIntent();
        getIntentValues(intent);

        dbhelper = new DbHelper(this);
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
        amtMenu.setTitle(dailyTotal.toString());
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

        //reset defaults
        myListData.clear();
        dailyTotal = 0.0;

        //get values n calculate dailytotal
        myListData .addAll( dbhelper.getAllCashHistory(dateString));

        String t1;
        for(MyListData item : myListData) {

            t1 = item.getAmt();
            dailyTotal += Double.valueOf(t1);
        }

        updateDailyTotal();
    }


    public void updateDailyTotal(){
        invalidateOptionsMenu();
        //Toast.makeText(this,"daily total: "+ dailyTotal.toString(),Toast.LENGTH_SHORT).show();
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
