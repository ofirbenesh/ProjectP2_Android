package com.example.projectp2_android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectp2_android.CallBack;
import com.example.projectp2_android.MyApplication;
import com.example.projectp2_android.R;
import com.example.projectp2_android.adapters.PostsListAdapter;
import com.example.projectp2_android.entities.User;
import com.example.projectp2_android.viewmodels.PostsViewModel;
import com.example.projectp2_android.viewmodels.UserViewModel;
import com.example.projectp2_android.webservices.UserAPI;

import java.util.ArrayList;

public class UserProfileActivity extends AppCompatActivity implements CallBack {
    private UserAPI userApi;
    private ImageView profileImageView;
    private RecyclerView lstPosts;
    private PostsListAdapter adapter;
    private String userId;
    private TextView userNameText;
    private Bitmap profileBitmap;
    private PostsViewModel viewModel;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        this.userApi = new UserAPI();
        this.userApi.setCallback(this);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        profileImageView = findViewById(R.id.profileImageView);
        userNameText = findViewById(R.id.userName);

        // Go back to feed
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // present user's information
        this.userId = getIntent().getStringExtra("userId");
        userApi.getUserById(userId);

        if (MyApplication.isFriendOf(userId))
        {
            presentUserPosts();
        }
    }

    @Override
    public void onSuccess(String token) {

    }

    @Override
    public void onFail() {

    }

    @Override
    public void userIsReturned(User user) {
        String profilePicBase64 = user.getProfilePic();
        if (profilePicBase64 != null && !profilePicBase64.equals("")) {
            profileBitmap = MyApplication.decodeBase64ToBitmap(profilePicBase64);
        }
        if (profileBitmap != null) {
            profileImageView.setImageBitmap(profileBitmap);
        }
        String userName = user.getFirstName() + " " + user.getLastName();
        userNameText.setText(userName);
    }

    public void presentUserPosts() {

        //region recyclerView
        lstPosts = findViewById(R.id.lstPosts);
        final PostsListAdapter adapterOnCreate = new PostsListAdapter(this, userViewModel);
        adapter = adapterOnCreate;
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(PostsViewModel.class);
        viewModel.getUserPosts(userId).observe(this, posts -> {
            adapter.setPosts(posts);
            adapter.notifyDataSetChanged();  // Notify the adapter to refresh the view
        });
        viewModel.get();
    }
}