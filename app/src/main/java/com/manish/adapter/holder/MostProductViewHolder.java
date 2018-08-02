package com.manish.adapter.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.manish.R;

public class MostProductViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvCount;

        public MostProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_pid);
            tvCount = itemView.findViewById(R.id.tv_viewcount);
        }
    }