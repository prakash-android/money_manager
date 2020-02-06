package com.money.manger.view.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.money.manger.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DailyExpensesActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    //show current daily stats (daily view in toolbar)
    @BindView(R.id.toolbarDate)
    TextView toolbarDate;

    @BindView(R.id.toolbarCash)
    TextView toolbarCash;

    @BindView(R.id.add_btn)
    Button addButton;

    String dateString = "";
    String uiDateString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_expenses);
        ButterKnife.bind(this);

        addBackButtonToolbar();
        Intent intent = getIntent();
        getIntentValues(intent);
        setListeners();
    }

    private void addBackButtonToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }


    @OnClick(R.id.add_btn)
    public void addButton(){
      Intent i = new Intent(this, NewExpensesActivity.class);
      startActivity(i);
      overridePendingTransition(R.anim.enter_right_to_left ,R.anim.exit_left_to_right);
    }


    public void setListeners(){
        toolbarCash.setText("0");

    }


    public void getIntentValues(Intent intent){
        uiDateString = "" + intent.getStringExtra("uidate");
        dateString = "" + intent.getStringExtra("date");
        setIntentValues();
    }

    public void setIntentValues() {
        toolbarDate.setText(uiDateString);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(R.anim.back_left_to_right,R.anim.back_right_to_left);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.back_left_to_right,R.anim.back_right_to_left);
    }


}
