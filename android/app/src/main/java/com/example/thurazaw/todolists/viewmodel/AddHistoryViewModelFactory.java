package com.example.thurazaw.todolists.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.thurazaw.todolists.database.AppDatabase;

public class AddHistoryViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase database;
    private final int hId;

    public AddHistoryViewModelFactory(AppDatabase db, int id){
        this.database = db;
        this.hId = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddHistoryViewModel(database, hId);
    }
}
