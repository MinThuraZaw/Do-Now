package com.example.thurazaw.todolists.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.thurazaw.todolists.database.AppDatabase;
import com.example.thurazaw.todolists.database.ItemEntry;

public class AddItemViewModel extends ViewModel {

    private LiveData<ItemEntry> items;

    public AddItemViewModel(AppDatabase database, int id){
        items = database.itemDao().loadItemById(id);
    }

    public LiveData<ItemEntry> getItems() {
        return items;
    }
}
