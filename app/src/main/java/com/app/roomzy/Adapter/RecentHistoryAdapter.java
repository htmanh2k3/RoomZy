package com.app.roomzy.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecentHistoryAdapter extends RecyclerView.Adapter<RecentHistoryAdapter.Viewholder> {
    @NonNull
    @Override
    public RecentHistoryAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecentHistoryAdapter.Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class Viewholder extends RecyclerView.ViewHolder{
        public Viewholder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
