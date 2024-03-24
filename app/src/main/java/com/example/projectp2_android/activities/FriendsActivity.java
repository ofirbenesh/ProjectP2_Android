package com.example.projectp2_android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.projectp2_android.CallBack;
import com.example.projectp2_android.MyApplication;
import com.example.projectp2_android.R;
import com.example.projectp2_android.adapters.CommentListAdapter;
import com.example.projectp2_android.adapters.FriendsListAdapter;
import com.example.projectp2_android.viewmodels.PostsViewModel;
import com.example.projectp2_android.viewmodels.UserViewModel;
import com.example.projectp2_android.webservices.FriendAPI;
import com.example.projectp2_android.webservices.UserAPI;

public class FriendsActivity extends AppCompatActivity {
    private FriendAPI friendAPI;
    private UserViewModel friendsViewModel;
    private UserViewModel friendRequestsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

//        this.friendAPI = new FriendAPI();

        RecyclerView lstFriends = findViewById(R.id.friendsList);
        final FriendsListAdapter adapter = new FriendsListAdapter(this);
        lstFriends.setAdapter(adapter);
        lstFriends.setLayoutManager(new LinearLayoutManager(this));

        // get friends list from the server
        friendsViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        friendsViewModel.getFriends().observe(this, friends-> {
            adapter.setFriends(friends);
        });
        friendsViewModel.getFriends();

        RecyclerView lstFriendRequests = findViewById(R.id.friendRequestList);
        final FriendsListAdapter adapter_req = new FriendsListAdapter(this);
        lstFriendRequests.setAdapter(adapter_req);
        lstFriendRequests.setLayoutManager(new LinearLayoutManager(this));

        friendRequestsViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        friendRequestsViewModel.getFriendRequests().observe(this, friends-> {
            adapter_req.setFriends(friends);
        });
        friendRequestsViewModel.getFriendRequests();


        // Go back to feed
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}