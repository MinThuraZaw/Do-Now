package com.example.thurazaw.todolists.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.thurazaw.todolists.database.AppDatabase;

public class AddItemViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private final AppDatabase database;
    private final int itemId;

    public AddItemViewModelFactory(AppDatabase db, int id){
        this.database = db;
        this.itemId = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddItemViewModel(database, itemId);
    }
}
