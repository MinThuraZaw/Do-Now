package com.example.thurazaw.todolists.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.thurazaw.todolists.R;
import com.example.thurazaw.todolists.database.AppDatabase;
import com.example.thurazaw.todolists.database.HistoryEntry;
import com.example.thurazaw.todolists.database.ItemEntry;
import com.example.thurazaw.todolists.viewmodel.AppExecutors;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder>{

    private static final String DATE_FORMAT = "dd/MM/yyy";

    private List<ItemEntry> mItemEntries;
    private Context mContext;
    private AppDatabase mData;

    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());


    public ItemAdapter(Context context) {
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_list, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        ItemEntry taskEntry = mItemEntries.get(position);
        String description = taskEntry.getDescription();
        String updatedAt = dateFormat.format(taskEntry.getUpdatedAt());

        mData = AppDatabase.getInstance(mContext.getApplicationContext());


        holder.taskDescriptionView.setText(description);
        holder.updatedAtView.setText(updatedAt);

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                            // insert new task
                            mData.itemDao().InsertHistory(new HistoryEntry(mItemEntries.get(position).getDescription(),
                                    mItemEntries.get(position).getUpdatedAt()));
                            //Log.i("insert","success");

                    }
                });

                mData.itemDao().deleteItem(mItemEntries.get(position));
                mItemEntries.remove(position );
                notifyItemRemoved(position );

            }
        });

    }


    @Override
    public int getItemCount() {
        if (mItemEntries == null) {
            return 0;
        }
        return mItemEntries.size();
    }

    public void setItem(List<ItemEntry> itemEntries) {
        mItemEntries = itemEntries;
        notifyDataSetChanged();

    }

    public List<ItemEntry> getmItemEntries(){
        return mItemEntries;
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        AppCompatTextView taskDescriptionView;
        AppCompatTextView updatedAtView;
        AppCompatImageButton btn_delete;

        public MyViewHolder(final View itemView) {
            super(itemView);

            taskDescriptionView = itemView.findViewById(R.id.taskDescription);
            updatedAtView = itemView.findViewById(R.id.taskUpdatedAt);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            itemView.setOnClickListener(this);

            Typeface font = Typeface.createFromAsset(mContext.getAssets(), "fonts/avenirnextregular.ttf");
            taskDescriptionView.setTypeface(font);
            updatedAtView.setTypeface(font);

        }

        @Override
        public void onClick(View view) {
            int elementId = mItemEntries.get(getAdapterPosition()).getId();
        }

    }
}
