package com.money.manger;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    Calendar myCalendar;
    @BindView(R.id.setDate)
    EditText dateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.setDate)
    public void selectDate(){
         myCalendar = Calendar.getInstance();
         int y = myCalendar.get(Calendar.YEAR);
         int m = myCalendar.get(Calendar.MONTH);
         int d = myCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {

                dateEditText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

            }
        }
        ,
                y,m,d);
        dialog.show();
    }
}
