package com.example.thurazaw.todolists.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ItemDao {

    @Query("SELECt * FROM item ")
    LiveData<List<ItemEntry>> getAllItem();

    @Query("SELECt * FROM history ")
    LiveData<List<HistoryEntry>> getAllHistory();

    @Insert
    void InsertItem(ItemEntry itemEntry);

    @Insert
    void InsertHistory(HistoryEntry historyEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateItem(ItemEntry itemEntry);

    @Delete
    void deleteItem(ItemEntry itemEntry);

    @Delete
    void deleteHistory(HistoryEntry historyEntry);

    @Query("SELECT * FROM item WHERE id = :id")
    LiveData<ItemEntry> loadItemById(int id);

    @Query("SELECT * FROM history WHERE id = :id")
    LiveData<HistoryEntry> loadHistoryById(int id);

    @Query("SELECT count(*) from item")
    int ItemCount();

    @Query("SELECT count(*) from history")
    int HistoryCount();
}
