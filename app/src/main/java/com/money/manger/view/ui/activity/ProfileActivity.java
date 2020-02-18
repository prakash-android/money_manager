package com.money.manger.view.ui.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.money.manger.R;
import com.money.manger.view.utils.PreferenceAppHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{


    @BindView(R.id.textInputEditTextName)
    EditText userName;

    @BindView(R.id.textInputEditTextEmail)
    EditText userEmail;

    @BindView(R.id.textInputEditTextLoginType)
    EditText loginType;

    @BindView(R.id.profile_image)
    CircleImageView profileImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setValues();
    }


    private void setValues() {

        String social = "";
        if (PreferenceAppHelper.getSocialUser() == 1){
            social = "google";
        } else {
            social = "email";
        }


        userName.setText(PreferenceAppHelper.getUserName());
        userEmail.setText(PreferenceAppHelper.getUserEmail());
        loginType.setText(social);
        try{
            Glide.with(this).load(PreferenceAppHelper.getUserImage()).placeholder(R.drawable.ic_user).error(R.drawable.ic_user).into(profileImage);
        }catch (NullPointerException e){
            Toast.makeText(getApplicationContext(),"image not found", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.log_out)
    public void logoutButton() {

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onBackPressed() {

    }

}
