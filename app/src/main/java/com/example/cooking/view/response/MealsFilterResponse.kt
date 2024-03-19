package com.example.cooking.view.response

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class MealsFilterResponse(

    @field:SerializedName("meals")
    val meals: List<FoodCategory>
) : Parcelable

@Keep
@Parcelize
data class FoodCategory(

    @field:SerializedName("strMealThumb")
    val strMealThumb: String? = null,

    @field:SerializedName("idMeal")
    val idMeal: String? = null,

    @field:SerializedName("strMeal")
    val strMeal: String? = null
) : Parcelable
