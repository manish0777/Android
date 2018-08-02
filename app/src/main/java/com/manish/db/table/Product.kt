package com.manish.db.table

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Product {

    @PrimaryKey
    var pId: Int? = null
    var catgoryID:Int?=null
    @ColumnInfo(name = "productName")
    var pName: String? = null


    @ColumnInfo(name = "date")
    var date: Int? = null

}