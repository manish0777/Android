package com.manish.adapter;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.manish.ApplicationLoader;
import com.manish.R;
import com.manish.db.table.Category;
import com.manish.db.table.MostOrderdProduct;
import com.manish.db.table.MostSharedProduct;
import com.manish.db.table.MostViewedProduct;
import com.manish.helper.HOMEPAGEVIEW;
import com.manish.model.HomePageBean;
import com.manish.utils.Apputil;
import com.manish.viewmodel.DataViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private DisplayMetrics displayMetrics;
    private float screenWidthInDp;
    private Activity mActivity;
    private List<HomePageBean> homePageBeanList;
    private LayoutInflater inflater;
    private String response;
    private List<Category> categorylist;

    public HomePageAdapter(Activity activity, List<HomePageBean> homePageBeanList) {
        this.mActivity = activity;
        this.homePageBeanList = homePageBeanList;
        this.inflater = LayoutInflater.from(mActivity);
        this.displayMetrics = mActivity.getResources().getDisplayMetrics();
        this.screenWidthInDp = displayMetrics.widthPixels;
    }

    public void setCategoryList(ArrayList<Category> categoryList) {
        this.categorylist = categoryList;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            return getCustomViewHolderView(parent, viewType);
        } catch (Exception e) {
            //sometime getting inflate issue, so trying to reinflate.
            return getCustomViewHolderView(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof CustomViewHolder) {
            final CustomViewHolder viewHolder = (CustomViewHolder) holder;
            final int listViewItemType = getItemViewType(position);
            final String displayId = homePageBeanList.get(position).displayId;
            if (HOMEPAGEVIEW.CATEGORYVIEW == Apputil.parseInt(displayId)) {
                displayCategoryView(viewHolder, position);
            }
            if (HOMEPAGEVIEW.MOSTVIEWED == Apputil.parseInt(displayId)) {
                displayMostViewedProduct(viewHolder, position);
            }
            if (HOMEPAGEVIEW.MOSTORDERED == Apputil.parseInt(displayId)) {
                displayMostOrderedProduct(viewHolder, position);
            }
            if (HOMEPAGEVIEW.SHAREDPRODUCT == Apputil.parseInt(displayId)) {
                displaySharedProduct(viewHolder, position);
            }


        }
    }

    private void displaySharedProduct(CustomViewHolder viewHolder, int position) {
        viewHolder.rlmostshared.setVisibility(View.VISIBLE);
        final MostSharedAdapter mostViewedAdapter = new MostSharedAdapter(mActivity);
        viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        if (mostViewedAdapter != null)
            viewHolder.recyclerView.setAdapter(mostViewedAdapter);
        DataViewModel dataViewModel = new DataViewModel(ApplicationLoader.getInstance());
        dataViewModel.getMostSharedProduct().observe((LifecycleOwner) mActivity, new Observer<List<MostSharedProduct>>() {
            @Override
            public void onChanged(@Nullable List<MostSharedProduct> mostViewedProducts) {
                mostViewedAdapter.setMostViewedProduct(mostViewedProducts);

            }
        });
    }

    private void displayMostViewedProduct(CustomViewHolder viewHolder, int position) {
        viewHolder.rlmostViewd.setVisibility(View.VISIBLE);
        final MostViewedAdapter mostViewedAdapter = new MostViewedAdapter(mActivity);
        viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        if (mostViewedAdapter != null)
            viewHolder.recyclerView.setAdapter(mostViewedAdapter);
        DataViewModel dataViewModel = new DataViewModel(ApplicationLoader.getInstance());
        dataViewModel.getMostViewedProduct().observe((LifecycleOwner) mActivity, new Observer<List<MostViewedProduct>>() {
            @Override
            public void onChanged(@Nullable List<MostViewedProduct> mostViewedProducts) {
                mostViewedAdapter.setMostViewedProduct(mostViewedProducts);

            }
        });
    }

    private void displayMostOrderedProduct(CustomViewHolder viewHolder, int position) {
        viewHolder.rlmostOrdered.setVisibility(View.VISIBLE);
        final MostOrderedProductAdapter mostViewedAdapter = new MostOrderedProductAdapter(mActivity);
        viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        if (mostViewedAdapter != null)
            viewHolder.recyclerView.setAdapter(mostViewedAdapter);
        DataViewModel dataViewModel = new DataViewModel(ApplicationLoader.getInstance());
        dataViewModel.getMostOrderedProduct().observe((LifecycleOwner) mActivity, new Observer<List<MostOrderdProduct>>() {
            @Override
            public void onChanged(@Nullable List<MostOrderdProduct> mostViewedProducts) {

                mostViewedAdapter.setMostViewedProduct(mostViewedProducts);

            }
        });
    }


    void displayCategoryView(final CustomViewHolder viewHolder, final int position) {
        viewHolder.rlcatBlock.setVisibility(View.VISIBLE);
        HomeCategoryAdapter homeCategoryAdapter = new HomeCategoryAdapter(mActivity, categorylist);
        int numberOfColumns = 4;
        GridLayoutManager gridlm = new GridLayoutManager(mActivity, numberOfColumns);
        viewHolder.recyclerView.setLayoutManager(gridlm);
        if (homeCategoryAdapter != null)
            viewHolder.recyclerView.setAdapter(homeCategoryAdapter);


    }


    public static int getBannerHeight(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay()
                .getMetrics(displaymetrics);

        return (displaymetrics.widthPixels * 47) / 100;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {


        RelativeLayout rlcatBlock, rlmostViewd, rlmostOrdered, rlmostshared;
        RecyclerView recyclerView;
        ProgressBar pBar;

        public CustomViewHolder(View itemLayoutView, String displayId) {
            super(itemLayoutView);

            switch (Apputil.parseInt(displayId)) {

                case HOMEPAGEVIEW.CATEGORYVIEW:
                    recyclerView = itemLayoutView.findViewById(R.id.rv_cat_list);
                    rlcatBlock = itemLayoutView.findViewById(R.id.rl_cat_block);
                    pBar = itemLayoutView.findViewById(R.id.loading);
                    break;
                case HOMEPAGEVIEW.MOSTVIEWED:
                    rlmostViewd = itemLayoutView.findViewById(R.id.rl_recently_block);
                    recyclerView = itemLayoutView.findViewById(R.id.rv_most_viewed);
                    break;
                case HOMEPAGEVIEW.MOSTORDERED:
                    rlmostOrdered = itemLayoutView.findViewById(R.id.rl_block);
                    recyclerView = itemLayoutView.findViewById(R.id.rv_most_ordered);
                    break;
                case HOMEPAGEVIEW.SHAREDPRODUCT:
                    rlmostshared = itemLayoutView.findViewById(R.id.rl_block_share);
                    recyclerView = itemLayoutView.findViewById(R.id.rv_most_shared);
                    break;
                default:
            }
        }

    }

    private CustomViewHolder getCustomViewHolderView(ViewGroup parent, int viewType) {
        View view;
        CustomViewHolder customViewHolder;
        int listViewItemType = getItemViewType(viewType);
        String displayId = "0";
        try {
            displayId = homePageBeanList.get(viewType).displayId;
        } catch (Exception e) {
        }
        switch (Apputil.parseInt(displayId)) {

            case HOMEPAGEVIEW.CATEGORYVIEW:
                view = inflater.inflate(R.layout.home_category_view, parent, false);
                customViewHolder = new CustomViewHolder(view, displayId);
                break;
            case HOMEPAGEVIEW.MOSTVIEWED:
                view = inflater.inflate(R.layout.home_most_viewed, parent, false);
                customViewHolder = new CustomViewHolder(view, displayId);
                break;
            case HOMEPAGEVIEW.MOSTORDERED:
                view = inflater.inflate(R.layout.home_most_ordered, parent, false);
                customViewHolder = new CustomViewHolder(view, displayId);
                break;
            case HOMEPAGEVIEW.SHAREDPRODUCT:
                view = inflater.inflate(R.layout.home_most_shared, parent, false);
                customViewHolder = new CustomViewHolder(view, displayId);
                break;
            default:
                view = inflater.inflate(null, parent, false);
                customViewHolder = new CustomViewHolder(view, displayId);
                break;
        }
        return customViewHolder;
    }

    @Override
    public int getItemCount() {
        return homePageBeanList.size();
    }

    @Override
    public int getItemViewType(int position) {

        return position;

    }
}