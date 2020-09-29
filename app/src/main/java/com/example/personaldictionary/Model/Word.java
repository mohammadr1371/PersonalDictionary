package com.example.personaldictionary.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "words")
public class Word implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int mId;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "meaning")
    private String mMeaning;

    public Word(String title, String meaning) {
        mTitle = title;
        mMeaning = meaning;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getMeaning() {
        return mMeaning;
    }

    public void setMeaning(String meaning) {
        mMeaning = meaning;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}
