package com.example.projectp2_android.entities;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.projectp2_android.User;

import java.util.Date;
@Entity
public class Post {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String author;
    private String content;
    private int likes;
    //private int pic;
    //private Uri picUri;


    // a way to present picture
    public Post(String author, String content, Uri pic, int likes) {
        this.author = author;
        this.content = content;
        //this.pic = -1;
        //this.picUri = pic;
        this.likes = likes;
    }
    public Post(String author, String content, int pic, int likes) {
        this.author = author;
        this.content = content;
        //this.pic = pic;
        //this.picUri = null;
        this.likes = likes;
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
//
//    public void addLikes(int likes) {
//        this.likes = likes++;
//    }

//    public int getPic() {
//        return pic;
//    }
//
//    public void setPic(int pic) {
//        this.pic = pic;
//    }


}