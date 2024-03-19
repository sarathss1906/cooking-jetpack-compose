package com.example.cooking

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "food_category")
data class SavedData(
    @PrimaryKey val idCategory: Int,
    @ColumnInfo(name = "strName") val strName: String?,
    @ColumnInfo(name = "strCategory") val strCategory: String?,
    @ColumnInfo(name = "strCategoryThumb") val strCategoryThumb: String?,
    @ColumnInfo(name = "strArea") val strArea: String?,
    @ColumnInfo(name = "strCategoryDescription") val strCategoryDescription: String?,
    @ColumnInfo(name = "strCategoryIngredients") val strCategoryIngredients: String?
)
