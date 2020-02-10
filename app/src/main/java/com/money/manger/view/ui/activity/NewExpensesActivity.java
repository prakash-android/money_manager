package com.money.manger.view.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.money.manger.R;
import com.money.manger.view.database.DatabaseHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class NewExpensesActivity extends AppCompatActivity {

    @BindView(R.id.nameEditText)
    EditText nameEditText;

    @BindView(R.id.amtEditText)
    EditText amtEditText;

    SQLiteDatabase sqLiteDatabaseObj;
    String NameHolder, NumberHolder, SQLiteDataBaseQueryHolder;
    Boolean EditTextEmptyHold;
    String dateString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expenses);
        ButterKnife.bind(this);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent intent = getIntent();
        getIntentValues(intent);

        SQLiteDataBaseBuild();

    }


    public void getIntentValues(Intent intent) {
        dateString = "" + intent.getStringExtra("date");
    }

    // create sqlite db and table
    public void SQLiteDataBaseBuild(){

        sqLiteDatabaseObj = openOrCreateDatabase("MoneyDataBase", Context.MODE_PRIVATE, null);
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS Expenses(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, item_name VARCHAR, amount VARCHAR, date VARCHAR);");

    }

    public void CheckEditTextStatus(){

        NameHolder = nameEditText.getText().toString() ;
        NumberHolder = amtEditText.getText().toString();

        if(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(NumberHolder)){

            EditTextEmptyHold = false ;

        }
        else {

            EditTextEmptyHold = true ;
        }
    }

    public void InsertDataIntoSQLiteDatabase(){

        if(EditTextEmptyHold == true)
        {

            SQLiteDataBaseQueryHolder = "INSERT INTO Expenses (item_name,amount,date) VALUES('"+NameHolder+"', '"+NumberHolder+"', '"+dateString+"');";

            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

            Toast.makeText(NewExpensesActivity.this,"Data Inserted Successfully", Toast.LENGTH_LONG).show();

        }
        else {

            Toast.makeText(NewExpensesActivity.this,"Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();

        }

    }

    public void EmptyEditTextAfterDataInsert(){

        nameEditText.getText().clear();

        amtEditText.getText().clear();

    }

    @OnClick(R.id.add_button)
    public void addButton(){

        CheckEditTextStatus();
        InsertDataIntoSQLiteDatabase();
        EmptyEditTextAfterDataInsert();

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
