package com.money.manger.view.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.money.manger.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_expenses);
        ButterKnife.bind(this);

        setToolbar();
        Intent intent = getIntent();
        getIntentValues(intent);
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




    @OnClick(R.id.add_btn)
    public void addButton() {
        Intent i = new Intent(this, NewExpensesActivity.class);
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
        super.onResume();
    }


}
