package com.example.kauppalappunen_02.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_data_table")
data class Recipe (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recipe_id")
    var id:Int,
    @ColumnInfo(name = "recipe_name")
    var name:String,
    @ColumnInfo(name = "ingredients")
    var ingredients:String
    )
