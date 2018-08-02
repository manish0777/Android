package com.manish.db.table

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
public class MostOrderdProduct {
    @PrimaryKey
    var id: Int? = null

    @ColumnInfo(name = "orderCount")
    var orderCount: String? = null
}