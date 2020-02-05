package com.money.manger;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpensesActivity  extends AppCompatActivity {

    //show current month stats
    @BindView(R.id.toolbarDate)
    TextView toolbarDate;

    @BindView(R.id.toolbarCash)
    TextView toolbarCash;

    String dateString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        getIntentValues(intent);
        setListeners();
    }


    public void setListeners(){
        toolbarCash.setText("1290");

    }


    public void getIntentValues(Intent intent){
        dateString = "" + intent.getStringExtra("date");
        setIntentValues();
    }

    public void setIntentValues() {
        toolbarDate.setText(dateString);
    }





}
