package com.example.projectp2_android.webservices;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.projectp2_android.CallBack;
import com.example.projectp2_android.MyApplication;
import com.example.projectp2_android.db.dao.UserDao;
import com.example.projectp2_android.entities.User;
import com.example.projectp2_android.viewmodels.UserViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserAPI {
    private UserDao userDao;
    Retrofit retrofit;
    WebServiceAPI webServiceApi;
    CallBack callback;

    public UserAPI() {
        // constructor
        Gson gson = new GsonBuilder().setLenient().create();
        userDao = MyApplication.usersDB.userDao();
        retrofit = RetrofitBuilder.getInstance();
        webServiceApi = retrofit.create(WebServiceAPI.class);
    }

    public void setCallback(CallBack callback) {
        this.callback = callback;
    }

    public void addUser(String fullname, String email, String username, String password, String profilePic) {

        // create the new User object
        String dbImgFormat = "data:image/jpeg;base64," + profilePic;
        User user = new User(fullname, " ", email, password, username, dbImgFormat);
        Call<JsonObject> call = webServiceApi.createUser(user);

        call.enqueue(new Callback<JsonObject>() {
            // response is received from the server
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // User created successfully
                    user.setProfilePic(profilePic);
                    userDao.insert(user);
                    callback.onSuccess("");
                    // Handle success
                    Log.d("UserCreation", "User created successfully");
                } else {
                    try {
                        // Check if the response from the server is the specific error
                        JsonObject errorBody = new Gson().fromJson(response.errorBody().string(), JsonObject.class);
                        String errorMessage = errorBody.get("error").getAsString();
                        if (errorMessage.equals("One of the fields is occupied")) {
                            showToast("Email or username is already taken.");
                        }
                    } catch (Exception e) {
                        showToast("Error parsing error response.");
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("failure", t.getMessage().toString());
            }

            private void showToast(final String text) {
                new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(MyApplication.context, text, Toast.LENGTH_LONG).show());
            }
        });
    }
    public void deleteUser(String userId) {
        Call<Void> call = webServiceApi.deleteUser(userId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    // Handle success
                    Log.d("UserDeletion", "User deleted successfully");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
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
                    MyApplication.loggedUser = username;

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
                    User returnedUser = response.body();
                    if (!MyApplication.isLogged) {
                        MyApplication.activeUser = response.body();
                        MyApplication.isLogged = true;
                        MyApplication.activeUser.setUserId(userID);
                    }
                callback.userIsReturned(returnedUser);
                }
                else {
                    Log.d("Login", "Login failed: " + response.message());
                    // Handle login failure
                    callback.onFail();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("getUser", "get user failed: " + t.getMessage());
            }
        });
    }
    public User getUser(String userId) {
        RoomUsers roomUsers = new RoomUsers(userDao, userId);
        User user;
        try {
            // executes the RoomUsers task asynchronously
            user = roomUsers.execute().get();
        }
        catch (Exception exception) {
            user = null;
        }
        return user;
    }

    // get user from room database async
    private class RoomUsers extends AsyncTask<Void, Void, User> {
        private UserDao userDao;
        private String userId;

        public RoomUsers(UserDao userDao, String userId) {
            this.userDao = userDao;
            this.userId = userId;
        }

        @Override
        protected User doInBackground(Void... voids) {
            return userDao.get(userId);
        }
    }
}
