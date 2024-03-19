package com.example.cooking.api

import com.example.cooking.utils.ApiConstants.NetworkEndPoints.FILTER_CATEGORY
import com.example.cooking.utils.ApiConstants.NetworkEndPoints.LIST_BY_AREA
import com.example.cooking.utils.ApiConstants.NetworkEndPoints.LIST_BY_CATEGORY
import com.example.cooking.utils.ApiConstants.NetworkEndPoints.LIST_BY_ID
import com.example.cooking.utils.ApiConstants.NetworkEndPoints.LIST_BY_INGREDIENT
import com.example.cooking.utils.ApiConstants.NetworkEndPoints.RANDOM_MEAL
import com.example.cooking.utils.ApiConstants.NetworkEndPoints.SEARCH_BY_NAME
import com.example.cooking.view.response.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CookingApi {

    @GET(FILTER_CATEGORY)
    suspend fun getListByCategory(
        @Query("c") foodCategory:String
    ): Response<MealsFilterResponse>

    @GET(FILTER_CATEGORY)
    suspend fun getListByIngredient(
        @Query("i") ingredient:String
    ): Response<MealsFilterResponse>

    @GET(FILTER_CATEGORY)
    suspend fun getListByCuisine(
        @Query("a") cuisine:String
    ): Response<MealsFilterResponse>

    @GET(LIST_BY_ID)
    suspend fun getSingleMealById(
        @Query("i") mealsId:String?
    ): Response<MealsListResponse>

    @GET(RANDOM_MEAL)
    suspend fun getSingleMealRandom(): Response<MealsListResponse>

    @GET(LIST_BY_CATEGORY)
    suspend fun searchMealByCategory(): Response<MealsCategoryResponse>

    @GET(LIST_BY_AREA)
    suspend fun searchMealByCuisine(): Response<MealsCuisineResponse>

    @GET(LIST_BY_INGREDIENT)
    suspend fun searchMealByIngredients(): Response<MealsIngredientsResponse>

    @GET(SEARCH_BY_NAME)
    suspend fun searchByText(
        @Query("s") text:String
    ) : Response<MealsListResponse>
}