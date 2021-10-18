package com.example.thurazaw.todolists.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {ItemEntry.class, HistoryEntry.class}, version = 2, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sinstance;
    private static final String DATABASE_NAME= "todolist";

    public static AppDatabase getInstance(Context context){

        if(sinstance == null){
            synchronized (new Object()){
                sinstance = Room.databaseBuilder(context,AppDatabase.class, DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build();

            }
        }

        return sinstance;

    }

    public abstract ItemDao itemDao();
}
