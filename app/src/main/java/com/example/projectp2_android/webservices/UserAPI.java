package com.example.projectp2_android.webservices;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.projectp2_android.CallBack;
import com.example.projectp2_android.MyApplication;
import com.example.projectp2_android.R;
import com.example.projectp2_android.db.dao.PostDao;
import com.example.projectp2_android.db.dao.UserDao;
import com.example.projectp2_android.entities.Post;
import com.example.projectp2_android.entities.User;
import com.example.projectp2_android.viewmodels.UserViewModel;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UserAPI {
    private UserDao userDao;
    Retrofit retrofit;
    WebServiceAPI webServiceApi;
    CallBack callback;

    public UserAPI() {
        retrofit = RetrofitBuilder.getInstance();
        webServiceApi = retrofit.create(WebServiceAPI.class);
    }

    public void setCallback(CallBack callback) {
        this.callback = callback;
    }

    public void addUser(String email, String username, String password, String profilePic) {

        // create the new User object
        String dbImgFormat = "data:image/jpeg;base64," + profilePic;
        User user = new User("first","last", email ,password, username, dbImgFormat);
        Call<JsonObject> call = webServiceApi.createUser(user);

        call.enqueue(new Callback<JsonObject>() {
            // response is received from the server
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                Log.d("failure", response.message().toString());
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    // add new user to the room database
                    //user.setProfilePic(profilePic);
                    userDao.insert(user);
                    //callback.onSuccess();
                    // Handle success
                    Log.d("UserCreation", "User created successfully");
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("failure", t.getMessage().toString());
            }
        });
    }
    public void loginUser(String username, String password, UserViewModel viewModel) {
        JsonObject credentials = new JsonObject();
        credentials.addProperty("username", username);
        credentials.addProperty("password", password);

        Call<JsonObject> call = webServiceApi.loginUser(credentials);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().get("token").getAsString();
                    MyApplication.loggerUserToken = token;
                    viewModel.updateToken(token);
                    Log.d("Login", "Token: " + token);
                    // Proceed with login
                    callback.onSuccess(token);
                } else {
                    Log.d("Login", "Login failed: " + response.message());
                    // Handle login failure
                    callback.onFail();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.d("Login", "Login error: " + t.getMessage());
                // Handle error in login attempt
            }
        });
    }

    public void getUserById(String userID) {

        String authToken = "Bearer " + MyApplication.loggerUserToken;
        Call<User> call = webServiceApi.getUserById(authToken, userID);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User activeUser = response.body();
                    MyApplication.activeUser = activeUser;
//                callback.onSuccess(activeUser);
                }
                else {
                    Log.d("Login", "Login failed: " + response.message());
                    // Handle login failure
//                    callback.onFail();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {}
        });
    }



}
