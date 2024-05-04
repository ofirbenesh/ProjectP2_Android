package com.example.projectp2_android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.projectp2_android.CallBack;
import com.example.projectp2_android.MyApplication;
import com.example.projectp2_android.R;
import com.example.projectp2_android.adapters.CommentListAdapter;
import com.example.projectp2_android.adapters.FriendsListAdapter;
import com.example.projectp2_android.entities.User;
import com.example.projectp2_android.viewmodels.PostsViewModel;
import com.example.projectp2_android.viewmodels.UserViewModel;
import com.example.projectp2_android.webservices.FriendAPI;
import com.example.projectp2_android.webservices.UserAPI;

import java.util.List;

public class FriendsActivity extends AppCompatActivity {
    private FriendAPI friendAPI;
    private UserViewModel friendsViewModel;
    private UserViewModel friendRequestsViewModel;
    private static FriendsListAdapter adapter;
    private static FriendsListAdapter adapter_req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

//        this.friendAPI = new FriendAPI();

        // get friends list from the server
        friendsViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        RecyclerView lstFriends = findViewById(R.id.friendsList);
        adapter = new FriendsListAdapter(this, false, friendsViewModel);
        lstFriends.setAdapter(adapter);
        lstFriends.setLayoutManager(new LinearLayoutManager(this));

        friendsViewModel.getFriends().observe(this, friends-> {
            Log.d("FriendsActivity", "Friends list updated: " + friends.size() + " items.");
            adapter.setFriends(friends);
        });
        List<User> friendsList = friendsViewModel.getFriends().getValue();
        adapter.setFriends(friendsList);
        MyApplication.activeUserFriends = friendsList;

        friendRequestsViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        RecyclerView lstFriendRequests = findViewById(R.id.friendRequestList);
        adapter_req = new FriendsListAdapter(this, true, friendRequestsViewModel);
        lstFriendRequests.setAdapter(adapter_req);
        lstFriendRequests.setLayoutManager(new LinearLayoutManager(this));

        friendRequestsViewModel.getFriendRequests().observe(this, friends-> {
            adapter_req.setFriends(friends);
        });
        adapter_req.setFriends(friendRequestsViewModel.getFriendRequests().getValue());

        // Go back to feed
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        adapter.notifyDataSetChanged();
//    }
}