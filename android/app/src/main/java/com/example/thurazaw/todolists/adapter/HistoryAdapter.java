package com.example.thurazaw.todolists.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thurazaw.todolists.R;
import com.example.thurazaw.todolists.database.AppDatabase;
import com.example.thurazaw.todolists.database.HistoryEntry;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder>{

    private static final String DATE_FORMAT = "dd/MM/yyy";

    private List<HistoryEntry> historyEntries;
    private Context mContext;
    private AppDatabase mData;

    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());


    public HistoryAdapter(Context context) {
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.history_list, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        HistoryEntry taskEntry = historyEntries.get(position);
        String description = taskEntry.getDescription();
        String updatedAt = dateFormat.format(taskEntry.getUpdatedAt());

        mData = AppDatabase.getInstance(mContext.getApplicationContext());


        holder.history_desc.setText(description);
        holder.history_date.setText(updatedAt);

        holder.btn_delete_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mData.itemDao().deleteHistory(historyEntries.get(position));
                Log.i("remove", "Remove "+ historyEntries.get(position).getDescription());
                historyEntries.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, historyEntries.size());


            }
        });

    }


    @Override
    public int getItemCount() {
        if (historyEntries == null) {
            return 0;
        }
        return historyEntries.size();
    }

    public void setHistory(List<HistoryEntry> mhistoryEntries) {
        this.historyEntries = mhistoryEntries;
        notifyDataSetChanged();

    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        AppCompatTextView history_desc;
        AppCompatTextView history_date;
        AppCompatImageButton btn_delete_history;

        public MyViewHolder(final View itemView) {
            super(itemView);

            history_desc = itemView.findViewById(R.id.history_desc);
            history_date = itemView.findViewById(R.id.history_date);
            btn_delete_history = itemView.findViewById(R.id.btn_delete_history);
            itemView.setOnClickListener(this);

            Typeface font = Typeface.createFromAsset(mContext.getAssets(), "fonts/avenirnextregular.ttf");
            history_desc.setTypeface(font);
            history_date.setTypeface(font);

        }

        @Override
        public void onClick(View view) {
            int elementId = historyEntries.get(getAdapterPosition()).getId();
        }

    }
}
