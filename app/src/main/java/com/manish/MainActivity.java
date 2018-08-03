package com.manish;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.manish.adapter.HomeCategoryAdapter;
import com.manish.adapter.HomePageAdapter;
import com.manish.db.DatabaseInterface;
import com.manish.db.EcomDataBase;
import com.manish.db.table.Category;
import com.manish.model.HomePageBean;
import com.manish.utils.Apputil;
import com.manish.utils.DataUtils;
import com.manish.viewmodel.DataViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private List<Category> categoryArrayList = new ArrayList<>();
    private DatabaseInterface databaseInterface;
    private DataViewModel dataViewModel;
    private HomePageAdapter homePageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EcomDataBase ecomDataBase = EcomDataBase.getEcomDataBase(MainActivity.this);
        databaseInterface = ecomDataBase.getDatabaseInterface();
        dataViewModel = ViewModelProviders.of(MainActivity.this).get(DataViewModel.class);

            DataViewModel dataViewModel = new DataViewModel(ApplicationLoader.getInstance());
            dataViewModel.getAllCategory(0).observe(this, new Observer<List<Category>>() {
                @Override
                public void onChanged(@Nullable List<Category> categoryList) {

                    if (categoryList == null || categoryList.size() <= 0) {
                        if(Apputil.checkInternetConnection()) {
                            DataUtils.getResult();
                        }else{
                            showNetworkDialog();
                        }
                    }
                }
            });

        setViews();
    }

    private void showNetworkDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Alert!!");
        alertDialog.setCancelable(false);
        alertDialog.setMessage("Please enable your internet connection only once to get sync data");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();

            }
        });
        alertDialog.show();
    }

    private void setViews() {
        List<HomePageBean> homePageBeanList = new ArrayList<>();
        try {
            String res = Apputil.readText();
            JSONArray jsonArray = new JSONArray(res);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                HomePageBean pageBean = new HomePageBean();
                pageBean.displayId = object.getString("id");
                homePageBeanList.add(pageBean);
            }
        } catch (Exception e) {
            e.printStackTrace();


        }
        RecyclerView rvhome = (RecyclerView) findViewById(R.id.rv_home);
        LinearLayoutManager hLayoutManager = new LinearLayoutManager(this);
        hLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvhome.setLayoutManager(hLayoutManager);
        homePageAdapter = new HomePageAdapter(MainActivity.this, homePageBeanList);
        rvhome.setAdapter(homePageAdapter);
        getCategoryList();
    }

    private void getCategoryList() {
        dataViewModel.getAllCategory(0).observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable final List<Category> list) {
                homePageAdapter.setCategoryList((ArrayList<Category>) list);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (HomeCategoryAdapter.isRefresh) {
            HomeCategoryAdapter.isRefresh = false;
            getCategoryList();
        } else {
            super.onBackPressed();
        }
    }
}
