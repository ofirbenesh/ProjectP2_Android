package com.example.projectp2_android.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.projectp2_android.User;

import java.util.Date;

public class Comment implements Parcelable {

    private final String commentId;
    private final Date date;
    private final User user;
    private String text;
    private int likes;

    public Comment(String commentId, Date date, User user, String text, int likes) {
        this.commentId = commentId;
        this.date = date;
        this.user = user;
        this.text = text;
        this.likes = likes;
    }

    public String getCommentId() {
        return commentId;
    }

    public Date getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    // Parcelable implementation
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(commentId);
        dest.writeSerializable(date);
        dest.writeParcelable((Parcelable) user, flags);
        dest.writeString(text);
        dest.writeInt(likes);
    }

    protected Comment(Parcel in) {
        commentId = in.readString();
        date = (Date) in.readSerializable();
        user = in.readParcelable(User.class.getClassLoader());
        text = in.readString();
        likes = in.readInt();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
