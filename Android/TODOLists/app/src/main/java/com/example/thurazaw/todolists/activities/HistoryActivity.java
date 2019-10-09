package com.example.thurazaw.todolists.activities;

import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.thurazaw.todolists.R;
import com.example.thurazaw.todolists.adapter.HistoryAdapter;
import com.example.thurazaw.todolists.adapter.ItemAdapter;
import com.example.thurazaw.todolists.database.AppDatabase;
import com.example.thurazaw.todolists.database.HistoryEntry;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;
    private List<HistoryEntry> data = null;
    private AppDatabase appDatabase;
    private AppCompatTextView tv_nohistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        appDatabase = AppDatabase.getInstance(this);
        tv_nohistory = findViewById(R.id.txt_nohistory);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/avenirnextregular.ttf");
        tv_nohistory.setTypeface(font);

        toolbar = findViewById(R.id.toolbar_history);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);


        recyclerView = findViewById(R.id.recycler_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        data = appDatabase.itemDao().getAllHistory();

        if(data.size() <= 0){
            tv_nohistory.setVisibility(View.VISIBLE);
        }else {

            tv_nohistory.setVisibility(View.INVISIBLE);


        }

        historyAdapter = new HistoryAdapter(this, data);
        recyclerView.setAdapter(historyAdapter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
}
