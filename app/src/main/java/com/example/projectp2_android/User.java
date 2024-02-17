package com.example.projectp2_android;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class User implements Serializable {
    private String userName;
    private String userPassword;
    private Uri profilePictureUri;

    public User(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return this.userName;
    }
    public String getUserPassword() {
        return this.userPassword;
    }
    public void setProfilePictureUri(Uri profilePictureUri) {
        this.profilePictureUri = profilePictureUri;
    }

    public Uri getProfilePictureUri() {
        return profilePictureUri;
    }
}

