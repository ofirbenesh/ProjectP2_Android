package com.example.projectp2_android;

import com.example.projectp2_android.entities.User;

public interface CallBack {
    void  onSuccess(String token);
    void onFail();
    void userIsReturned(User user);
}