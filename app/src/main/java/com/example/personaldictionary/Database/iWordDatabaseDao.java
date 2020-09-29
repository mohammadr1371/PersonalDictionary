package com.example.personaldictionary.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.personaldictionary.Model.Word;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface iWordDatabaseDao {

    @Query("SELECT * FROM words")
    List<Word> getWordsList();

    @Insert
    void insertWord(Word word);

    @Update
    void updateWordTable(Word word);

    @Delete
    void deleteWord(Word word);

    @Query("DELETE FROM words")
    void deleteALL();
}
