package com.example.projectp2_android;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projectp2_android.adapters.PostsListAdapter;
import com.example.projectp2_android.entities.Comment;
import com.example.projectp2_android.entities.GlobalVariables;
import com.example.projectp2_android.entities.Post;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Feed extends AppCompatActivity {
    private String userName;
    private ImageView profileImageView;
    private ImageView newPostImage;
    private Uri profilePictureUri;
    private Uri postImgUri;
    private List<Post> posts;
    private EditText inputText;
    private boolean isDarkMode;
    private static PostsListAdapter adapter;
    private boolean isPhotoAttached;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        this.isDarkMode = false;

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

        //region recyclerView
        RecyclerView lstPosts = findViewById(R.id.lstPosts);
        final PostsListAdapter adapterOnCreate = new PostsListAdapter(this);
        adapter = adapterOnCreate;
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));

        JsonFileReader.readPostsFromJson(this);
        adapter.setPosts(GlobalVariables.allPosts);
        //endregion

        //dark mode
        Button darkModeBtn = findViewById(R.id.darkModeBtn);
        darkModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                if (nightMode == Configuration.UI_MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
            }
        });

        // sign out from app
        Button signOutBtn = findViewById(R.id.signOut_btn);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // adding new post
        Button postButton = findViewById(R.id.publishPost);
        inputText = findViewById(R.id.inputPost);

        // adding picture to post
        Button buttonUploadPhoto = findViewById(R.id.addImage);
        newPostImage = findViewById(R.id.ImgPost);
        buttonUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhotoFromGallery();
            }

        });

        // publish post
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int postID;
                if (posts == null) {
                    postID = 1;
                } else {
                    postID = GlobalVariables.allPosts.size() + 1;
                }
                String date = "Today";
                String postText = Objects.requireNonNull(inputText.getText()).toString();
                Post post = new Post(postID, userName, postText, postImgUri,
                        profilePictureUri, 0, date);
                if (!isPhotoAttached) {
                    post.setPic(R.drawable.blank);
                }
                GlobalVariables.allPosts.add(post);
                adapter.setPosts(GlobalVariables.allPosts);
            }
        });
    }

    // Method to retrieve the resource ID
    // TODO delete if not needed
    public int getImageResourceFromImageView(ImageView imageView) {
        Object tag = imageView.getTag();
        return (tag instanceof Integer) ? (int) tag : -1; // Returns -1 if no resource ID is found
    }


    public void choosePhotoFromGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1); // You can use a constant instead of '1' to make the code more readable.
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) { // Ensure you check for the same requestCode you used to start the activity
            Uri selectedImageUri = data.getData();
            newPostImage.setImageURI(selectedImageUri);

            postImgUri = selectedImageUri;
            isPhotoAttached = true;
        }
    }

}