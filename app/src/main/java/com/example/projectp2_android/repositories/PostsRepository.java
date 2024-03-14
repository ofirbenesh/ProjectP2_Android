package com.example.projectp2_android.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.projectp2_android.MyApplication;
import com.example.projectp2_android.db.LocalDatabase;
import com.example.projectp2_android.db.dao.PostDao;
import com.example.projectp2_android.R;
import com.example.projectp2_android.entities.Post;
import com.example.projectp2_android.webservices.PostAPI;

import java.util.LinkedList;
import java.util.List;

public class PostsRepository {
    private PostDao dao;
    private PostListData postListData;
    private PostAPI api;

    public PostsRepository() {
        LocalDatabase db = LocalDatabase.getInstance();
        dao = db.postDao();
        postListData = new PostListData();
        api = new PostAPI(postListData, dao);
    }

    class PostListData extends MutableLiveData<List<Post>> {
        public PostListData() {
            super();
            setValue(new LinkedList<Post>());

//            List<Post> posts = new LinkedList<>();
//            posts.add(new Post(1, "Alice", "hello", R.drawable.pic1, R.drawable.profilepic, 5, "today"));
//            posts.add(new Post(2, "Alice1", "hello", R.drawable.pic1, R.drawable.profilepic, 5, "today"));
//            posts.add(new Post(3, "Alice2", "hello", R.drawable.pic1, R.drawable.profilepic, 5, "today"));
//            setValue(posts);
        }

        @Override
        protected void onActive() {
            super.onActive();

            if (api == null) {
                api = new PostAPI(postListData, dao);
            }
            api.get(postListData);
//            new Thread(() ->
//            {
//                // TODO check if dao.get
//                postListData.postValue(dao.index());
//            }).start();
        }
    }

    public LiveData<List<Post>> getAll() {
        return postListData;
    }

    public void add(final Post post) {
        api.addPost(post);
    }
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
