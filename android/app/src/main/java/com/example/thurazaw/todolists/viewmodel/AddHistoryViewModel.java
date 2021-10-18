package com.example.thurazaw.todolists.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.thurazaw.todolists.database.AppDatabase;
import com.example.thurazaw.todolists.database.HistoryEntry;
import com.example.thurazaw.todolists.database.ItemEntry;

public class AddHistoryViewModel extends ViewModel {

    private LiveData<HistoryEntry> histories;

    public AddHistoryViewModel(AppDatabase database, int id){
        histories = database.itemDao().loadHistoryById(id);
    }

    public LiveData<HistoryEntry> getHistories() {
        return histories;
    }
}
