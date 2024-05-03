package com.example.projectp2_android;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.projectp2_android.db.UsersDB;
import com.example.projectp2_android.entities.User;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class MyApplication extends Application {
    public static Context context;
    public static String loggedUser;
    public static String loggedUserID;
    public static User activeUser;
    public static String loggerUserToken;
    public static boolean isLogged;
    public static List<User> activeUserFriends;
    public static UsersDB usersDB;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        loggedUser = null;
        loggerUserToken = "";
        activeUser = null;
        loggedUserID = null;
        activeUserFriends = null;
        isLogged = false;
        usersDB = Room.databaseBuilder(context, UsersDB.class, "user")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
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

    public static String getUserIdFromToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length == 3) {
                String base64Payload = parts[1];
                byte[] decodedBytes = Base64.decode(base64Payload, Base64.URL_SAFE);
                String payload = new String(decodedBytes, "UTF-8");
                JSONObject jsonPayload = new JSONObject(payload);
                MyApplication.loggedUserID = jsonPayload.getString("id");
                return jsonPayload.getString("id"); // Use "id" to match your token structure
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Token is invalid or userId not found
    }
    public static boolean isFriendOf(String userId) {
        for (User friend : activeUserFriends) {
            if (friend.getUserId().equals(userId)) {
                return true; // Found the user in the friends list
            }
        }
        return false; // User not found in the friends list
    }
}
