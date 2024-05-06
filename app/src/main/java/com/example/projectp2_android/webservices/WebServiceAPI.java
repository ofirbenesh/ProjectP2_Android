package com.example.projectp2_android.webservices;

import com.example.projectp2_android.entities.FriendRequestsResponse;
import com.example.projectp2_android.entities.Post;
import com.example.projectp2_android.entities.User;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {

    // POSTS
    @GET("posts")
    Call<List<Post>> getPosts(@Header("Authorization") String authToken);

    @GET("users/{id}/posts")
    Call<List<Post>> getUserPosts(@Path("id") String userId, @Header("Authorization") String authToken);

    @POST("posts")
    Call<Post> createPost(@Header("Authorization") String authToken, @Body JsonObject postBody);

    @PATCH("posts/{postId}")
    Call<Post> updatePost(@Header("Authorization") String authToken, @Path("postId") String postId, @Body Post post);

    @DELETE("posts/{postId}")
    Call<Void> deletePost(@Header("Authorization") String authToken, @Path("postId") String postId);



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
    Call<Void> sendFriendRequest(@Header("Authorization") String token, @Path("id") String userId, @Body JsonObject requestBody);

    @GET("users/{id}/friend-requests/android")
    Call<List<User>> getFriendsRequestAndroid(@Header("Authorization") String token, @Path("id") String userId);

    @PATCH("users/{id}/friends/{fid}")
    Call<Void> approveFriendRequest(@Header("Authorization") String token, @Path("id") String userId, @Path("fid") String friendId);

    @DELETE("users/{id}/friend-requests/{fid}")
    Call<Void> removeFriendRequest(@Header("Authorization") String token, @Path("id") String userId, @Path("fid") String friendId);

    @DELETE("users/{id}/friends/{fid}")
    Call<Void> removeFriend(@Header("Authorization") String token, @Path("id") String userId, @Path("fid") String friendId);
}
