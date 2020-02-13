package com.money.manger.view.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.money.manger.R;
import com.money.manger.view.database.DbHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class EditExpensesActivity extends AppCompatActivity {

    @BindView(R.id.nameEditText)
    EditText nameEditText;

    @BindView(R.id.amtEditText)
    EditText amtEditText;

    @BindView(R.id.name_layout)
    TextInputLayout nameLayout;

    @BindView(R.id.amt_layout)
    TextInputLayout amtLayout;

    String dateString;
    String nameString;
    String amtString;
    int id = 0;

    DbHelper dbhelper;
    AlertDialog.Builder builder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expenses);
        ButterKnife.bind(this);
        dbhelper =new DbHelper(this);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent intent = getIntent();
        getIntentValues(intent);

    }


    public void getIntentValues(Intent intent) {
        id = intent.getIntExtra("id", id);
        nameString = intent.getStringExtra("name");
        amtString = intent.getStringExtra("amt");
        dateString = "" + intent.getStringExtra("date");

        setIntentValues();
    }

    private void setIntentValues() {
        nameEditText.setText(nameString);
        amtEditText.setText(amtString);
    }


    @OnClick(R.id.update_button)
    public void updateButton(){

        nameLayout.setErrorEnabled(false);
        amtLayout.setErrorEnabled(false);

        //validate whitespace in edittext
        if(nameEditText.getText().toString().isEmpty() || nameEditText.getText().toString().equals(" ")){
            nameLayout.setError("Enter Valid Item Name");
            return;
        }
        if(amtEditText.getText().toString().isEmpty() ){
            amtLayout.setError("Amount Must Not Be Empty");
            return;
        }

        updateRow();

    }

    public void updateRow(){
        boolean queryResult = false;
        queryResult = dbhelper.updateCashHistory( id,""+nameEditText.getText().toString(), ""+amtEditText.getText().toString(), ""+dateString );

        if(queryResult) {
            Toast.makeText(this, "expenses updated",Toast.LENGTH_LONG).show();
            onBackPressed();
        } else {
            Toast.makeText(this, "error occurred",Toast.LENGTH_LONG).show();
        }
    }

/*    public void EmptyEditTextAfterDataInsert(){

        nameEditText.getText().clear();

        amtEditText.getText().clear();

    }*/



    @Override
    public boolean onSupportNavigateUp() {
        alertDialog();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        alertDialog();
    }

   //check for data changed
    public void alertDialog() {
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert");
        builder.setMessage("You have made some changes. \n Do you want to save the changes before exiting?");

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to close this application ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        overridePendingTransition(R.anim.back_left_to_right, R.anim.back_right_to_left);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        overridePendingTransition(R.anim.back_left_to_right, R.anim.back_right_to_left);
                    }
                })
                .setNeutralButton("Cancel",  new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setCancelable(false);
        alert.setCanceledOnTouchOutside(false);
        alert.show();

    }

}
