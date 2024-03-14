package com.example.projectp2_android.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.projectp2_android.MyApplication;
import com.example.projectp2_android.db.dao.PostDao;
import com.example.projectp2_android.entities.Post;

@Database(entities = {Post.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract PostDao postDao();
    private static volatile LocalDatabase INSTANCE;

    public static LocalDatabase getInstance() {
        if (INSTANCE == null) {
            synchronized (LocalDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(MyApplication.context,
                                    LocalDatabase.class, "LocalDataBase").allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
