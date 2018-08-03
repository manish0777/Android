package com.manish;

import android.arch.lifecycle.Observer;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.manish.db.table.ProductVarient;
import com.manish.viewmodel.DataViewModel;

import java.util.List;

public class PDPScreen extends BaseActivity {
    private TextView tvName;
    private int pId;
    private String pName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdp_screen);
        tvName = findViewById(R.id.tv_Name);
        pId = getIntent().getIntExtra("pId", 0);
        pName=getIntent().getStringExtra("pName");
        tvName.setText("Product Name : "+pName);
        getSupportActionBar().setTitle("Product Details");
        getProductDeatils();
    }

    private void getProductDeatils() {
        DataViewModel dataViewModel = new DataViewModel(ApplicationLoader.getInstance());
        dataViewModel.getVarientTypes(pId).observe(this, new Observer<List<ProductVarient>>() {
            @Override
            public void onChanged(@Nullable List<ProductVarient> productVarients) {
                Log.d(this.getClass().getName(), "productVarients===" + productVarients.size());
                setUpVarientView(productVarients);

            }
        });

    }

    private void setUpVarientView(List<ProductVarient> productVarients) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.varient);
        for (int i = 0; i < productVarients.size(); i++) {
            TextView valueTV = new TextView(this);
            valueTV.setText("Select "+productVarients.get(i).getVType());
            valueTV.setTag(productVarients.get(i).getVType());
            valueTV.setId(i+1);
            valueTV.setTextSize(20);
            valueTV.setTextColor(getResources().getColor(R.color.colorPrimary));
            valueTV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            valueTV.setPadding(10, 30, 0, 3);
            valueTV.setTypeface(Typeface.DEFAULT_BOLD);
            valueTV.setGravity(Gravity.CENTER);
            linearLayout.addView(valueTV);
            valueTV.setOnClickListener(btnclick);
        }
    }

    View.OnClickListener btnclick = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            displayVarient(view.getTag().toString());

        }
    };

    private void displayVarient(String tag) {
        DataViewModel dataViewModel = new DataViewModel(ApplicationLoader.getInstance());
        dataViewModel.getProductVarient(pId,tag).observe(this, new Observer<List<ProductVarient>>() {
            @Override
            public void onChanged(@Nullable List<ProductVarient> productVarients) {
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_varientvalue);
                linearLayout.removeAllViews();
                for (ProductVarient productVarient : productVarients) {
                    for (int i = 0; i < productVarients.size(); i++) {
                        TextView valueTV = new TextView(PDPScreen.this);
                        valueTV.setText(productVarients.get(i).getVValue());
                        valueTV.setTextSize(14);
                        valueTV.setTextColor(getResources().getColor(R.color.colorAccent));
                        valueTV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        valueTV.setPadding(10, 5, 0, 3);
                        valueTV.setTypeface(Typeface.DEFAULT_BOLD);
                        valueTV.setGravity(Gravity.CENTER);
                        linearLayout.addView(valueTV);
                    }
                }
            }
        });

    }
}
