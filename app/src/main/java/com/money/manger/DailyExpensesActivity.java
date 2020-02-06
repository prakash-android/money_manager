package com.money.manger;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyExpensesActivity extends AppCompatActivity {

    //show current daily stats (daily view in toolbar)
    @BindView(R.id.toolbarDate)
    TextView toolbarDate;

    @BindView(R.id.toolbarCash)
    TextView toolbarCash;

    String dateString = "";
    String uiDateString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_expenses);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        getIntentValues(intent);
        setListeners();
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





}
