package com.example.projectp2_android.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectp2_android.entities.Post;
import com.example.projectp2_android.repositories.PostsRepository;

import java.util.List;

public class PostsViewModel extends ViewModel {
         private PostsRepository repository;

         private LiveData<List<Post>> posts;
    private LiveData<List<Post>> userPosts;

         public PostsViewModel () {
         repository = new PostsRepository();
         posts = repository.getAll();
         }

         public LiveData<List<Post>> get() { return posts; }

         public void add(Post post) { repository.add(post); }

    public void updatePost(Post post) {
        repository.updatePost(post);
    }

    public void deletePost(Post post) {
        repository.deletePost(post);
    }

    public LiveData<List<Post>> getUserPosts(String userId) {
        userPosts = repository.getUserPosts(userId);
        return userPosts;
    }
 }
