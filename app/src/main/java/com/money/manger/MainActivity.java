package com.money.manger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    Calendar myCalendar;
    @BindView(R.id.datePicker)
    DatePicker datePicker;


    @BindView(R.id.root_layout)
    ConstraintLayout constraintLayout;
    String selectedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }



    public String getCurrentDate(){
        StringBuilder builder=new StringBuilder();
        builder.append((datePicker.getMonth() + 1)+"/");
        builder.append(datePicker.getDayOfMonth()+"/");
        builder.append(datePicker.getYear());
        return builder.toString();
    }

    @OnClick(R.id.btn)
    public void nextButton(){
        Intent i = new Intent(this, ExpensesActivity.class);
        i.putExtra("date", "" + getCurrentDate());
        Toast.makeText(this,getCurrentDate(),Toast.LENGTH_LONG).show();
        startActivity(i);
    }

}



//    @OnClick(R.id.setDate)
//    public void selectDate(){
//         myCalendar = Calendar.getInstance();
//         int y = myCalendar.get(Calendar.YEAR);
//         int m = myCalendar.get(Calendar.MONTH);
//         int d = myCalendar.get(Calendar.DAY_OF_MONTH);
//
//        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year,
//                                  int monthOfYear, int dayOfMonth) {
//
//                dateEditText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//
//            }
//        }
//        ,
//                y,m,d);
//        dialog.show();
//    }

//    @OnClick(R.id.btn)
//    public void nextButton(){
//        // validate n move to next screen
//        if(dateEditText.getText().toString().equals("select date")) {
//            Snackbar snackbar = Snackbar
//                    .make(constraintLayout, "select the date", Snackbar.LENGTH_LONG);
//            snackbar.show();
//        } else {
//            Intent i = new Intent(this, ExpensesActivity.class);
//            startActivity(i);
//        }
//    }