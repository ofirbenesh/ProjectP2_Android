package com.example.projectp2_android;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.projectp2_android.entities.Post;

@Database(entities = {Post.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract PostDao postDao();
    private static volatile LocalDatabase INSTANCE;

    public static void init(Context context) {
        if (INSTANCE == null) {
            synchronized (LocalDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    LocalDatabase.class, "local_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
    }

    public static LocalDatabase getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Database has not been initialized.");
        }
        return INSTANCE;
    }

}
