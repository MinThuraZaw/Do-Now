package com.example.thurazaw.todolists.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.thurazaw.todolists.adapter.ItemAdapter;
import com.example.thurazaw.todolists.dialog.CheckingDialog;
import com.example.thurazaw.todolists.viewmodel.MainViewModel;
import com.example.thurazaw.todolists.R;
import com.example.thurazaw.todolists.database.AppDatabase;
import com.example.thurazaw.todolists.database.ItemEntry;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemAdapter.ItemClickListener {

    private RecyclerView mRecyclerView;
    private ItemAdapter mAdapter;
    private AppDatabase mData;
    private Toolbar toolbar;
    private AppCompatTextView tv_nolist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        tv_nolist = findViewById(R.id.txt_nolist);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/avenirnextregular.ttf");
        tv_nolist.setTypeface(font);

        mRecyclerView = findViewById(R.id.recyclerViewTasks);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ItemAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);


        FloatingActionButton fabButton = findViewById(R.id.fab);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent addTaskIntent = new Intent(MainActivity.this, AddItemActivity.class);
                //startActivity(addTaskIntent);
                //custom dialog setup
                FragmentManager fragmentManager  = getSupportFragmentManager();
                final CheckingDialog checkingDialog = CheckingDialog.getInstance(0);
                checkingDialog.show(fragmentManager,"check");
                checkingDialog.setCancelable(true);
            }
        });

        mData = AppDatabase.getInstance(this);

        setupViewModel();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.menu_main_history:
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));

            default:
                return super.onOptionsItemSelected(item);

        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);

    }

    private void setupViewModel() {

        MainViewModel mainViewModel = ViewModelProviders.of(this ).get(MainViewModel.class);

        mainViewModel.getItems().observe(this, new Observer<List<ItemEntry>>(){
            @Override
            public void onChanged(@Nullable List<ItemEntry> itemEntries) {

                mAdapter.setItem(itemEntries);

                if(mData.itemDao().ItemCount() <= 0){
                    tv_nolist.setVisibility(View.VISIBLE);
                }else {

                    tv_nolist.setVisibility(View.INVISIBLE);

                }

            }
        });


    }

    @Override
    public void onItemClickListener(int itemId) {
        // Launch AddTaskActivity adding the itemId as an extra in the intent
    }
}
