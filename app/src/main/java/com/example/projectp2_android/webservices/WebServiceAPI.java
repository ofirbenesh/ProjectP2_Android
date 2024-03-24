package com.example.projectp2_android.webservices;

import com.example.projectp2_android.entities.Post;
import com.example.projectp2_android.entities.User;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {

    // POSTS
    @GET("posts")
    Call<List<Post>> getPosts(@Header("Authorization") String authToken);

    @POST("posts")
    Call<Post> createPost(@Header("Authorization") String authToken, @Body JsonObject postBody);

    @POST("posts/{id}")
    Call<Post> updatePost(@Path("id") String postId, @Body JsonObject requestBody);

    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") String id);

    // USERS
    @GET("users/{id}")
    Call<User> getUserById(@Header("Authorization") String token, @Path("id") String userId);

    @DELETE("users/{id}")
    Call<Void> deleteUser(@Path("id") String id);

    @POST("users")
    Call<JsonObject> createUser(@Body User user);
    @POST("tokens")
    Call<JsonObject> loginUser(@Body JsonObject credentials);

    // FRIENDS
    @GET("users/{id}/friends")
    Call<List<User>> getFriends(@Header("Authorization") String token, @Path("id") String userId);

    @POST("users/{id}/friends")
    Call<Void> sendFriendRequest(@Header("Authorization") String token, @Path("id") String userId, @Path("id") String friendId);

    @GET("users/{id}/friends/requests")
    Call<List<User>> getFriendRequests(@Header("Authorization") String authToken);

    @POST("users/{id}/friends/approve/{fid}")
    Call<Void> approveFriendRequest(@Path("id") String userId, @Path("fid") String friendId);

    @DELETE("users/{id}/friends/removeRequest/{fid}")
    Call<Void> removeFriendRequest(@Path("id") String userId, @Path("fid") String friendId);

    @DELETE("users/{id}/friends/{fid}")
    Call<Void> removeFriend(@Path("id") String userId, @Path("fid") String friendId);
}
