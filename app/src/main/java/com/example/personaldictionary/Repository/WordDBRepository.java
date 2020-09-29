package com.example.personaldictionary.Repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.personaldictionary.Model.Word;

import java.util.ArrayList;

public class WordDBRepository {

    public static Context mContext;
    public static WordDBRepository sWordDBRepository;

    public static WordDBRepository getInstance(Context context){
        mContext = context.getApplicationContext();
        if(sWordDBRepository == null){
            sWordDBRepository = new WordDBRepository();
            return sWordDBRepository;
        }
        return sWordDBRepository;
    }

//    ArrayList<Word> mWordArrayList = new ArrayList<>();
//    SQLiteDatabase mSQLiteDatabase;
//    private WordDBRepository() {
//        WordDatabaseHelper wordDatabaseHelper = new WordDatabaseHelper(mContext);
//        mSQLiteDatabase = wordDatabaseHelper.getWritableDatabase();
//    }

//    public ArrayList<Word> getWordArrayList(){
//        Cursor cursor = mSQLiteDatabase.query("wordTable"
//                ,null
//                , null
//                ,null
//                ,null
//                ,null
//                ,null);
//
//        try {
//            cursor.moveToFirst();
//
//        } finally {
//            cursor.close();
//        }
//
//        return null;
//    }
}
