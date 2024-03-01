package com.example.projectp2_android.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.projectp2_android.LocalDatabase;
import com.example.projectp2_android.PostDao;
import com.example.projectp2_android.R;
import com.example.projectp2_android.entities.Post;

import java.util.LinkedList;
import java.util.List;

public class PostsRepository {
    private PostDao dao;
    private PostListData postListData;
    public PostsRepository() {
        LocalDatabase db = LocalDatabase.getInstance();
        dao = db.postDao();
        postListData = new PostListData();
    }

    class PostListData extends MutableLiveData<List<Post>> {
        public PostListData() {
            super();
            //setValue(new LinkedList<Post>());

            List<Post> posts = new LinkedList<>();
            posts.add(new Post(1, "Alice", "hello", R.drawable.pic1, R.drawable.profilepic, 5, "today"));
            posts.add(new Post(2, "Alice1", "hello", R.drawable.pic1, R.drawable.profilepic, 5, "today"));
            posts.add(new Post(3, "Alice2", "hello", R.drawable.pic1, R.drawable.profilepic, 5, "today"));
            setValue(posts);
        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(() ->
            {
                // TODO check if dao.get
                postListData.postValue(dao.index());
            }).start();
        }
    }
    public LiveData<List<Post>> getAll() {
         return postListData;
    }
//         public void add
//            (final Post post) {
//         api.add(post);
//
    }
//
//         public void delete
//            (final Post post) {
//         api.delete(post);
//
//    }
//
//         public void reload() {
//         api.get();
//
//    }
//    }
