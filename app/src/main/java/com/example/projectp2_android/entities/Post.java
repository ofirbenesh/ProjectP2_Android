package com.example.projectp2_android.entities;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Post {
    @PrimaryKey(autoGenerate = true)

    private int id;
    String userID;
    private String author;
    private String content;

    private String photo;

    private int likes;
    private int pic;
    //    private int profilePic;
//    private Uri profilePicUri;
//    private Uri picUri;
//    private String date;
//    private List<Comment> listOfComments;
    private boolean isLiked;
    // Constructor match to server

    public Post(String userID, String content, String photo, String author) {
        this.userID = userID;
        this.content = content;
        this.photo = photo;
        this.author = author;
    }
    @Ignore
    // a way to present picture
    public Post(int id, String author, String content, Uri pic, Uri profilePic, int likes, String date) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.pic = -1;
//        this.profilePic = -1;
//        this.picUri = pic;
//        this.profilePicUri = profilePic;
        this.likes = likes;
//        this.listOfComments = new ArrayList<>();
//        this.date = date;
        this.isLiked = false;
    }

    @Ignore
    public Post(int id, String author, String content, int pic, int profilePic, int likes, String date) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.pic = pic;
//        this.profilePic = profilePic;
//        this.picUri = null;
//        this.date = date;
//        this.profilePicUri = null;
        this.likes = likes;
//        this.listOfComments = new ArrayList<>();
//        this.date = date;
        this.isLiked = false;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
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
        return likes;
    }

    public void addLike() {
        this.likes += 1;
    }

    public void removeLike() {
        if (this.likes > 0) {
            this.likes -= 1;
        }
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

//    public int getProfilePic() {
//        return profilePic;
//    }
//
//    public void setProfilePic(int profilePic) {
//        this.profilePic = profilePic;
//    }
//    public void setProfilePicUri(Uri profilePic) {
//        this.profilePicUri = profilePic;
//    }
//    public void addComment(Comment comment) {
//        this.listOfComments.add(comment);
//    }
//    public List<Comment> getAllComments() {
//        return listOfComments;
//    }
//
//    public List<Comment> getListOfComments() {
//        return listOfComments;
//    }
//
//    public void setListOfComments(List<Comment> listOfComments) {
//        this.listOfComments = listOfComments;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public Uri getProfilePicUri() {
//        return profilePicUri;
//    }
//
//    public Uri getPicUri() {
//        return picUri;
//    }
    // Existing fields and methods

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

//    public void setPicUri(Uri picUri) {
//        this.picUri = picUri;
//    }
}