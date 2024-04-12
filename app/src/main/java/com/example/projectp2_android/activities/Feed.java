package com.example.projectp2_android.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projectp2_android.MyApplication;
import com.example.projectp2_android.db.LocalDatabase;
import com.example.projectp2_android.R;
import com.example.projectp2_android.db.dao.PostDao;
import com.example.projectp2_android.entities.User;
import com.example.projectp2_android.adapters.PostsListAdapter;
import com.example.projectp2_android.entities.GlobalVariables;
import com.example.projectp2_android.entities.Post;
import com.example.projectp2_android.viewmodels.PostsViewModel;
import com.example.projectp2_android.viewmodels.UserViewModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Feed extends AppCompatActivity {
    private String userName;
    private ImageView profileImageView;
    private ImageView newPostImage;
    private RecyclerView lstPosts;
    private Uri profilePictureUri;
    private Uri postImgUri;
    private List<String> posts;
    private List<Post> dbPosts;
    private EditText inputText;
    private PostDao postDao;
    private static PostsListAdapter adapter;
    private boolean isPhotoAttached;
    private PostsViewModel viewModel;
    private UserViewModel userViewModel;
    private LocalDatabase db;
    private String imageBase64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_feed);

        // transfer the user information to feed inorder to show the details.
        profileImageView = findViewById(R.id.profileImageView);
        TextView userNameText = findViewById(R.id.userName);
        userNameText.setText(MyApplication.loggedUser);
        String profilePicBase64 = MyApplication.activeUser.getProfilePhoto();
        Bitmap profileBitmap = MyApplication.decodeBase64ToBitmap(profilePicBase64);
        profileImageView.setImageBitmap(profileBitmap);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        db = Room.databaseBuilder(getApplicationContext(), LocalDatabase.class, "FooDB")
                .allowMainThreadQueries().build();
        postDao = db.postDao();

        //region recyclerView
        lstPosts = findViewById(R.id.lstPosts);
        final PostsListAdapter adapterOnCreate = new PostsListAdapter(this, userViewModel);
        adapter = adapterOnCreate;
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));
//        loadPosts();

        viewModel = new ViewModelProvider(this).get(PostsViewModel.class);
        viewModel.get().observe(this, posts -> {
            adapter.setPosts(posts);
        });
        viewModel.get();

//        handlePosts();

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
                if (imageBase64 == null) {
                    imageBase64 = "";
                }
                Post post = new Post(MyApplication.loggedUserID, inputText.getText().toString(), imageBase64, MyApplication.loggedUser);
                viewModel.add(post);
                loadPosts();
            }
        });

        // move to friends activity
        ImageButton friendsButton = findViewById(R.id.friendsBtn);
        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Feed.this, FriendsActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadPosts();
    }

    private void loadPosts() {
//        posts.clear();
        dbPosts = postDao.index();
        adapter.setPosts(dbPosts);

        adapter.notifyDataSetChanged();
    }

    private void handlePosts() {

        //region recyclerView
        lstPosts = findViewById(R.id.lstPosts);
        final PostsListAdapter adapterOnCreate = new PostsListAdapter(this, userViewModel);
        adapter = adapterOnCreate;
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));
        loadPosts();
    }

    public void choosePhotoFromGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1); // You can use a constant instead of '1' to make the code more readable.
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap imageBitmap = null;
        if (resultCode == RESULT_OK && requestCode == 1) { // Ensure you check for the same requestCode you used to start the activity
            Uri selectedImageUri = data.getData();
            newPostImage.setImageURI(selectedImageUri);

            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (imageBitmap != null) {
            imageBase64 = MyApplication.bitmapToBase64(imageBitmap);
        }
    }
}