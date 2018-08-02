package com.manish.adapter;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.manish.ApplicationLoader;
import com.manish.R;
import com.manish.db.table.Category;
import com.manish.db.table.MostOrderdProduct;
import com.manish.viewmodel.DataViewModel;
import com.manish.viewmodel.PLPScreen;

import java.util.ArrayList;
import java.util.List;


public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.CategoryViewHolder> {
    private List<Category> CategoryList = new ArrayList<>();
    Activity activity;
    private DisplayMetrics displayMetrics;
    int screenWidthInDp;


    public HomeCategoryAdapter(Activity activity, List<Category> CategoryList) {
        this.activity = activity;
        this.CategoryList = CategoryList;
        this.displayMetrics = activity.getResources().getDisplayMetrics();
        this.screenWidthInDp = displayMetrics.widthPixels;
    }


    @Override
    public int getItemCount() {
        if(CategoryList!=null && CategoryList.size()>0) {
            return CategoryList.size();
        }else {
            return 0;
        }

    }


    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_cat, parent, false);
        CategoryViewHolder shopByViewHolder = new CategoryViewHolder(view);
        return shopByViewHolder;
    }

    @Override
    public void onBindViewHolder(final CategoryViewHolder holder, final int position) {


        final Category shopCategoryBean = CategoryList.get(position);
        holder.tvName.setText(shopCategoryBean.getCatName());
        holder.outerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPLPScreen(shopCategoryBean.getCatId());
//                DataViewModel dataViewModel = new DataViewModel(ApplicationLoader.getInstance());
//                dataViewModel.getMostOrderedProduct().observe((LifecycleOwner) activity, new Observer<List<Category>>() {
//                    @Override
//                    public void onChanged(@Nullable List<Category> mostViewedProducts) {
//
//                        mostViewedAdapter.setMostViewedProduct(mostViewedProducts);
//
//                    }
//                });

            }
        });


    }


    private void openPLPScreen(int categoryId) {
        Intent intent = new Intent(activity, PLPScreen.class);
        intent.putExtra("cat_id", categoryId);
        activity.startActivity(intent);

    }


    static class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        RelativeLayout outerLayout;
        Button btn;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            outerLayout = (RelativeLayout) itemView.findViewById(R.id.outer_layout);

        }
    }

}




