package com.example.cooking.view.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cooking.CookingApplication
import com.example.cooking.R
import com.example.cooking.SavedData
import com.example.cooking.ui.theme.Green
import com.example.cooking.ui.theme.White
import com.example.cooking.utils.Utility
import com.example.cooking.utils.showToast
import com.example.cooking.view.adapter.SingleMealLazyColumn
import com.example.cooking.view.components.Indicator
import com.example.cooking.view.response.SingleMealDetail
import com.example.cooking.viewmodel.RandomMealViewState
import com.example.cooking.viewmodel.SavedViewState
import com.example.cooking.viewmodel.SingleMealViewModel
import com.example.cooking.viewmodel.SingleMealsViewState
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@SuppressLint("SuspiciousIndentation")
@Composable
fun singleMealDetails(
    idMeal: String?,
    disableBackButton: Boolean?,
    navController: NavHostController
) {
    val viewModel: SingleMealViewModel = hiltViewModel()
    var isBookmarked by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (idMeal.isNullOrEmpty()) {
            viewModel.getRandomMealDetails()
        } else {
            viewModel.getSavedMealDetails(mealId = idMeal)
        }
    }

    viewModel.savedViewState.collectAsState().value.let { savedViewState ->
        when (savedViewState) {
            is SavedViewState.Success -> {
                val savedData = savedViewState.description



                if (idMeal != savedData?.idCategory.toString() && idMeal.isNullOrEmpty().not()) {
                    LaunchedEffect(Unit) {
                        isBookmarked = false
                        viewModel.getMealDetailsById(idMeal)
                    }
                } else if (idMeal == savedData?.idCategory.toString() && idMeal.isEmpty().not()) {
                    isBookmarked = true

                    val singleMealsDetail = SingleMealDetail(
                        idMeal = savedData?.idCategory.toString(),
                        image = savedData?.strCategoryThumb,
                        name = savedData?.strName,
                        description = savedData?.strCategoryDescription,
                        area = savedData?.strArea,
                        category = savedData?.strCategory,
                        ingredients = Utility.getIngredientsList(savedData)
                    )
                    SingleMeal(singleMealsDetail = singleMealsDetail, navController = navController, disableBackButton = disableBackButton, isSaved = isBookmarked)
                } else if (idMeal.isNullOrEmpty()) {
                    isBookmarked = savedData?.idCategory.toString() == viewModel.idSavedData.value
                }
            }
            else -> {
                //no op
            }
        }

    }

    viewModel.singleMealsViewState.collectAsState().value.let { singleMealsViewState ->
        when (singleMealsViewState) {
            is SingleMealsViewState.EmptyState -> {
                //no op
            }
            is SingleMealsViewState.Loading -> {
                Column(modifier = Modifier
                    .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Indicator()
                }
            }
            is SingleMealsViewState.Success -> {

                val singleMealDescription = singleMealsViewState.data

                val singleMealsDetail = SingleMealDetail(
                    idMeal = singleMealDescription?.idMeal,
                    image = singleMealDescription?.strMealThumb,
                    name = singleMealDescription?.strMeal,
                    description = singleMealDescription?.strInstructions,
                    area = singleMealDescription?.strArea,
                    category = singleMealDescription?.strCategory,
                    ingredients = viewModel.getIngredientsList(singleMealDescription)
                )
                SingleMeal(singleMealsDetail = singleMealsDetail, navController = navController, disableBackButton = disableBackButton, isSaved = isBookmarked)
            }

            is SingleMealsViewState.Error -> {
                CookingApplication.appContext.showToast(stringResource(R.string.api_error_message))
            }
        }
    }

    viewModel.randomMealViewState.collectAsState().value.let { randomMealViewState ->
        when (randomMealViewState) {
            is RandomMealViewState.EmptyState -> {
                //no op
            }
            is RandomMealViewState.Loading -> {
                Column(modifier = Modifier
                    .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Indicator()
                }
            }
            is RandomMealViewState.Success -> {

                val singleMealDescription = randomMealViewState.data


                viewModel.getSavedMealDetails(singleMealDescription?.idMeal)

                val singleMealsDetail = SingleMealDetail(
                    idMeal = singleMealDescription?.idMeal,
                    image = singleMealDescription?.strMealThumb,
                    name = singleMealDescription?.strMeal,
                    description = singleMealDescription?.strInstructions,
                    area = singleMealDescription?.strArea,
                    category = singleMealDescription?.strCategory,
                    ingredients = viewModel.getIngredientsList(singleMealDescription)
                )
                SingleMeal(singleMealsDetail = singleMealsDetail, navController = navController, disableBackButton = disableBackButton, isSaved = isBookmarked)
            }

            is RandomMealViewState.Error -> {
                CookingApplication.appContext.showToast(stringResource(R.string.api_error_message))
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun SingleMeal(singleMealsDetail: SingleMealDetail, disableBackButton: Boolean?,navController: NavHostController,isSaved:Boolean) {
    var isClicked by remember { mutableStateOf(false) }
    var isBookmarked by remember { mutableStateOf(false) }
    isBookmarked = isSaved
    val gson = Gson()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(

                title = {
                    Text(
                        text = singleMealsDetail.name ?: "",
                        color = White,
                        fontWeight = FontWeight.Medium
                    )
                },

                navigationIcon = {
                    if (disableBackButton == false) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.Filled.ArrowBack,
                                contentDescription = stringResource(id = R.string.back),
                                tint = White
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { isClicked = !isClicked }) {
                        MyIcon(isInserted = isBookmarked) {
                            isBookmarked = if (isBookmarked) {
                                // Perform delete operation
                                GlobalScope.launch(Dispatchers.IO) {
                                    CookingApplication.recipeDatabase?.savedDao()
                                        ?.delete(singleMealsDetail.idMeal)
                                }
                                false
                            } else {
                                // Perform insert operation
                                val myEntity = SavedData(
                                    idCategory = singleMealsDetail.idMeal?.toInt()
                                        ?: 0,
                                    strName = singleMealsDetail.name,
                                    strCategory = singleMealsDetail.category,
                                    strArea = singleMealsDetail.area,
                                    strCategoryThumb = singleMealsDetail.image,
                                    strCategoryDescription = singleMealsDetail.description,
                                    strCategoryIngredients = gson.toJson(
                                        singleMealsDetail.ingredients
                                    )
                                )
                                GlobalScope.launch(Dispatchers.IO) {
                                    CookingApplication.recipeDatabase?.savedDao()
                                        ?.insert(myEntity)
                                }
                                true
                            }
                        }

                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Green
                )
            )
        }
    ) {
        SingleMealLazyColumn(mealList = singleMealsDetail)
    }
}

@Composable
fun MyIcon(isInserted: Boolean, onClick: () -> Unit) {
    val imageRes = if (isInserted) {
        R.drawable.save_icon
    } else {
        R.drawable.unsave_icon
    }

    Image(
        painter = painterResource(id = imageRes),
        contentDescription = null,
        modifier = Modifier.clickable { onClick() }
    )
}
