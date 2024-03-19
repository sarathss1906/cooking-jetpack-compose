package com.example.cooking.view.response

import com.google.gson.annotations.SerializedName

data class MealsCuisineResponse(

	@field:SerializedName("meals")
	val meals: List<MealsCuisine>
)

data class MealsCuisine(

	@field:SerializedName("strArea")
	val strArea: String? = null
)

data class MealsCategoryResponse(

	@field:SerializedName("meals")
	val meals: List<MealsCategory>
)

data class MealsCategory(

	@field:SerializedName("strCategory")
	val strCategory: String? = null
)

data class MealsIngredientsResponse(

	@field:SerializedName("meals")
	val meals: List<MealsIngredients>
)

data class MealsIngredients(

	@field:SerializedName("strDescription")
	val strDescription: String? = null,

	@field:SerializedName("strIngredient")
	val strIngredient: String? = null,

	@field:SerializedName("strType")
	val strType: Any? = null,

	@field:SerializedName("idIngredient")
	val idIngredient: String? = null
)