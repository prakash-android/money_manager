package com.money.manger.view.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.money.manger.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.threeten.bp.LocalDate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Monthly_DateSelection_Activity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    MenuItem amtMenu;

    @BindView(R.id.calendarView)
    MaterialCalendarView calenderView;

    @BindView(R.id.dateTextView)
    TextView dateTextView;

    @BindView(R.id.root_layout)
    RelativeLayout rootLayout;

    String selectedDate = "";

//    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_dateselection);
        ButterKnife.bind(this);

        setToolbar();
        setupListeners();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.monthly_toolbar_menu, menu);
        amtMenu = menu.findItem(R.id.mmamt_item);
        amtMenu.setTitle("dummy");
        return true;
    }

    private void setToolbar() {
        toolbar.setTitle("title here");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        amtMenu.setTitle("100");
        return true;
    }


    /**
     * initial date format - yyyy-MM-dd
     * return month in full string
     * getdate() - to get date at the beginning
     */
    public String formatMonth(String inputString) {
        String formattedDate = "";

        try {
            SimpleDateFormat originalFormat =
                    new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat targetFormat = new SimpleDateFormat("MMMM");
            Date date = originalFormat.parse(inputString);
            formattedDate = targetFormat.format(date);
        } catch (Exception e){
            Log.e("Exception", "" + e.getMessage());
        }
        return formattedDate;
    }

    public String formatDate(String inputString) {
        String formattedDate = "";

        try {
            SimpleDateFormat originalFormat =
                    new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy");
            Date date = originalFormat.parse(inputString);
            formattedDate = targetFormat.format(date);
        } catch (Exception e){
            Log.e("Exception", "" + e.getMessage());
        }
        return formattedDate;
    }


    // get double digits in date
    public String doubleDigitNumber(int Date) {

        String initialNumber = "";

        if (Date < 10) {
            initialNumber = "0" + Date;
        } else {
            initialNumber = "" + Date;
        }

        return initialNumber;
    }



    public void setupListeners(){
        // Get Current Date as default
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        selectedDate = ( mYear + "-" + doubleDigitNumber((mMonth + 1)) + "-" + doubleDigitNumber(mDay));
        toolbar.setTitle(formatMonth(selectedDate));


        calenderView.setSelectedDate(LocalDate.parse(selectedDate));
        calenderView.setSelected(true);
        calenderView.setOnDateChangedListener(
                new OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                        final String text = String.valueOf(date.getDate());
                        selectedDate = text;
                        String tempDate = formatDate(text);
                        dateTextView.setText(tempDate);
                    }
                });


        // month change listener
        calenderView.setOnMonthChangedListener( new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                final String text = String.valueOf(date.getDate());
                toolbar.setTitle(formatMonth(text));
            }
        });
    }


    @OnClick(R.id.nxt_btn)
    public void nextButton(){
       // Log.e("mm", selectedDate + " " + formatDate(selectedDate));
        dateTextView.setText(formatDate(selectedDate));
        Intent i = new Intent(this, DailyExpensesActivity.class);
        i.putExtra("uidate", "" + formatDate(selectedDate));
        i.putExtra("date", "" + selectedDate);
        startActivity(i);
    }


    @Override
    protected void onResume() {
        super.onResume();
       // dateTextView.setText(formatDate(selectedDate));
    }
}



/*

//        // cropping out of {} brackets in string
//        int start = inputString.indexOf("{") + 1;
//        int end = inputString.indexOf("}");
//        String result = inputString.substring(start, end);

    //date formatter with month in 3 letters
    public String dateWithMonthInLetters(String inputString) {
        String formattedDate = "";

        try {
            SimpleDateFormat originalFormat =
                   new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy");
            Date date = originalFormat.parse(inputString);
            formattedDate = targetFormat.format(date);
        } catch (Exception e){
            Log.e("Exception", "" + e.getMessage());
        }
        return formattedDate;
    }

    //date formatter with month (MMM - 3 letters)
    public String monthInLetters(String inputString) {
        String formattedDate = "";

        try {
            SimpleDateFormat originalFormat =
                    new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            SimpleDateFormat targetFormat = new SimpleDateFormat("MMMM");
            Date date = originalFormat.parse(inputString);
            formattedDate = targetFormat.format(date);
        } catch (Exception e){
            Log.e("Exception", "" + e.getMessage());
        }
        return formattedDate;
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

    @OnClick(R.id.btn)
    public void nextButton(){
        // validate n move to next screen
        if(dateEditText.getText().toString().equals("select date")) {
            Snackbar snackbar = Snackbar
                    .make(constraintLayout, "select the date", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            Intent i = new Intent(this, DailyExpensesActivity.class);
            startActivity(i);
        }
    }*/
