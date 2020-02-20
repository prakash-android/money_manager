package com.money.manger.view.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.money.manger.view.utils.PreferenceAppHelper;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {

        super.onNewToken(s);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Log.e("regToken", "" + refreshedToken);

        PreferenceAppHelper.setFcmToken(refreshedToken);

    }
}
