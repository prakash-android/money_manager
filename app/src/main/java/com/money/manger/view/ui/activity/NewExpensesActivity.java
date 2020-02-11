package com.money.manger.view.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.money.manger.R;
import com.money.manger.view.database.DbHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class NewExpensesActivity extends AppCompatActivity {

    @BindView(R.id.nameEditText)
    EditText nameEditText;

    @BindView(R.id.amtEditText)
    EditText amtEditText;

    @BindView(R.id.name_layout)
    TextInputLayout nameLayout;

    @BindView(R.id.amt_layout)
    TextInputLayout amtLayout;

    String dateString;

    DbHelper dbhelper;



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

        dbhelper =new DbHelper(this);

    }


    public void getIntentValues(Intent intent) {
        dateString = "" + intent.getStringExtra("date");
    }







    @OnClick(R.id.add_button)
    public void addButton(){

        nameLayout.setErrorEnabled(false);
        amtLayout.setErrorEnabled(false);

        if(nameEditText.getText().toString().isEmpty() ){
            nameLayout.setError("Enter Valid Item Name");
            return;
        }
        if(amtEditText.getText().toString().isEmpty() ){
            amtLayout.setError("Amount Must Not Be Empty");
            return;
        }

        addNewRow();

    }

    public void addNewRow(){
        dbhelper.addCashHistory(""+nameEditText.getText().toString(), ""+amtEditText.getText().toString(), ""+dateString );
        Log.e("mm", "data inserted");
        EmptyEditTextAfterDataInsert();
    }

    public void EmptyEditTextAfterDataInsert(){

        nameEditText.getText().clear();

        amtEditText.getText().clear();

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
