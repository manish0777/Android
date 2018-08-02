package com.manish.db.table

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity
class ProductVarient {

    @PrimaryKey
    var vId: Int? = null
    @ColumnInfo(name = "productId")
    var productId:Int?=null
    @ColumnInfo(name = "varientType")
    var vType: String? = null
    @ColumnInfo(name = "varientValue")
    var vValue: String? = null

}