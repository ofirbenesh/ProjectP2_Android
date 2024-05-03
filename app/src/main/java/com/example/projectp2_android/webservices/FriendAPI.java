package com.example.projectp2_android.webservices;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.projectp2_android.CallBack;
import com.example.projectp2_android.MyApplication;
import com.example.projectp2_android.db.dao.UserDao;
import com.example.projectp2_android.entities.FriendRequestsResponse;
import com.example.projectp2_android.entities.Post;
import com.example.projectp2_android.entities.User;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FriendAPI {
    private MutableLiveData<List<User>> friendsListData;
    private MutableLiveData<List<User>> friendRequestsListData;
    Retrofit retrofit;
    WebServiceAPI webServiceApi;
    CallBack callback;
    public FriendAPI(MutableLiveData<List<User>> friendsListData, MutableLiveData<List<User>> friendRequestsListData) {
        retrofit = RetrofitBuilder.getInstance();
        webServiceApi = retrofit.create(WebServiceAPI.class);
        this.friendsListData = friendsListData;
        this.friendRequestsListData = friendRequestsListData;
    }

    public void getFriends() {
        String authToken = "Bearer " + MyApplication.loggerUserToken;
        String userId = MyApplication.loggedUserID;
        Call<List<User>> call = webServiceApi.getFriends(authToken, userId);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> friends = response.body();
                    MyApplication.activeUserFriends = friends;
                    friendsListData.postValue(response.body());
                    // pictures if friends
//                    for (User friend : friends) {
//                        String profilePicture = friend.getProfilePic();
//                        if (profilePicture.startsWith("data:image/jpeg;base64,")) {
//                            // Remove the prefix
//                            profilePicture = profilePicture.substring("data:image/jpeg;base64,".length());
//                            contact.setProfilePic(profilePicture);
//                        }
                        // dao
//                    }
//                    callback.onSuccess("");
                } else {
                    // Handle the case where the response is not successful
//                    callback.onFail();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                // Pass the error to the callback
//                callback.onFail();
            }
        });
    }

    public void sendFriendRequest(String friendID) {

        String authToken = "Bearer " + MyApplication.loggerUserToken;
        String userId = MyApplication.loggedUserID;
        JsonObject friendBody = new JsonObject();
        friendBody.addProperty("userId", friendID);
        Call<Void> call =  webServiceApi.sendFriendRequest(authToken, userId, friendBody);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("friendRequest", "friend request sent");
                    // update local db
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("friendRequest", "failed to send friend request");
            }
        });
    }

    public void getFriendRequests() {

        String authToken = "Bearer " + MyApplication.loggerUserToken;
        String userId = MyApplication.loggedUserID;
        Call<List<User>> call = webServiceApi.getFriendsRequestAndroid(authToken, userId);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
//                    MyApplication.activeUser.setFriendRequests(response.body());
                    if (response.body() != null) {
//                        MyApplication.activeUser.setFriendRequests(response.body());
//                        List<String> friendRequests = new ArrayList<>();
//                        friendRequests = response.body().getPendingRequests();
                        friendRequestsListData.postValue(response.body());
                        Log.d("friendRequestsList", "request list recived");
                        // update local db
                    }
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("friendRequest", "falied to send friend requests");
            }
        });
    }
    public void approveFriendRequest(String friendID) {

        String authToken = "Bearer " + MyApplication.loggerUserToken;
        String userId = MyApplication.loggedUserID;
        Call<Void> call = webServiceApi.approveFriendRequest(authToken, userId, friendID);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("friendRequest", "friend was added to friends list");
                    getFriendRequests();
                    // update local db
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("friendRequest", "failed to approve friend request");
            }
        });
    }

    public void removeFriend(String friendID) {

        String authToken = "Bearer " + MyApplication.loggerUserToken;
        String userId = MyApplication.loggedUserID;
        Call<Void> call = webServiceApi.removeFriend(authToken, userId, friendID);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("friends", "friend was removed from friends list");
                    getFriends();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("friends", "failed to remove friend");
            }
        });
    }

    public void removeFriendRequest(String friendID) {

        String authToken = "Bearer " + MyApplication.loggerUserToken;
        String userId = MyApplication.loggedUserID;
        Call<Void> call = webServiceApi.removeFriendRequest(authToken, userId, friendID);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("friends", "friend request was removed");
                    getFriendRequests();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("friends", "failed to remove friend request");
            }
        });
    }
}
