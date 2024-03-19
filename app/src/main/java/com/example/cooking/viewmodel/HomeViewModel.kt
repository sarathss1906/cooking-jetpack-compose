package com.example.cooking.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cooking.api.CookingApi
import com.example.cooking.view.response.FoodCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cookingApi: CookingApi
) :
    ViewModel() {

    val homeViewState = MutableStateFlow<HomeViewState>(HomeViewState.EmptyState)
    val breakfastViewState = MutableStateFlow<HomeViewState>(HomeViewState.EmptyState)
    val veganViewState = MutableStateFlow<HomeViewState>(HomeViewState.EmptyState)
    val starterViewState = MutableStateFlow<HomeViewState>(HomeViewState.EmptyState)
    val miscellaneousViewState = MutableStateFlow<HomeViewState>(HomeViewState.EmptyState)


    fun foodByCategoryBreakfast(foodCategory:String) {
        viewModelScope.launch {
            homeViewState.value = HomeViewState.Loading
            val response = cookingApi.getListByCategory(foodCategory)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    breakfastViewState.value = HomeViewState.Success(data = it.meals, heading = foodCategory)
                    homeViewState.value = breakfastViewState.value

                }
            } else {
                homeViewState.value = HomeViewState.Error(
                    errorCode = response.code(),
                    statusMessage = response.message()
                )
            }
        }
    }
    fun foodByCategoryStarter(foodCategory:String) {
        viewModelScope.launch {
            homeViewState.value = HomeViewState.Loading
            val response = cookingApi.getListByCategory(foodCategory)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    starterViewState.value = HomeViewState.Success(data = it.meals, heading = foodCategory)
                    homeViewState.value = starterViewState.value

                }
            } else {
                homeViewState.value = HomeViewState.Error(
                    errorCode = response.code(),
                    statusMessage = response.message()
                )
            }
        }
    }
    fun foodByCategoryVegan(foodCategory:String) {
        viewModelScope.launch {
            homeViewState.value = HomeViewState.Loading
            val response = cookingApi.getListByCategory(foodCategory)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    veganViewState.value = HomeViewState.Success(data = it.meals, heading = foodCategory)
                    homeViewState.value = veganViewState.value

                }
            } else {
                homeViewState.value = HomeViewState.Error(
                    errorCode = response.code(),
                    statusMessage = response.message()
                )
            }
        }
    }
    fun foodByCategoryMiscellaneous(foodCategory:String) {
        viewModelScope.launch {
            homeViewState.value = HomeViewState.Loading
            val response = cookingApi.getListByCategory(foodCategory)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    miscellaneousViewState.value = HomeViewState.Success(data = it.meals, heading = foodCategory)
                    homeViewState.value = miscellaneousViewState.value

                }
            } else {
                homeViewState.value = HomeViewState.Error(
                    errorCode = response.code(),
                    statusMessage = response.message()
                )
            }
        }
    }

}

sealed class HomeViewState{
    object Loading : HomeViewState()

    object EmptyState : HomeViewState()

    data class Error(val errorCode: Int, val statusMessage: String) : HomeViewState()

    data class Success(val data: List<FoodCategory>, val heading:String) : HomeViewState()
}