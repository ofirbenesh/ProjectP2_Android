package com.example.projectp2_android.entities;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
@Entity
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String username;
    private String profilePhoto;

    @Ignore
    private List<User> friends = new ArrayList<>();
    @Ignore
    private List<User> friendRequests = new ArrayList<>();

    @Ignore
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String firstName, String lastName, String email, String password, String username, String profilePhoto) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.username = username;
        this.profilePhoto = profilePhoto;
    }
    public int getId() { return id; }

    public void setId(@NonNull int id) { this.id = id; }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getProfilePic() { return profilePhoto; }

    public void setProfilePic(String profilePhoto) { this.profilePhoto = profilePhoto; }
    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }
    public List<User> getFriendRequests() {
        return friends;
    }

    public void setFriendRequests(List<User> friends) {
        this.friends = friends;
    }
}

