package com.example.thurazaw.todolists.dialog;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.example.thurazaw.todolists.R;
import com.example.thurazaw.todolists.database.AppDatabase;
import com.example.thurazaw.todolists.database.ItemEntry;
import com.example.thurazaw.todolists.viewmodel.AppExecutors;

import java.util.Date;

public class CheckingDialog extends DialogFragment implements View.OnClickListener {

    private AppCompatButton btn_additem;
    private AppDatabase mDb;
    private AppCompatEditText et_additem;


    public static CheckingDialog getInstance(int check){
        CheckingDialog checkingDialog = new CheckingDialog();

        Bundle bundle = new Bundle();
        bundle.putInt("checking", check);
        checkingDialog.setArguments(bundle);
        return checkingDialog;
    }


    @Override
    public void onResume() {

        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        int width = Utils.dpToPx(300);
        params.width = width;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_item_dialog, container, false);
        getDialog().getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.custom_dialog));
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);

        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_additem = view.findViewById(R.id.btn_additem);
        et_additem = view.findViewById(R.id.et_additem);
        mDb = AppDatabase.getInstance(view.getContext());


        Typeface font = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/avenirnextregular.ttf");
        btn_additem.setTypeface(font);


        int check = getArguments().getInt("checking");

        if(check == 0) {

            btn_additem.setOnClickListener(this);

        }
    }


    @Override
    public void onClick(View v) {

        String description = et_additem.getText().toString();
        Date date = new Date();

        if(description.isEmpty()){

            Toast.makeText(v.getContext(),"Enter your task!", Toast.LENGTH_SHORT).show();

        }else {

            final ItemEntry task = new ItemEntry(description, date);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    // insert new task
                    mDb.itemDao().InsertItem(task);
                    dismiss();

                }
            });

        }


    }
}