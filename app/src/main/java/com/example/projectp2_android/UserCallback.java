package com.example.projectp2_android;

import com.example.projectp2_android.entities.User;

public interface UserCallback {
    void onSuccess(User user);
    void onFail(String message);
}
