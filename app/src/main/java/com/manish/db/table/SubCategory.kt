package com.manish.db.table

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class SubCategory {
    @PrimaryKey
    var subCatId: Int? = null

    @ColumnInfo(name = "catId")
    var catId: Int? = null

}