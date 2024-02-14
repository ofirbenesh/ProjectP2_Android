package com.example.projectp2_android.entities;

public class Post {

    private int id;
    private String author;
    private String content;
    private int likes;
    private int pic;

    public Post(int id, String author, String content, int likes, int pic) {
        this.author = author;
        this.content = content;
        this.pic = pic;
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

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }
}
