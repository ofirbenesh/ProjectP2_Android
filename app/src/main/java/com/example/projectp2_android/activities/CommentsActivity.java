package com.example.projectp2_android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.projectp2_android.R;
import com.example.projectp2_android.adapters.CommentListAdapter;
import com.example.projectp2_android.adapters.PostsListAdapter;
import com.example.projectp2_android.entities.Comment;
import com.example.projectp2_android.entities.GlobalVariables;
import com.example.projectp2_android.entities.Post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class CommentsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        RecyclerView lstComments = findViewById(R.id.commentsLst);
        final CommentListAdapter adapter = new CommentListAdapter(this);
        lstComments.setAdapter(adapter);
        lstComments.setLayoutManager(new LinearLayoutManager(this));

        // Fetch comments based on the Post ID passed from the previous Activity
        int postId = getIntent().getIntExtra("POST_ID", -1);
        String commentsAuthor = GlobalVariables.userName;
        Post currPost = GlobalVariables.allPosts.get(postId);
        adapter.setComments(currPost.getAllComments());

        // Add new comment
        Button postCommentBtn = findViewById(R.id.submitComment);
        EditText inputText = findViewById(R.id.commentInput);
        postCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentTimeMillis = System.currentTimeMillis();
                String commentText = Objects.requireNonNull(inputText.getText()).toString();
                List<Comment> comments = new ArrayList<>();
                //int profile_pic = getImageResourceFromImageView(profileImageView);
                Comment comment = new Comment(1, commentsAuthor, commentText);
                currPost.addComment(comment);
                adapter.setComments(currPost.getAllComments());
            }
        });

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