package com.manish.db.table

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity
class Category() {

    @ColumnInfo(name = "catName")
    var catName: String? = null

    @ColumnInfo(name = "parent")
    var parent: Int? = null
    @PrimaryKey
    var catId: Int? = null
    @ColumnInfo(name = "mappingId")
    var mappingId: Int? = null



}