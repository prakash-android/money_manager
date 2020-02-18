package com.money.manger.view.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.money.manger.MMApplication;
import com.money.manger.R;
import com.money.manger.view.database.DbHelper;
import com.money.manger.view.utils.PreferenceAppHelper;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;


public class NewExpensesActivity extends AppCompatActivity {

    @BindView(R.id.nameEditText)
    EditText nameEditText;

    @BindView(R.id.amtEditText)
    EditText amtEditText;

    @BindView(R.id.name_layout)
    TextInputLayout nameLayout;

    @BindView(R.id.amt_layout)
    TextInputLayout amtLayout;

    @BindView(R.id.itemImageView)
    ImageView itemImageView;


    String dateString;

    DbHelper dbhelper;

    private String[] permission_storage =
            {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private int PERMISSION_REQUEST_CODE = 103;


    private String[] permission_camera =
            {Manifest.permission.CAMERA};
    private int CAMERA_PERMISSION_REQUEST_CODE = 104;

    private File newItemImage = null;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expenses);
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
        dateString = "" + intent.getStringExtra("date");
    }



    @OnClick(R.id.item_image_layout)
    public void addImage() {
        if (checkPermission()) {
            if (checkCameraPermission()) {
                EasyImage.openChooserWithGallery(NewExpensesActivity.this, "Select the image", 0);

            } else {
                if (PreferenceAppHelper.getCameraPermission() == "0") {

                    requestCameraPermission();
                } else {
                    showCameraPermissionDialog();
                }
            }


        } else {
            if (PreferenceAppHelper.getStoragePermission() == "0") {

                requestStoragePermission();
            } else {
                showStoragePermissionDialog();
            }

        }
    }


    // easyImage result action
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {

                newItemImage = imageFile;
                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                Glide.with(NewExpensesActivity.this).load(bitmap).into(itemImageView);
            }

        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //  openDocumentPicker()
                } else if (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(
                        permissions[0]
                )
                ) {

                    Log.e("Never", "Go to Settings and Grant the permission to use this feature.");

                    PreferenceAppHelper.setStoragePermission("1");

                    // User selected the Never Ask Again Option
                } else {

                    PreferenceAppHelper.setStoragePermission("1");
                    Log.e("Denied", "Permission Denied");
                }
            }
        }
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //  openDocumentPicker()
                } else if (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(
                        permissions[0]
                )
                ) {

                    Log.e("Never", "Go to Settings and Grant the permission to use this feature.");

                    PreferenceAppHelper.setCameraPermission("1");

                    // User selected the Never Ask Again Option
                } else {

                    PreferenceAppHelper.setCameraPermission("1");
                    Log.e("Denied", "Permission Denied");
                }
            }
        }


    }


    void showStoragePermissionDialog() {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setIcon(R.drawable.logo).setTitle("Alert");
        builder.setMessage("We need to access your storage to use this feature. Do you want to allow permission now?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
            dialog.dismiss();
        }
        });
        builder.setNegativeButton("Close", null);
        //builder.show();
        AlertDialog dialog = builder.create();
        dialog.show(); //Only after .show() was called
        dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
                .setTextColor(getResources().getColor(R.color.colorPrimary));
        dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(getResources().getColor(R.color.colorPrimary));

    }


    void showCameraPermissionDialog() {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setIcon(R.drawable.logo).setTitle("Alert");
        builder.setMessage("We need to access your Camera to use this feature. Do you want to allow permission now?");
        builder.setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Close", null);
        //builder.show();
        AlertDialog dialog = builder.create();
        dialog.show(); //Only after .show() was called
        dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
                .setTextColor(getResources().getColor(R.color.colorPrimary));
        dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(getResources().getColor(R.color.colorPrimary));

    }

    private Boolean checkPermission() {
        final int result = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        );
        return result == PackageManager.PERMISSION_GRANTED;
    }


    private Boolean checkCameraPermission(){
        final int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }


    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(
                this,
        permission_storage,
                PERMISSION_REQUEST_CODE
        );
    }


    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(
                this,
        permission_camera,
                CAMERA_PERMISSION_REQUEST_CODE
        );
    }




    @OnClick(R.id.add_button)
    public void addButton(){

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

        addNewRow();

    }

    public void addNewRow(){
        boolean queryResult = false;
        queryResult = dbhelper.addCashHistory(""+nameEditText.getText().toString(), Integer.parseInt(amtEditText.getText().toString()), ""+dateString );

        if(queryResult) {
            Toast.makeText(this, "expenses added",Toast.LENGTH_LONG).show();
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
