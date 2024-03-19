package com.example.cooking.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cooking.CookingApplication
import com.example.cooking.SavedData
import com.example.cooking.api.CookingApi
import com.example.cooking.utils.Utility.getIngredientsImage
import com.example.cooking.view.response.IngredientsList
import com.example.cooking.view.response.MealsListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleMealViewModel @Inject constructor(
    private val cookingApi: CookingApi
) :
    ViewModel() {

    val singleMealsViewState =
        MutableStateFlow<SingleMealsViewState>(SingleMealsViewState.EmptyState)
    val randomMealViewState = MutableStateFlow<RandomMealViewState>(RandomMealViewState.EmptyState)
    val savedViewState = MutableStateFlow<SavedViewState>(SavedViewState.EmptyState)
    val idSavedData = MutableStateFlow("")
    private val stringDao = CookingApplication.recipeDatabase?.savedDao()


    fun getMealDetailsById(mealsId:String?) {
        viewModelScope.launch {
            singleMealsViewState.value = SingleMealsViewState.Loading
            val response = cookingApi.getSingleMealById(mealsId = mealsId)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    singleMealsViewState.value =
                        SingleMealsViewState.Success(it.meals?.firstOrNull())
                }
            } else {
                singleMealsViewState.value = SingleMealsViewState.Error(
                    errorCode = response.code(),
                    statusMessage = response.message()
                )
            }
        }
    }



    fun getRandomMealDetails() {
        viewModelScope.launch {
            randomMealViewState.value = RandomMealViewState.Loading
            val response = cookingApi.getSingleMealRandom()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    randomMealViewState.value = RandomMealViewState.Success(it.meals?.firstOrNull())
                    idSavedData.value = it.meals?.firstOrNull()?.idMeal.toString()
                }
            } else {
                randomMealViewState.value = RandomMealViewState.Error(
                    errorCode = response.code(),
                    statusMessage = response.message()
                )
            }
        }
    }

    fun getSavedMealDetails(mealId:String?){
        viewModelScope.launch {
            val description = stringDao?.getSaveMealDescriptionData(mealId = mealId)
            savedViewState.value = SavedViewState.Success(description = description)
        }
    }

    fun getIngredientsList(mealDetails: MealsListItem?): ArrayList<IngredientsList?> {
        val ingredientsList = ArrayList<IngredientsList?>()
        ingredientsList.add(
            IngredientsList(
                ingredients = mealDetails?.strIngredient1,
                measurement = mealDetails?.strMeasure1,
                image = getIngredientsImage(mealDetails?.strIngredient1)
            )
        )
        ingredientsList.add(
            IngredientsList(
                ingredients = mealDetails?.strIngredient2,
                measurement = mealDetails?.strMeasure2,
                image = getIngredientsImage(mealDetails?.strIngredient2)
            )
        )
        ingredientsList.add(
            IngredientsList(
                ingredients = mealDetails?.strIngredient3,
                measurement = mealDetails?.strMeasure3,
                image = getIngredientsImage(mealDetails?.strIngredient3)
            )
        )
        ingredientsList.add(
            IngredientsList(
                ingredients = mealDetails?.strIngredient4,
                measurement = mealDetails?.strMeasure4,
                image = getIngredientsImage(mealDetails?.strIngredient4)
            )
        )
        ingredientsList.add(
            IngredientsList(
                ingredients = mealDetails?.strIngredient5,
                measurement = mealDetails?.strMeasure5,
                image = getIngredientsImage(mealDetails?.strIngredient5)
            )
        )
        ingredientsList.add(
            IngredientsList(
                ingredients = mealDetails?.strIngredient6,
                measurement = mealDetails?.strMeasure6,
                image = getIngredientsImage(mealDetails?.strIngredient6)
            )
        )
        ingredientsList.add(
            IngredientsList(
                ingredients = mealDetails?.strIngredient7,
                measurement = mealDetails?.strMeasure7,
                image = getIngredientsImage(mealDetails?.strIngredient7)
            )
        )
        ingredientsList.add(
            IngredientsList(
                ingredients = mealDetails?.strIngredient8,
                measurement = mealDetails?.strMeasure8,
                image = getIngredientsImage(mealDetails?.strIngredient8)
            )
        )
        ingredientsList.add(
            IngredientsList(
                ingredients = mealDetails?.strIngredient9,
                measurement = mealDetails?.strMeasure9,
                image = getIngredientsImage(mealDetails?.strIngredient9)
            )
        )
        ingredientsList.add(
            IngredientsList(
                ingredients = mealDetails?.strIngredient10,
                measurement = mealDetails?.strMeasure10,
                image = getIngredientsImage(mealDetails?.strIngredient10)
            )
        )
        ingredientsList.add(
            IngredientsList(
                ingredients = mealDetails?.strIngredient11,
                measurement = mealDetails?.strMeasure11,
                image = getIngredientsImage(mealDetails?.strIngredient11)
            )
        )
        ingredientsList.add(
            IngredientsList(
                ingredients = mealDetails?.strIngredient12,
                measurement = mealDetails?.strMeasure12,
                image = getIngredientsImage(mealDetails?.strIngredient12)
            )
        )
        ingredientsList.add(
            IngredientsList(
                ingredients = mealDetails?.strIngredient13,
                measurement = mealDetails?.strMeasure13,
                image = getIngredientsImage(mealDetails?.strIngredient13)
            )
        )
        ingredientsList.add(
            IngredientsList(
                ingredients = mealDetails?.strIngredient14,
                measurement = mealDetails?.strMeasure14,
                image = getIngredientsImage(mealDetails?.strIngredient14)
            )
        )
        ingredientsList.add(
            IngredientsList(
                ingredients = mealDetails?.strIngredient15,
                measurement = mealDetails?.strMeasure15,
                image = getIngredientsImage(mealDetails?.strIngredient15)
            )
        )
        ingredientsList.add(
            IngredientsList(
                ingredients = mealDetails?.strIngredient16,
                measurement = mealDetails?.strMeasure16,
                image = getIngredientsImage(mealDetails?.strIngredient16)
            )
        )
        ingredientsList.add(
            IngredientsList(
                ingredients = mealDetails?.strIngredient17,
                measurement = mealDetails?.strMeasure17,
                image = getIngredientsImage(mealDetails?.strIngredient17)
            )
        )
        ingredientsList.add(
            IngredientsList(
                ingredients = mealDetails?.strIngredient18,
                measurement = mealDetails?.strMeasure18,
                image = getIngredientsImage(mealDetails?.strIngredient18)
            )
        )
        ingredientsList.add(
            IngredientsList(
                ingredients = mealDetails?.strIngredient19,
                measurement = mealDetails?.strMeasure19,
                image = getIngredientsImage(mealDetails?.strIngredient19)
            )
        )
        return ingredientsList
    }
}

sealed class SingleMealsViewState {
    object Loading : SingleMealsViewState()

    object EmptyState : SingleMealsViewState()

    data class Error(val errorCode: Int, val statusMessage: String) : SingleMealsViewState()

    data class Success(val data: MealsListItem?) : SingleMealsViewState()
}

sealed class RandomMealViewState {
    object Loading : RandomMealViewState()

    object EmptyState : RandomMealViewState()

    data class Error(val errorCode: Int, val statusMessage: String) : RandomMealViewState()

    data class Success(val data: MealsListItem?) : RandomMealViewState()
}

sealed class SavedViewState {
    object Loading : SavedViewState()

    object EmptyState : SavedViewState()

    data class Error(val errorCode: Int, val statusMessage: String) : SavedViewState()

    data class Success(val description: SavedData?) : SavedViewState()
}