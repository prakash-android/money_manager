package com.money.manger.view.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.money.manger.R;
import com.money.manger.view.database.DbHelper;
import com.money.manger.view.utils.PreferenceAppHelper;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 101;
    private GoogleSignInClient mGoogleSignInClient;
   // private CallbackManager callbackManager;

    @BindView(R.id.google_sign_in_button)
    SignInButton signInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
       // dbhelper = new DbHelper(this);

        if(PreferenceAppHelper.getLoginedUser()){
            verifySuccess();
        } else {
            //do nothing
        }

        settingUpGoogleSignIn();
    }


    private void verifySuccess() {
        Intent intent = new Intent(LoginActivity.this, MonthlyDateSelectionActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_right_to_left, R.anim.exit_left_to_right);
    }


    @OnClick(R.id.google_sign_in_button)
    public void googleSigninButton(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            if (account != null) {
                account.getGivenName();
                account.getFamilyName();
                handleSocialLogin(1 , account.getId(), account.getDisplayName(), account.getEmail(), "" + account.getPhotoUrl());
                // loginSuccess("");
            }

            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // ...
                        }
                    });

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // ...
                        }
                    });
        }
    }

    private void handleSocialLogin(int s, String id, String displayName, String email, String photoUrl) {

        //setting data to shared pref
        PreferenceAppHelper.setUserId("" + id);
        PreferenceAppHelper.setSocialUser(s);
        PreferenceAppHelper.setUserName("" + displayName);
        PreferenceAppHelper.setUserEmail("" + email);
        PreferenceAppHelper.setUserImage("" + photoUrl);
        PreferenceAppHelper.setLoginedUser(true);

        Intent i = new Intent(this, MonthlyDateSelectionActivity.class);
        startActivity(i);


    }




    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void settingUpGoogleSignIn() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
    }


    @Override
    public void onBackPressed() {
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


/*    GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
if (acct != null) {
        String personName = acct.getDisplayName();
        String personGivenName = acct.getGivenName();
        String personFamilyName = acct.getFamilyName();
        String personEmail = acct.getEmail();
        String personId = acct.getId();
        Uri personPhoto = acct.getPhotoUrl();
    }*/

//    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
//        try {
//            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
//
//            // Signed in successfully, show authenticated UI.
//            if (account != null) {
//
//                //handleSocialLogin("2", account.getId(), account.getDisplayName(), account.getEmail(), "" + account.getPhotoUrl());
//                // loginSuccess("");
//            }
//
//            mGoogleSignInClient.signOut()
//                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            // ...
//                        }
//                    });
//
//        } catch (ApiException e) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//
//            mGoogleSignInClient.signOut()
//                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            // ...
//                        }
//                    });
//        }
//    }


//    public void handleSocialLogin(String type, String tokenId, String name, String email, String imageName) {
//
//        mApiService.socialLogin(tokenId, type, name, email, imageName)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<GoogleSignIn>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        progressDialog.setCanceledOnTouchOutside(true);
//                        progressDialog.setCancelable(false);
//                        progressDialog.setMessage("Loading...");
//                        progressDialog.show();
//                    }
//
//                    @Override
//                    public void onNext(GoogleSignIn googleSignIn) {
//
//                        if (googleSignIn.getSuccess().equals("1")) {
//
//                            if (googleSignIn.getRecord().isEmpty()) {
//                                getView().setEmptyDataMessage();
//                            } else {
//                                PreferenceAppHelper.setApiCode("" + googleSignIn.getRecord().get(0).getApiCode());
//                                PreferenceAppHelper.setUserName("" + googleSignIn.getRecord().get(0).getName());
//                                PreferenceAppHelper.setUserEmail("" + googleSignIn.getRecord().get(0).getEmail());
//                                PreferenceAppHelper.setIntrestedLoginEvent("" + googleSignIn.getRecord().get(0).getInterested_event_types());
//                                PreferenceAppHelper.setUserMobile("" + googleSignIn.getRecord().get(0).getUserMobile());
//                                PreferenceAppHelper.setUserAddress("" + googleSignIn.getRecord().get(0).getUserAddress());
//                                PreferenceAppHelper.setUserImage("" + googleSignIn.getRecord().get(0).getImage());
//                                getView().loginSuccess("" + googleSignIn.getMessage());
//                            }
//                        } else {
//                            getView().loginSuccess("" + googleSignIn.getMessage());
//                        }
//
//                        progressDialog.dismiss();
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                        Log.e("errorResponse", "" + e.getMessage());
//                        progressDialog.dismiss();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        progressDialog.dismiss();
//                    }
//                });
//
//
//    }