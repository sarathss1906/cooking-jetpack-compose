package com.example.cooking.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cooking.api.CookingApi
import com.example.cooking.view.response.MealsCategory
import com.example.cooking.view.response.MealsCuisine
import com.example.cooking.view.response.MealsIngredients
import com.example.cooking.view.response.MealsListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val cookingApi: CookingApi) : ViewModel() {


    val categoryViewState = MutableStateFlow<CategoryViewState>(CategoryViewState.EmptyState)
    val ingredientsViewState =
        MutableStateFlow<IngredientsViewState>(IngredientsViewState.EmptyState)
    val searchViewState = MutableStateFlow<SearchViewState>(SearchViewState.EmptyState)
    val cuisineViewState = MutableStateFlow<CuisinesViewState>(CuisinesViewState.EmptyState)
    val searchVisibleText = MutableStateFlow<SearchVisibleViewState>(SearchVisibleViewState.EmptyState)

    fun getMealCategory() {
        viewModelScope.launch {
            categoryViewState.value = CategoryViewState.Loading
            val response = cookingApi.searchMealByCategory()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    categoryViewState.value = CategoryViewState.Success(it.meals)
                }
            } else {
                categoryViewState.value = CategoryViewState.Error(
                    errorCode = response.code(),
                    statusMessage = response.message()
                )
            }
        }
    }

    fun getMealCuisine() {
        viewModelScope.launch {
            cuisineViewState.value = CuisinesViewState.Loading
            val response = cookingApi.searchMealByCuisine()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    cuisineViewState.value = CuisinesViewState.Success(it.meals)
                }
            } else {
                cuisineViewState.value = CuisinesViewState.Error(
                    errorCode = response.code(),
                    statusMessage = response.message()
                )
            }
        }
    }

    fun getMealIngredients() {
        viewModelScope.launch {
            ingredientsViewState.value = IngredientsViewState.Loading
            val response = cookingApi.searchMealByIngredients()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    ingredientsViewState.value = IngredientsViewState.Success(it.meals)
                }
            } else {
                ingredientsViewState.value = IngredientsViewState.Error(
                    errorCode = response.code(),
                    statusMessage = response.message()
                )
            }
        }
    }

    fun getMealSearchText(searchText: String) {
        viewModelScope.launch {
            searchViewState.value = SearchViewState.Loading
            val response = cookingApi.searchByText(searchText)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    searchViewState.value = SearchViewState.Success(it.meals)
                }
            } else {
                searchViewState.value = SearchViewState.Error(
                    errorCode = response.code(),
                    statusMessage = response.message()
                )
            }
        }
    }
}

sealed class SearchViewState {
    object Loading : SearchViewState()

    object EmptyState : SearchViewState()

    data class Error(val errorCode: Int, val statusMessage: String) : SearchViewState()

    data class Success(val data: List<MealsListItem>?) : SearchViewState()
}

sealed class CategoryViewState {
    object Loading : CategoryViewState()

    object EmptyState : CategoryViewState()

    data class Error(val errorCode: Int, val statusMessage: String) : CategoryViewState()

    data class Success(val data: List<MealsCategory>) : CategoryViewState()
}

sealed class IngredientsViewState {
    object Loading : IngredientsViewState()

    object EmptyState : IngredientsViewState()

    data class Error(val errorCode: Int, val statusMessage: String) : IngredientsViewState()

    data class Success(val data: List<MealsIngredients>) : IngredientsViewState()
}

sealed class CuisinesViewState {
    object Loading : CuisinesViewState()

    object EmptyState : CuisinesViewState()

    data class Error(val errorCode: Int, val statusMessage: String) : CuisinesViewState()

    data class Success(val data: List<MealsCuisine>) : CuisinesViewState()
}

sealed class SearchVisibleViewState{

    object EmptyState : SearchVisibleViewState()
    object Success :SearchVisibleViewState()
    object Loading : SearchVisibleViewState()
}