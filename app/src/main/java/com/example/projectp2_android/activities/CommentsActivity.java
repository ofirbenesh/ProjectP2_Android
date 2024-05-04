package com.example.projectp2_android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Random;

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
    private List<Comment> listOfComments;
    private Random random = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        RecyclerView lstComments = findViewById(R.id.commentsLst);
        final CommentListAdapter adapter = new CommentListAdapter(this);
        lstComments.setAdapter(adapter);
        lstComments.setLayoutManager(new LinearLayoutManager(this));

        // hardCoded comments list
        listOfComments = new ArrayList<>();
        listOfComments.add(new Comment(1,"Monica geller","Get over it already"));
        listOfComments.add(new Comment(2,"Janice Hosenstein","OH MY GOD"));
        listOfComments.add(new Comment(3,"Joey Tribbiani","How you doin??"));
        listOfComments.add(new Comment(4,"Chandler Bing","Could this BE any more random?"));
        listOfComments.add(new Comment(5, "Ross Geller", "We were on a break!"));
        listOfComments.add(new Comment(6, "Phoebe Buffay", "Smelly Cat, Smelly Cat, what are they feeding you?"));
        listOfComments.add(new Comment(7, "Rachel Green", "It's like all my life everyone has always told me, 'You're a shoe!'"));
        listOfComments.add(new Comment(8, "Gunther", "I thought that picture was good enough to frame!"));
        listOfComments.add(new Comment(9, "Mike Hannigan", "I just bamboozled Chandler!"));
        listOfComments.add(new Comment(10, "Richard Burke", "I've got a moustache!"));

        List<Comment> currComments = new ArrayList<>();

        if (listOfComments.size() > 1) {
            int firstIndex = random.nextInt(listOfComments.size());
            int secondIndex;
            do {
                secondIndex = random.nextInt(listOfComments.size());
            } while (secondIndex == firstIndex); // Ensure the two indices are not the same

            currComments.add(listOfComments.get(firstIndex));
            currComments.add(listOfComments.get(secondIndex));
        }

        adapter.setComments(currComments);

//        // Add new comment
//        Button postCommentBtn = findViewById(R.id.submitComment);
//        EditText inputText = findViewById(R.id.commentInput);
//        postCommentBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                long currentTimeMillis = System.currentTimeMillis();
//                String commentText = Objects.requireNonNull(inputText.getText()).toString();
//                List<Comment> comments = new ArrayList<>();
//                //int profile_pic = getImageResourceFromImageView(profileImageView);
//                Comment comment = new Comment(1, commentsAuthor, commentText);
////                currPost.addComment(comment);
////                adapter.setComments(currPost.getAllComments());
//                listOfComments.add(comment);
//                adapter.setComments(listOfComments);
//            }
//        });

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