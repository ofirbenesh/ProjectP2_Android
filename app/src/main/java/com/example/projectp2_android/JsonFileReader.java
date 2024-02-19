package com.example.projectp2_android;

import android.content.Context;

import com.example.projectp2_android.entities.GlobalVariables;
import com.example.projectp2_android.entities.Post;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonFileReader {

    public static void readPostsFromJson(Context context) {
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.posts);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("posts");

            List<Post> allPosts = GlobalVariables.allPosts;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonPost = jsonArray.getJSONObject(i);
                int id = jsonPost.getInt("id");
                String author = jsonPost.getString("author");
                String content = jsonPost.getString("content");
                String date = jsonPost.getString("date");
                int likes = jsonPost.getInt("likes");
                int picResourceId = R.drawable.cake;
                Post post = new Post(id, author, content, picResourceId, likes);
                allPosts.add(post);
            }

            // Use Gson to parse the JSON data into a List<Post>
//            Type listType = new TypeToken<List<Post>>() {
//            }.getType();
//            return new Gson().fromJson(json, listType);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

}

