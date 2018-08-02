package com.manish.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.manish.PDPScreen;
import com.manish.R;
import com.manish.db.table.Category;
import com.manish.db.table.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {
    private List<Product> productList = new ArrayList<>();
    Activity activity;


    public ProductListAdapter(Activity activity, List<Product> productList) {
        this.activity = activity;
        this.productList = productList;

    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return productList.size();

    }


    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_cat, parent, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(view);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {


        final Product product = productList.get(position);
//        float reqspacing = screenWidthInDp / (int) 4.2;
//        holder.outerLayout.getLayoutParams().width = (int) reqspacing;
//        float dp = Resources.getSystem().getDisplayMetrics().density;
        holder.tvName.setText(product.getPName());
        holder.outerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, PDPScreen.class);
                intent.putExtra("pId",product.getPId());
                intent.putExtra("pName",product.getPName());
                activity.startActivity(intent);

            }
        });




    }






    static class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        RelativeLayout outerLayout;
        Button btn;

        public ProductViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            outerLayout = itemView.findViewById(R.id.outer_layout);

        }
    }

}




