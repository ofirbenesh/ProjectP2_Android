package com.example.projectp2_android;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.lifecycle.LiveData;

import com.example.projectp2_android.entities.User;

import java.io.ByteArrayOutputStream;

public class MyApplication extends Application {
    public static Context context;
    public static String loggedUser;
    public static String loggedUserID;
    public static User activeUser;
    public static String loggerUserToken;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        loggedUser = null;
        loggerUserToken = "";
        activeUser = null;
        loggedUserID = null;
    }

    // function to convert photo from db so it can be presented on app
    public static Bitmap decodeBase64ToBitmap(String base64String) {
        // Check and remove data URI scheme part if present.
        String base64Image;
        if (base64String.contains(",")) {
            base64Image = base64String.split(",")[1];
        } else {
            base64Image = base64String; // Use the original string if no data URI scheme is found
        }
        byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }


    // function to convert photo from gallery so it can be moved to Db
    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
