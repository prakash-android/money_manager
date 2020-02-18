package com.money.manger.view.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.money.manger.R;
import com.money.manger.model.MyListData;
import com.money.manger.view.database.DbHelper;
import com.money.manger.view.utils.PreferenceAppHelper;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.threeten.bp.LocalDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MonthlyDateSelectionActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    MenuItem amtMenu;

    @BindView(R.id.calendarView)
    MaterialCalendarView calenderView;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.root_layout)
    RelativeLayout rootLayout;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    String selectedDate = "";
    String selectedMonth = "";
    String monthOfDay = "";
    int monthlyTotal = 0;

    DbHelper dbhelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_dateselection);
        ButterKnife.bind(this);

        dbhelper = new DbHelper(this);
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

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        amtMenu.setTitle(String.valueOf(monthlyTotal));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setNavigationViewListener() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displayView(item.getItemId());
        return true;
    }

    private void displayView(int itemId) {
        switch (itemId) {

            case R.id.user_profile:

                Intent i = new Intent(this, ProfileActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.enter_right_to_left, R.anim.exit_left_to_right);
                break;
            case R.id.logout:

                showLogoutDialog();
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
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

    public String formatMonthYear(String inputString){
        String formattedDate = "";
        try {
            SimpleDateFormat originalFormat =
                    new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM");
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

        //nav listener
        setNavigationViewListener();

        // Get Current Date as default
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        selectedDate = ( mYear + "-" + doubleDigitNumber((mMonth + 1)) + "-" + doubleDigitNumber(mDay));
        // sets value for first time fetch
        toolbar.setTitle(formatMonth(selectedDate));
        monthOfDay = formatMonthYear(selectedDate);
        //getMonthlyData();


        calenderView.setSelectedDate(LocalDate.parse(selectedDate));
        calenderView.setSelected(true);
        calenderView.setOnDateChangedListener(
                new OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                        final String text = String.valueOf(date.getDate());
                        selectedDate = text;
                    }
                });


        // month change listener
        calenderView.setOnMonthChangedListener( new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                final String text = String.valueOf(date.getDate());
                selectedMonth = formatMonth(text);
                monthOfDay = formatMonthYear(text);
                //Log.e("mm" , monthOfDay );
                toolbar.setTitle(selectedMonth);
                getMonthlyData();
            }
        });
    }


    @OnClick(R.id.nxt_btn)
    public void nextButton(){
       // Log.e("mm", selectedDate + " " + formatDate(selectedDate));
        Intent i = new Intent(this, DailyExpensesActivity.class);
        i.putExtra("uidate", "" + formatDate(selectedDate));
        i.putExtra("date", "" + selectedDate);
        startActivity(i);
    }


    //get value from db
    private void getMonthlyData() {

        //reset defaults
        monthlyTotal = 0;

        //fetch total from db
        monthlyTotal = dbhelper.getAllMonthCashHistoryTotal(monthOfDay);

        // updates value in toolbar
        invalidateOptionsMenu();
    }


    @Override
    protected void onResume() {
        super.onResume();
        getMonthlyData();
    }

    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.logo).setTitle("Exit");
            builder.setMessage("Are you sure you want to Exit?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishAffinity();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }

            });
            builder.setNegativeButton("No", null);
            //builder.show();
            AlertDialog dialog = builder.create();
            dialog.show(); //Only after .show() was called
            dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorThemeOrange));
            dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorThemeOrange));


        }

    }

    public void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.logo).setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                PreferenceAppHelper.setLoginedUser(false);
                PreferenceAppHelper.setUserName("null");
                PreferenceAppHelper.setUserEmail("null");
                PreferenceAppHelper.setUserImage("null");
                Intent loginIntent = new Intent(MonthlyDateSelectionActivity.this, LoginActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(loginIntent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }

        });
        builder.setNegativeButton("No", null);
        //builder.show();
        AlertDialog dialog = builder.create();
        dialog.show(); //Only after .show() was called
        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorThemeOrange));
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorThemeOrange));
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
