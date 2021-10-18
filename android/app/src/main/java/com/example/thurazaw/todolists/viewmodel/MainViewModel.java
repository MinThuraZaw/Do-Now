package com.example.thurazaw.todolists.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.thurazaw.todolists.database.AppDatabase;
import com.example.thurazaw.todolists.database.ItemEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private LiveData<List<ItemEntry>> items;

    public MainViewModel(@NonNull Application application) {
        super(application);

        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        items = database.itemDao().getAllItem();

    }

    public LiveData<List<ItemEntry>> getItems() {
        return items;
    }
}
