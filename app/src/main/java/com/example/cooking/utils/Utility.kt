package com.example.cooking.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.cooking.SavedData
import com.example.cooking.view.response.IngredientsList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Utility {
    fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
        return result
    }

    fun getIngredientsList(savedData: SavedData?): ArrayList<IngredientsList?> {
        val gson = Gson()
        val listType = object : TypeToken<ArrayList<IngredientsList?>>() {}.type
        return gson.fromJson(savedData?.strCategoryIngredients, listType)
    }

    fun getIngredientsImage(ingredient:String?): String {
        return "https://www.themealdb.com/images/ingredients/${ingredient}-small.png"
    }
}