package com.example.projectp2_android.viewmodels;

import androidx.lifecycle.MutableLiveData;

public class SignUpViewModel {
    private MutableLiveData<String> username;
    private MutableLiveData<String> password;
    private MutableLiveData<String> confirmPassword;
    private MutableLiveData<String> displayName;
    private MutableLiveData<String> profilePic;

    public MutableLiveData<String> getUsername() {
        if (username == null) {
            username = new MutableLiveData<String>();
        }
        return username;
    }

    public MutableLiveData<String> getPassword() {
        if (password == null) {
            password = new MutableLiveData<String>();
        }
        return password;
    }

    public MutableLiveData<String> getConfirmPassword() {
        if (confirmPassword == null) {
            confirmPassword = new MutableLiveData<String>();
        }
        return confirmPassword;
    }
}
