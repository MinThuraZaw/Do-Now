package com.example.thurazaw.todolists.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.thurazaw.todolists.viewmodel.AddItemViewModel;
import com.example.thurazaw.todolists.viewmodel.AddItemViewModelFactory;
import com.example.thurazaw.todolists.viewmodel.AppExecutors;
import com.example.thurazaw.todolists.R;
import com.example.thurazaw.todolists.database.AppDatabase;
import com.example.thurazaw.todolists.database.ItemEntry;

import java.util.Date;

public class AddItemActivity extends AppCompatActivity {

    public static final String EXTRA_TASK_ID = "extraTaskId";
    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    private static final int DEFAULT_TASK_ID = -1;
    EditText mEditText;
    Button mButton;


    private int mTaskId = DEFAULT_TASK_ID;
    private AppDatabase mDb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //make full screen
        this.getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initViews();

        mDb = AppDatabase.getInstance(getApplicationContext());

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


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_TASK_ID, mTaskId);
        super.onSaveInstanceState(outState);
    }


    private void initViews() {
        mEditText = findViewById(R.id.editTextTaskDescription);

        mButton = findViewById(R.id.saveButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });
    }



    public void onSaveButtonClicked() {
        String description = mEditText.getText().toString();
        Date date = new Date();

        final ItemEntry task = new ItemEntry( description, date);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mTaskId == DEFAULT_TASK_ID) {
                    // insert new task
                    mDb.itemDao().InsertItem(task);
                }
                finish();
            }
        });


    }


}
