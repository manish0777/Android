package com.manish.db.table

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class MostViewedProduct {

    @PrimaryKey
    var id: Int? = null

    @ColumnInfo(name = "viewcount")
    var viewcount: String? = null
}