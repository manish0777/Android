package com.manish.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.manish.db.table.Category;
import com.manish.db.table.MostOrderdProduct;
import com.manish.db.table.MostSharedProduct;
import com.manish.db.table.MostViewedProduct;
import com.manish.db.table.Product;
import com.manish.db.table.ProductVarient;
import com.manish.db.table.SubCategory;

@Database(entities = {Category.class, MostViewedProduct.class,Product.class, ProductVarient.class, MostOrderdProduct.class, MostSharedProduct.class, SubCategory.class}, version = 1,exportSchema = false)
public abstract class EcomDataBase extends RoomDatabase {


    private static EcomDataBase ecomDataBase;

    public abstract DatabaseInterface getDatabaseInterface();

    public static EcomDataBase getEcomDataBase(final Context context) {
        if (null == ecomDataBase) {
            synchronized (EcomDataBase.class) {
                ecomDataBase = Room.databaseBuilder(context.getApplicationContext(), EcomDataBase.class,"ecom_manish.db").build();
            }
        }
        return ecomDataBase;
    }
}