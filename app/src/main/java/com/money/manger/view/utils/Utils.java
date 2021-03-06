package com.money.manger.view.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {


    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static File convertCustomFileSize(File file, int maxSize, String imageSavedFileName) {
        Bitmap image = BitmapFactory.decodeFile(file.getAbsolutePath());
        File fileOut = null;


        float width = image.getWidth();
        float height = image.getHeight();

        float bitmapRatio = width / height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (height * bitmapRatio);
        }

        Bitmap cBitmap = Bitmap.createScaledBitmap(image, Math.round(width), Math.round(height), true);

        try {
            String file_path =
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/mm";
            File dir = new File(file_path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            fileOut = new File(dir, "" + imageSavedFileName + "" + ".png");
            FileOutputStream fOut = new FileOutputStream(fileOut);
            cBitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();

        } catch (Exception e) {
            Log.e("Exceptione", "" + e.getMessage());
        }
        return fileOut;
    }


    public static File convertCustomImageSize(Bitmap image, int maxSize, String imageSavedFileName) {

        File fileOut = null;


        float width = image.getWidth();
        float height = image.getHeight();

        float bitmapRatio = width / height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (height * bitmapRatio);
        }

        Bitmap cBitmap = Bitmap.createScaledBitmap(image, Math.round(width), Math.round(height), true);

        try {
            String file_path =
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/mm";
            File dir = new File(file_path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            fileOut = new File(dir, "" + imageSavedFileName + "" + ".png");
            FileOutputStream fOut = new FileOutputStream(fileOut);
            cBitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();

        } catch (Exception e) {
            Log.e("Exceptione", "" + e.getMessage());
        }
        return fileOut;
    }


    // For Checking Internet Availability....
    public static Boolean isInternetAvailable(Context c) {

        ConnectivityManager cm = (ConnectivityManager) c
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return true;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return true;
        }
        return false;
    }

    /**
     * add following to work this function:
     *  vectorDrawables.useSupportLibrary = true in defaultConfig gradle file
     *
     * for vector support for less then API 21, add the following to onCreate:
     * AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
     */
    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static byte[] getImageBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }



    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

}
