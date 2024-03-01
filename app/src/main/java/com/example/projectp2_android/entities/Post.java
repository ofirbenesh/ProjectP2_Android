package com.example.projectp2_android.entities;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Post {
    @PrimaryKey(autoGenerate = true)

    private int id;
    private String author;
    private String content;
    private int likes;
    private int pic;
    private int profilePic;
    private Uri profilePicUri;
    private Uri picUri;
    private String date;
    private List<Comment> listOfComments;
    private boolean isLiked;



    // a way to present picture
    public Post(int id, String author, String content, Uri pic, Uri profilePic, int likes, String date) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.pic = -1;
        this.profilePic = -1;
        this.picUri = pic;
        this.date = date;
        this.profilePicUri = profilePic;
        this.likes = likes;
        this.listOfComments = new ArrayList<>();
        this.date = date;
        this.isLiked = false;
    }


    public Post(int id, String author, String content, int pic, int profilePic, int likes, String date) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.pic = pic;
        this.profilePic = profilePic;
        this.picUri = null;
        this.date = date;
        this.profilePicUri = null;
        this.likes = likes;
        this.listOfComments = new ArrayList<>();
        this.date = date;
        this.isLiked = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikes() {
        return this.likes;
    }

    public void setLikes(int i) {
        this.likes = i;
    }

    public void incrementLikes() {
        this.likes += 1;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public int getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(int profilePic) {
        this.profilePic = profilePic;
    }
    public void setProfilePicUri(Uri profilePic) {
        this.profilePicUri = profilePic;
    }
    public void addComment(Comment comment) {
        this.listOfComments.add(comment);
    }
    public List<Comment> getAllComments() {
        return listOfComments;
    }

    public String getDate() {
        return date;
    }

    public Uri getProfilePicUri() {
        return profilePicUri;
    }

    public Uri getPicUri() {
        return picUri;
    }
    // Existing fields and methods

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public void setPicUri(Uri picUri) {
        this.picUri = picUri;
    }
}