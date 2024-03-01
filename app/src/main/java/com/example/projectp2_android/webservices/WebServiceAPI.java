package com.example.projectp2_android.webservices;

import com.example.projectp2_android.entities.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {
    @GET("posts")
    Call<List<Post>> getPosts();

    @POST("posts")

    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);
}
