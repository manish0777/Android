package com.manish;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EcomDataBase ecomDataBase = EcomDataBase.getEcomDataBase(MainActivity.this);
        databaseInterface = ecomDataBase.getDatabaseInterface();
        dataViewModel = ViewModelProviders.of(MainActivity.this).get(DataViewModel.class);
        if (Apputil.checkInternetConnection()) {
            DataViewModel dataViewModel = new DataViewModel(ApplicationLoader.getInstance());
            dataViewModel.getAllCategory().observe(this, new Observer<List<Category>>() {
                @Override
                public void onChanged(@Nullable List<Category> categoryList) {

                    if (categoryList == null || categoryList.size() <= 0) {
                        DataUtils.getResult();
                    }
                }
            });
        }
        setViews();
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
        final HomePageAdapter homePageAdapter = new HomePageAdapter(MainActivity.this, homePageBeanList);
        rvhome.setAdapter(homePageAdapter);
        dataViewModel.getAllCategory().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable final List<Category> list) {
                // Update the cached copy of the words in the adapter.
                homePageAdapter.setCategoryList((ArrayList<Category>) list);
            }
        });


    }




}