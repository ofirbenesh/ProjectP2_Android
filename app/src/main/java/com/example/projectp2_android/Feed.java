package com.example.projectp2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Feed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        // transffer the user information to feed inorder to show the details.
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");
    }
}