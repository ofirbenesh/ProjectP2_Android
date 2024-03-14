package com.example.projectp2_android.webservices;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.projectp2_android.CallBack;
import com.example.projectp2_android.MyApplication;
import com.example.projectp2_android.db.dao.PostDao;
import com.example.projectp2_android.R;
import com.example.projectp2_android.entities.Post;
import com.google.gson.JsonObject;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostAPI {
    private MutableLiveData<List<Post>> postListData;
    private PostDao dao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    CallBack callback;

    public PostAPI(MutableLiveData<List<Post>> postListData, PostDao dao) {
        this.postListData = postListData;
        this.dao = dao;

        // TODO connect URL to FooServer
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.serverUrl))
                .addConverterFactory(GsonConverterFactory.create()).build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        }
    public void setCallback(CallBack callback) {
        this.callback = callback;
    }
    public void get(MutableLiveData<List<Post>> posts) {

        String authToken = "Bearer " + MyApplication.loggerUserToken;
        Call<List<Post>> call = webServiceAPI.getPosts(authToken);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                postListData.postValue(response.body());
//                new Thread(() -> {
////                    dao.insert(posts);
//                    postListData.postValue(dao.index());
//                }).start();
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {}
        });
    }

    public void addPost(Post post) {
        JsonObject postBody = new JsonObject();
        postBody.addProperty("userID", post.getUserID());
        postBody.addProperty("content", post.getContent());
        postBody.addProperty("photo", post.getPhoto());
        postBody.addProperty("author", post.getAuthor());

        String authToken = "Bearer " + MyApplication.loggerUserToken;

        Call<Post> call = webServiceAPI.createPost(authToken, postBody);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    // Optionally update your local database or LiveData here
                    Log.d("AddPost", "Post added successfully");
                    callback.onSuccess("Post added successfully");

                } else {
                    Log.d("AddPost", "Failed to add post");
                    callback.onFail();
                }
        }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.d("AddPost", "Error adding post: " + t.getMessage());
                callback.onFail();
            }
        });
    }

}
