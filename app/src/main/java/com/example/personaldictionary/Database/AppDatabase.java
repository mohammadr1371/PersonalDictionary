package com.example.personaldictionary.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.personaldictionary.Model.Word;

@Database(entities = {Word.class}, version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {

    private static Context mContext;

    public abstract iWordDatabaseDao mIWordDatabaseDao();

    public static AppDatabase sAppDatabase;

    public static AppDatabase getInstance(Context context){
        mContext = context.getApplicationContext();
        if(sAppDatabase == null){
            sAppDatabase = Room.databaseBuilder(mContext,
                    AppDatabase.class, "AppDatabase.db").allowMainThreadQueries().build();
        }
        return sAppDatabase;
    }
}
