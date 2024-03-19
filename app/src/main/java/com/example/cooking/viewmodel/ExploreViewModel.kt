package com.example.cooking.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cooking.CookingApplication
import com.example.cooking.SavedData
import com.example.cooking.api.CookingApi
import com.example.cooking.view.response.FoodCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val cookingApi: CookingApi
) :
    ViewModel() {

    val exploreViewState = MutableStateFlow<ExploreViewState>(ExploreViewState.EmptyState)
    val ingredientViewState = MutableStateFlow<IngredientViewState>(IngredientViewState.EmptyState)
    val exploreCuisineViewState = MutableStateFlow<ExploreCuisineViewState>(ExploreCuisineViewState.EmptyStateExplore)
    val savedViewState = MutableStateFlow<SavedThumbnailViewState>(SavedThumbnailViewState.EmptyState)
    private val stringDao = CookingApplication.recipeDatabase?.savedDao()



    fun foodByCategoryExplore(foodCategory: String) {
        viewModelScope.launch {
            exploreViewState.value = ExploreViewState.Loading
            val response = cookingApi.getListByCategory(foodCategory)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    exploreViewState.value = ExploreViewState.Success(it.meals)
                }
            } else {
                exploreViewState.value = ExploreViewState.Error(
                    errorCode = response.code(),
                    statusMessage = response.message()
                )
            }
        }
    }
    fun getFoodByIngredient(foodCategory: String) {
        viewModelScope.launch {
            ingredientViewState.value = IngredientViewState.Loading
            val response = cookingApi.getListByIngredient(foodCategory)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    ingredientViewState.value = IngredientViewState.Success(it.meals)
                }
            } else {
                ingredientViewState.value = IngredientViewState.Error(
                    errorCode = response.code(),
                    statusMessage = response.message()
                )
            }
        }
    }

    fun getFoodByCuisine(foodCategory: String) {
        viewModelScope.launch {
            exploreCuisineViewState.value = ExploreCuisineViewState.Loading
            val response = cookingApi.getListByCuisine(foodCategory)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    exploreCuisineViewState.value = ExploreCuisineViewState.Success(it.meals)
                }
            } else {
                exploreCuisineViewState.value = ExploreCuisineViewState.Error(
                    errorCode = response.code(),
                    statusMessage = response.message()
                )
            }
        }
    }


    fun getSavedMealList(){
        viewModelScope.launch {
            val stringList = stringDao?.getSaveMealData()
            savedViewState.value = SavedThumbnailViewState.Success(stringList)
        }
    }
}

sealed class ExploreViewState {
    object Loading : ExploreViewState()

    object EmptyState : ExploreViewState()

    data class Error(val errorCode: Int, val statusMessage: String) : ExploreViewState()

    data class Success(val data: List<FoodCategory?>) : ExploreViewState()
}

sealed class IngredientViewState {
    object Loading : IngredientViewState()

    object EmptyState : IngredientViewState()

    data class Error(val errorCode: Int, val statusMessage: String) : IngredientViewState()

    data class Success(val data: List<FoodCategory?>) : IngredientViewState()
}

sealed class ExploreCuisineViewState {
    object Loading : ExploreCuisineViewState()

    object EmptyStateExplore : ExploreCuisineViewState()

    data class Error(val errorCode: Int, val statusMessage: String) : ExploreCuisineViewState()

    data class Success(val data: List<FoodCategory?>) : ExploreCuisineViewState()
}

sealed class SavedThumbnailViewState {
    object Loading : SavedThumbnailViewState()

    object EmptyState : SavedThumbnailViewState()

    data class Error(val errorCode: Int, val statusMessage: String) : SavedThumbnailViewState()

    data class Success(val data: List<SavedData?>?) : SavedThumbnailViewState()
}