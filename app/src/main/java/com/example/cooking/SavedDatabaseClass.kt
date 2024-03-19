package com.example.cooking

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SavedData::class], version = 3)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun savedDao(): SavedDao
}