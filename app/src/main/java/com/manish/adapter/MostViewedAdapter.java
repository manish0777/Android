package com.manish.adapter;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.manish.R;
import com.manish.db.table.Category;
import com.manish.db.table.MostViewedProduct;
import com.manish.viewmodel.DataViewModel;

import java.util.ArrayList;
import java.util.List;

class MostViewedAdapter extends RecyclerView.Adapter<MostViewedAdapter.MostProductViewHolder> {
    private List<MostViewedProduct> mostViewedProducts = new ArrayList<>();
    private Activity mActivity;

    public MostViewedAdapter(Activity mActivity) {
        this.mActivity = mActivity;
    }


    @NonNull
    @Override
    public MostViewedAdapter.MostProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_most_viewd, parent, false);
        MostViewedAdapter.MostProductViewHolder mostProductViewHolder = new MostViewedAdapter.MostProductViewHolder(view);
        return mostProductViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MostViewedAdapter.MostProductViewHolder holder, int position) {

        final MostViewedProduct mostViewedProduct = mostViewedProducts.get(position);
//        float reqspacing = screenWidthInDp / (int) 4.2;
//        holder.outerLayout.getLayoutParams().width = (int) reqspacing;
//        float dp = Resources.getSystem().getDisplayMetrics().density;
        holder.tvName.setText("Product Id :" + mostViewedProduct.getId());
        holder.tvCount.setText("Count:" + mostViewedProduct.getViewcount());

    }

    @Override
    public int getItemCount() {
        return mostViewedProducts.size();
    }

    public void setMostViewedProduct(List<MostViewedProduct> mostViewedProducts) {
        this.mostViewedProducts = mostViewedProducts;
        notifyDataSetChanged();
    }

    public class MostProductViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvCount;

        public MostProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_pid);
            tvCount = (TextView) itemView.findViewById(R.id.tv_viewcount);
        }
    }
}
