package com.money.manger.view.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.money.manger.MMApplication;

public class PreferenceAppHelper {

    private static final String PREFS_NAME = "MM_USER";
    private static final String USER_ID = "user_id";
    private static final String USER_NAME = "user_name";
    private static final String USER_EMAIL = "user_email";
    private static final String USER_IMAGE = "user_image";
    private static final String SOCIAL_USER = "social_user";

    private static final String STORAGE_PERMISSION = "storage_permission";
    private static final String CAMERA_PERMISSION = "camera_permission";

    private static final String LOGINED_USER = "logined_user";

    private static final String FCM_TOKEN = "fcm_token";


    private static SharedPreferences mSharedPreferences = null;

    public static void setStoragePermission(String status) {
        setStringInPrefs(STORAGE_PERMISSION, status);
    }

    public static String getStoragePermission() {
        return mSharedPreferences.getString(STORAGE_PERMISSION, "0");
    }
    public static void setCameraPermission(String status) {
        setStringInPrefs(CAMERA_PERMISSION, status);
    }

    public static String getCameraPermission() {
        return mSharedPreferences.getString(CAMERA_PERMISSION, "0");
    }

    public static String getFcmToken() {
        return getSharedPreference().getString(FCM_TOKEN, "null");
    }

    public static void setFcmToken(String fcmToken) {
        setStringInPrefs(FCM_TOKEN, fcmToken);
    }

    public static void setLoginedUser(boolean loginedUser) {
        setBooleanInPrefs(LOGINED_USER, loginedUser);
    }

    public static Boolean getLoginedUser() {
        return getSharedPreference().getBoolean(LOGINED_USER, false);
    }

    public static String getUserId() {
        return getSharedPreference().getString(USER_ID, "null");
    }

    public static void setUserId(String apiCode) {
        setStringInPrefs(USER_ID, apiCode);
    }

    public static String getUserName() {
        return getSharedPreference().getString(USER_NAME, "null");
    }

    public static void setUserName(String userName) {
        setStringInPrefs(USER_NAME, userName);
    }

    public static String getUserEmail() {
        return getSharedPreference().getString(USER_EMAIL, "null");
    }

    public static void setUserEmail(String userEmail) {
        setStringInPrefs(USER_EMAIL, userEmail);
    }

    public static int getSocialUser() {
        return getSharedPreference().getInt(SOCIAL_USER,0);
    }

    public static void setSocialUser(int socialUser) {
        setIntInPrefs(SOCIAL_USER, socialUser);
    }

    public static String getUserImage() {
        return getSharedPreference().getString(USER_IMAGE, "null");
    }

    public static void setUserImage(String userImage) {
        setStringInPrefs(USER_IMAGE, userImage);
    }

    private static void setStringInPrefs(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putString(key, value);
        editor.apply();
    }

    private static void setBooleanInPrefs(String key, Boolean value) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private static void setIntInPrefs(String key, int value) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putInt(key, value);
        editor.apply();
    }


    private static SharedPreferences getSharedPreference() {
        if (mSharedPreferences == null) {
            mSharedPreferences = MMApplication.getAppContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }
}