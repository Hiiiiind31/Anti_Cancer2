package com.example.hindahmed.anti_cancer;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

import static com.example.hindahmed.anti_cancer.R.id.Hashtag;

/**
 * Created by hindahmed on 11/05/17.
 */

public class Post {


    public String uid;
    public String author;
    public String title;
    public String body;
    public int starCount = 0;
    public String hashtag ;
    public Map<String, Boolean> stars = new HashMap<>();

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String uid, String author, String title, String body,String hashtag) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
        this.hashtag = hashtag ;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);
        result.put("starCount", starCount);
        result.put("stars", stars);
        result.put("hashtag", hashtag);

        return result;


}

    public String getUid() {
        return uid;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public int getStarCount() {
        return starCount;
    }

    public Map<String, Boolean> getStars() {
        return stars;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

    public void setStars(Map<String, Boolean> stars) {
        this.stars = stars;
    }
}
