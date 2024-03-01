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

         public PostsViewModel () {
         repository = new PostsRepository();
         posts = repository.getAll();
         }

         public LiveData<List<Post>> get() { return posts; }
//
//         public void add(Post post) { repository.add(post); }
//
//         public void delete(Post post) { repository.delete(post); }
//
//         public void reload() { repository.reload(); }
 }
