package com.example.thurazaw.todolists.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "history")
public class HistoryEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String description;
    private Date updatedAt;

    @Ignore
    public HistoryEntry( String description, Date updatedAt) {
        this.description = description;
        this.updatedAt = updatedAt;
    }

    public HistoryEntry(int id, String description, Date updatedAt) {
        this.id = id;
        this.description = description;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }






}
