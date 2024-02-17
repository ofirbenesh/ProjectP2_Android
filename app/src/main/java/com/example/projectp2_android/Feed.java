package com.example.projectp2_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectp2_android.adapters.PostsListAdapter;
import com.example.projectp2_android.entities.Post;

import java.util.List;

public class Feed extends AppCompatActivity {
    private String userName;
    private ImageView profileImageView;
    private Uri profilePictureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        // transffer the user information to feed inorder to show the details.
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");
        profileImageView = findViewById(R.id.profileImageView);
        TextView userNameText = findViewById(R.id.userName);
        Uri img = (Uri) intent.getParcelableExtra("img");
        if (user != null) {
            userName = user.getUserName();
            profilePictureUri = img;
            profileImageView.setImageURI(img);
            userNameText.setText(userName);
        }
        else {
            userNameText.setText("EMPTY USER");
        }
        //region recyclerView
        RecyclerView lstPosts = findViewById(R.id.lstPosts);
        final PostsListAdapter adapter = new PostsListAdapter(this);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));
        List<Post> posts = new JsonFileReader().readPostsFromJson(this, "posts.json");
        adapter.setPosts(posts);
        //endregion
    }
}