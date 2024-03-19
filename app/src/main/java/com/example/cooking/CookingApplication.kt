package com.example.cooking

import android.app.Application
import android.content.Context
import com.example.cooking.di.DatabaseProvider
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class CookingApplication : Application() {


    companion object {
        lateinit var appContext: Context

        var recipeDatabase: RecipeDatabase? = null

        fun getString(id: Int): String {
            return appContext.getString(id)
        }

    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        recipeDatabase = DatabaseProvider.provideRecipeDatabase(appContext)
    }


}