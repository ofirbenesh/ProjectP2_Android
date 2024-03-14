package com.example.projectp2_android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.projectp2_android.MyApplication;
import com.example.projectp2_android.R;
import com.example.projectp2_android.adapters.CommentListAdapter;
import com.example.projectp2_android.adapters.FriendsListAdapter;
import com.example.projectp2_android.webservices.FriendAPI;
import com.example.projectp2_android.webservices.UserAPI;

public class FriendsActivity extends AppCompatActivity {
    private FriendAPI friendAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        this.friendAPI = new FriendAPI();

        RecyclerView lstFriends = findViewById(R.id.friendsList);
        final FriendsListAdapter adapter = new FriendsListAdapter(this);
        lstFriends.setAdapter(adapter);
        lstFriends.setLayoutManager(new LinearLayoutManager(this));

        // get friends list from server
        friendAPI.getFriends();

    }
}