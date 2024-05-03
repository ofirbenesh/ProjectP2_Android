package com.example.projectp2_android.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.projectp2_android.db.dao.PostDao;
import com.example.projectp2_android.db.dao.UserDao;
import com.example.projectp2_android.entities.Post;
import com.example.projectp2_android.entities.User;

@Database(entities = {User.class}, version = 1)
public abstract class UsersDB extends RoomDatabase {
    public abstract UserDao userDao();
    private static volatile UsersDB INSTANCE;

    public static void init(Context context) {
        if (INSTANCE == null) {
            synchronized (UsersDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    UsersDB.class, "user")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
    }

    public static UsersDB getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Database has not been initialized.");
        }
        return INSTANCE;
    }

}
