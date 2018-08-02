package com.manish.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.manish.db.table.*

/**
 * Created by Manish Singh
 */
@Dao
interface DatabaseInterface {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllCategory(list: List<Category>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllProduct(list: List<Product>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMostViewedProduct(productlist: List<MostViewedProduct>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveProductVarient(productVarient: List<ProductVarient>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMostOrderedProduct(orderProductList: List<MostOrderdProduct>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMostSharedProduct(mostSharedProduct: List<MostSharedProduct>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllSubcat(mostSharedProduct: List<SubCategory>)


    @Query("select * from MostSharedProduct")
    fun getMostSharedProduct(): LiveData<List<MostSharedProduct>>

    @Query("select DISTINCT varientValue,varientType from ProductVarient where productId = :arg0 AND varientType = (:arg1)")
    fun getProductVarint(arg0: Int, arg1: String): LiveData<List<ProductVarient>>

    @Query("select * from Product where catgoryID = :arg0")
    fun getProductByCatId(arg0: Int): LiveData<List<Product>>


    @Query("select * from Category")
    fun getAllCategory(): LiveData<List<Category>>

    @Query("select * from MostViewedProduct")
    fun getMostViewedProduct(): LiveData<List<MostViewedProduct>>

    @Query("select * from MostOrderdProduct")
    fun getMostOrderedProduct(): LiveData<List<MostOrderdProduct>>

    @Query("select * from SubCategory where catId = :arg0")
    fun getSubCategories(arg0: Int): LiveData<List<SubCategory>>

    @Query("select DISTINCT varientType from ProductVarient where productId = :arg0")
    fun getVarientTypes(arg0: Int): LiveData<List<ProductVarient>>


}