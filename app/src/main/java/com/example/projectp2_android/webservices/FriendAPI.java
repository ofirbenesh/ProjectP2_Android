package com.example.projectp2_android.webservices;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.projectp2_android.CallBack;
import com.example.projectp2_android.MyApplication;
import com.example.projectp2_android.db.dao.UserDao;
import com.example.projectp2_android.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FriendAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceApi;
    CallBack callback;
    public FriendAPI() {
        retrofit = RetrofitBuilder.getInstance();
        webServiceApi = retrofit.create(WebServiceAPI.class);
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
                    // new implementation:
                    for (User contact : friends) {
                        String profilePicture = contact.getProfilePic();
                        if (profilePicture.startsWith("data:image/jpeg;base64,")) {
                            // Remove the prefix
                            profilePicture = profilePicture.substring("data:image/jpeg;base64,".length());
                            contact.setProfilePic(profilePicture);
                        }
                        // dao
                    }
//                    contacts.postValue(contactDao.getAllContacts());
                    // Pass the friends list to the callback
                    callback.onSuccess("");
                } else {
                    // Handle the case where the response is not successful
                    callback.onFail();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                // Pass the error to the callback
                callback.onFail();
            }
        });
    }

    public void sendFriendRequest(String friendID) {
        String authToken = "Bearer " + MyApplication.loggerUserToken;
        String userId = MyApplication.loggedUserID;
        Call<Void> call = webServiceApi.sendFriendRequest(userId, friendID);
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
                Log.d("friendRequest", "falied to send friend request");
            }
        });
    }
}
