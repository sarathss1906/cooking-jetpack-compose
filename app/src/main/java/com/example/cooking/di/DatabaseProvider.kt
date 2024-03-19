package com.example.cooking.di

import android.content.Context
import androidx.room.Room
import com.example.cooking.RecipeDatabase

object DatabaseProvider {
    private var recipeDatabase: RecipeDatabase? = null

    fun provideRecipeDatabase(context: Context): RecipeDatabase? {
        synchronized(this) {
            if (recipeDatabase == null) {
                recipeDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return recipeDatabase
        }
    }
}
