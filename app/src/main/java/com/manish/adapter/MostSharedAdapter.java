package com.manish.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manish.R;
import com.manish.db.table.MostSharedProduct;

import java.util.ArrayList;
import java.util.List;

public class MostSharedAdapter extends RecyclerView.Adapter<MostSharedAdapter.MostProductViewHolder> {
    private List<MostSharedProduct> mostViewedProducts = new ArrayList<>();
    private Activity mActivity;

    public MostSharedAdapter(Activity mActivity) {
        this.mActivity = mActivity;
    }


    @NonNull
    @Override
    public MostProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_most_viewd, parent, false);
        MostProductViewHolder mostProductViewHolder = new MostProductViewHolder(view);
        return mostProductViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MostProductViewHolder holder, int position) {
        MostSharedProduct orderdProduct = mostViewedProducts.get(position);
        holder.tvName.setText("Product Id :" + orderdProduct.getId());
        holder.tvCount.setText("Shared count:" + orderdProduct.getMostShared());
    }


    @Override
    public int getItemCount() {
        return mostViewedProducts.size();
    }

    public void setMostViewedProduct(List<MostSharedProduct> mostViewedProducts) {
        this.mostViewedProducts = mostViewedProducts;
        notifyDataSetChanged();
    }

    public class MostProductViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvCount;

        public MostProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_pid);
            tvCount = itemView.findViewById(R.id.tv_viewcount);
        }
    }
}
