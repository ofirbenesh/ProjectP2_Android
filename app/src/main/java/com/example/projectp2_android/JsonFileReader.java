package com.example.projectp2_android;

import android.content.Context;

import com.example.projectp2_android.entities.Post;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JsonFileReader {

    public List<Post> readPostsFromJson(Context context, String fileName) {
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String json = new String(buffer, StandardCharsets.UTF_8);

            // Use Gson to parse the JSON data into a List<Post>
            Type listType = new TypeToken<List<Post>>() {
            }.getType();
            return new Gson().fromJson(json, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
