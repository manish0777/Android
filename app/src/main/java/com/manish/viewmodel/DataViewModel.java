package com.manish.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.manish.ApplicationLoader;
import com.manish.db.DatabaseInterface;
import com.manish.db.EcomDataBase;
import com.manish.db.table.Category;
import com.manish.db.table.MostOrderdProduct;
import com.manish.db.table.MostSharedProduct;
import com.manish.db.table.MostViewedProduct;
import com.manish.db.table.Product;
import com.manish.db.table.ProductVarient;
import com.manish.db.table.SubCategory;

import java.util.List;

public class DataViewModel extends AndroidViewModel {

    private DatabaseInterface databaseInterface;

    public DataViewModel(@NonNull Application application) {
        super(application);
        EcomDataBase ecomDataBase = EcomDataBase.getEcomDataBase(application);
        databaseInterface = ecomDataBase.getDatabaseInterface();

    }

    private DatabaseInterface getDatabaseInterface() {
        if (databaseInterface == null) {
            EcomDataBase ecomDataBase = EcomDataBase.getEcomDataBase(ApplicationLoader.getInstance());
            databaseInterface = ecomDataBase.getDatabaseInterface();
        }
        return databaseInterface;
    }


    public LiveData<List<Category>> getAllCategory(int parent) {
        databaseInterface = getDatabaseInterface();
        return databaseInterface.getAllCategory(parent);
    }

    public LiveData<List<Product>> getAllProductByCatId(int catId) {
        databaseInterface = getDatabaseInterface();
        return databaseInterface.getProductByCatId(catId);
    }

    public LiveData<List<MostViewedProduct>> getMostViewedProduct() {
        databaseInterface = getDatabaseInterface();
        return databaseInterface.getMostViewedProduct();
    }

    public LiveData<List<MostOrderdProduct>> getMostOrderedProduct() {
        databaseInterface = getDatabaseInterface();
        return databaseInterface.getMostOrderedProduct();
    }
    public LiveData<List<MostSharedProduct>> getMostSharedProduct() {
        databaseInterface = getDatabaseInterface();
        return databaseInterface.getMostSharedProduct();
    }

    public LiveData<List<ProductVarient>> getProductVarient(int pId,String value) {
        databaseInterface = getDatabaseInterface();
        return databaseInterface.getProductVarint(pId,value);
    }
    public LiveData<List<ProductVarient>> getVarientTypes(int pId) {
        databaseInterface = getDatabaseInterface();
        return databaseInterface.getVarientTypes(pId);
    }
    public LiveData<List<Category>> getSubCategories(int catId){
        databaseInterface=getDatabaseInterface();
        return databaseInterface.getSubCategories(catId);
    }
}
