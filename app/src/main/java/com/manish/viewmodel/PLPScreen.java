package com.manish.viewmodel;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.manish.BaseActivity;
import com.manish.R;
import com.manish.adapter.ProductListAdapter;
import com.manish.db.table.Product;

import java.util.ArrayList;
import java.util.List;

public class PLPScreen extends BaseActivity {
    private int catId;
    private List<Product> productList = new ArrayList<>();
    private ProductListAdapter productListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plp_screen);
        catId = getIntent().getIntExtra("cat_id", 0);
        final RecyclerView rvhome = (RecyclerView) findViewById(R.id.rv_plp);
        int numberOfColumns = 2;
        GridLayoutManager gridlm = new GridLayoutManager(this, numberOfColumns);
        rvhome.setLayoutManager(gridlm);
        productListAdapter = new ProductListAdapter(this, productList);
        rvhome.setAdapter(productListAdapter);
        loadProduct();

    }

    private void loadProduct() {
        ViewModelProviders.of(this).get(DataViewModel.class).getAllProductByCatId(catId).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {
                if (products != null && products.size() > 0) {
                    productListAdapter.setProductList((ArrayList<Product>) products);
                }else{
                    Product product=new Product();
                    product.setPName("No Product Found");
                    products.add(product);
                    productListAdapter.setProductList((ArrayList<Product>) products);
                }


            }
        });
    }
}
