package com.example.projectp2_android.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.projectp2_android.User;

import java.util.Date;
import java.util.List;

public class Post implements Parcelable {

    private final String postId;
    private final Date date;
    private final User user;
    private String text;
    //private int image;
    private int likes;
    //private List<Comment> comments;

    // Constructors, getters, setters, and other methods

    // Parcelable implementation
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(postId);
        dest.writeSerializable(date);
        //dest.writeParcelable(user, flags);
        dest.writeString(text);
        //dest.writeString(image);
        dest.writeInt(likes);
        //dest.writeTypedList(comments);
    }

    protected Post(Parcel in) {
        postId = in.readString();
        date = (Date) in.readSerializable();
        user = in.readParcelable(User.class.getClassLoader());
        text = in.readString();
        // image = in.readInt();
        likes = in.readInt();
        //comments = in.createTypedArrayList(Comment.CREATOR);
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

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

//    public List<Comment> getComments() {
//        return comments;
//    }
//
//    public void setComments(List<Comment> comments) {
//        this.comments = comments;
//    }
//
//    public void addComment(Comment comment) {
//        comments.add(comment);
//    }

//    public int getImage() {
//        return image;
//    }
//
//    public void setImage(int image) {
//        this.image = image;
//    }
}