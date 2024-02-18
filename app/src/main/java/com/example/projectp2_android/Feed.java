package com.example.projectp2_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectp2_android.adapters.PostsListAdapter;
import com.example.projectp2_android.entities.Comment;
import com.example.projectp2_android.entities.Post;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Feed extends AppCompatActivity {
    private String userName;
    private ImageView profileImageView;
    private Uri profilePictureUri;
    private List<Post> posts;
    private EditText inputText;

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
            userNameText.setText(userName);
            if (img != null) {
                profilePictureUri = img;
                profileImageView.setImageURI(img);
            }
        }
        else {
            userNameText.setText("EMPTY USER");
        }

        // add new post
        Button postButton = findViewById(R.id.publishPost);
        //region recyclerView
        RecyclerView lstPosts = findViewById(R.id.lstPosts);
        final PostsListAdapter adapter = new PostsListAdapter(this);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));


        posts = new ArrayList<>();
        posts.add(new Post("jhon doe", "I made a cake", R.drawable.cake, 4));
        posts.add(new Post("mike ross", "hello world", R.drawable.fac_bg, 13));
        posts.add(new Post("Jenny dom", "beautiful", R.drawable.sunset, 21));

        //List<Post> posts = new JsonFileReader().readPostsFromJson(this, "posts.json");
        adapter.setPosts(posts);
        //endregion
        inputText = findViewById(R.id.inputPost);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //inputPostImage = findViewById(R.id.inputImage);
                //inputPostImage.setImageResource(R.drawable.like2);
                int postID;
                if (posts == null) {
                    postID = 1;
                } else {
                    postID = posts.size() + 1;
                }
                long currentTimeMillis = System.currentTimeMillis();
                Date date = new Date(currentTimeMillis);
                String postText = Objects.requireNonNull(inputText.getText()).toString();
                List<Comment> comments = new ArrayList<>();
                // savedImageResource = getImageResourceFromImageView(inputPostImage);
                Post post = new Post(userName, inputText.getText().toString(), R.drawable.cake, 0);
                posts.add(post);
                adapter.setPosts(posts);
            }
        });
    }
}