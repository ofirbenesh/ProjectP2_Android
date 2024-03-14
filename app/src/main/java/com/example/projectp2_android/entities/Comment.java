package com.example.projectp2_android.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable {

    private int commentId;
    private String author;
    private String text;

    public Comment(int commentId, String author, String text) {
        this.commentId = commentId;
        this.author = author;
        this.text = text;
    }

    public int getCommentId() {
        return commentId;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    // Parcelable implementation
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(commentId);
        dest.writeString(author);
        dest.writeString(text);
    }

    protected Comment(Parcel in) {
        commentId = in.readInt();
        author = in.readString();
        text = in.readString();
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
