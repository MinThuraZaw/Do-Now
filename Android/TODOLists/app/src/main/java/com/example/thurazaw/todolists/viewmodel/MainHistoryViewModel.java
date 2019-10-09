package com.example.thurazaw.todolists.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.thurazaw.todolists.database.AppDatabase;
import com.example.thurazaw.todolists.database.HistoryEntry;
import com.example.thurazaw.todolists.database.ItemEntry;

import java.util.List;

public class MainHistoryViewModel extends AndroidViewModel {

    private LiveData<List<HistoryEntry>> histories;

    public MainHistoryViewModel(@NonNull Application application) {
        super(application);

        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        histories = database.itemDao().getAllHistory();

    }

    public LiveData<List<HistoryEntry>> getHistories() {
        return histories;
    }
}
