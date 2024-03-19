package com.example.cooking.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceEvenly
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cooking.CookingApplication
import com.example.cooking.R
import com.example.cooking.ui.theme.Black
import com.example.cooking.ui.theme.White
import com.example.cooking.utils.AppConstants.TitleConstant.TITLE_CUISINE
import com.example.cooking.utils.AppConstants.TitleConstant.TITLE_INGREDIENT
import com.example.cooking.utils.AppConstants.TitleConstant.TITLE_SAVED
import com.example.cooking.utils.showToast
import com.example.cooking.view.components.ShowLoader
import com.example.cooking.view.components.foodThumbnail
import com.example.cooking.view.response.FoodCategory
import com.example.cooking.viewmodel.ExploreCuisineViewState
import com.example.cooking.viewmodel.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreMore(
    title: String?,
    mainNavController: NavHostController
) {
    val viewModel: ExploreViewModel = hiltViewModel()
    val exploreTitle = title
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(

                title = {
                    (if (title?.contains(TITLE_INGREDIENT) == true) {
                        title.removePrefix(TITLE_INGREDIENT)
                    } else if (title?.contains(TITLE_CUISINE) == true) {
                        title.removePrefix(TITLE_CUISINE)
                    } else {
                        title
                    })?.let {
                        Text(
                            text = it,
                            color = Black,
                            fontWeight = FontWeight.Medium
                        )
                    }
                },
                navigationIcon = {
                    if (title != TITLE_SAVED) {
                        IconButton(onClick = { mainNavController.popBackStack() }) {
                            Icon(
                                Icons.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back),
                                tint = Black
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = White
                )
            )
        }
    )
    {
        if (title == TITLE_SAVED) {
            LaunchedEffect(Unit) {
                viewModel.getSavedMealList()
            }
            viewModel.savedViewState.collectAsState().value.let { savedViewState ->
                when (savedViewState) {
                    is SavedThumbnailViewState.EmptyState -> {
                        //no op
                    }
                    is SavedThumbnailViewState.Loading -> {
                        ShowLoader()
                    }
                    is SavedThumbnailViewState.Success -> {
                        val savedList: List<FoodCategory>? = savedViewState.data?.map {
                            FoodCategory(
                                strMeal = it?.strName,
                                strMealThumb = it?.strCategoryThumb,
                                idMeal = it?.idCategory.toString()
                            )
                        }
                        if (savedList != null) {
                            showExploreList(
                                exploreList = savedList,
                                mainNavController = mainNavController,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 70.dp)
                            )
                        }
                    }
                    is SavedThumbnailViewState.Error -> {
                        CookingApplication.appContext.showToast(stringResource(R.string.api_error_message))
                    }
                }
            }

        } else {
            if (exploreTitle != null) {
                println(exploreTitle)
                if (exploreTitle.contains(TITLE_INGREDIENT)) {
                    LaunchedEffect(Unit) {
                        viewModel.getFoodByIngredient(
                            exploreTitle.removePrefix(TITLE_INGREDIENT)
                        )
                    }

                } else if (exploreTitle.contains(TITLE_CUISINE)) {

                    LaunchedEffect(Unit) {
                        viewModel.getFoodByCuisine(
                            exploreTitle.removePrefix(TITLE_CUISINE)
                        )
                    }

                } else {

                    LaunchedEffect(Unit) {
                        viewModel.foodByCategoryExplore(title)
                    }

                }
            }
            viewModel.exploreViewState.collectAsState().value.let { exploreViewState ->


                when (exploreViewState) {
                    is ExploreViewState.EmptyState -> {
                        //no op
                    }
                    is ExploreViewState.Loading -> {
                        ShowLoader()
                    }
                    is ExploreViewState.Success -> {
                        val categoryList = exploreViewState.data
                        showExploreList(
                            exploreList = categoryList,
                            mainNavController = mainNavController,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 70.dp)
                        )
                    }
                    is ExploreViewState.Error -> {
                        CookingApplication.appContext.showToast(stringResource(R.string.api_error_message))
                    }
                }
            }

            viewModel.ingredientViewState.collectAsState().value.let { ingredientViewState ->

                when (ingredientViewState) {
                    is IngredientViewState.EmptyState -> {
                        //no op
                    }
                    is IngredientViewState.Loading -> {
                        ShowLoader()
                    }
                    is IngredientViewState.Success -> {
                        val ingredientsList = ingredientViewState.data
                        showExploreList(
                            exploreList = ingredientsList,
                            mainNavController = mainNavController,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 70.dp)
                        )
                    }
                    is IngredientViewState.Error -> {
                        CookingApplication.appContext.showToast(stringResource(R.string.api_error_message))
                    }
                }
            }

            viewModel.exploreCuisineViewState.collectAsState().value.let { cuisineViewState ->

                when (cuisineViewState) {
                    is ExploreCuisineViewState.EmptyStateExplore -> {
                        //no op
                    }
                    is ExploreCuisineViewState.Loading -> {
                        ShowLoader()
                    }
                    is ExploreCuisineViewState.Success -> {
                        val cuisineList = cuisineViewState.data
                        showExploreList(
                            exploreList = cuisineList,
                            mainNavController = mainNavController,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 70.dp)
                        )
                    }
                    is ExploreCuisineViewState.Error -> {
                        CookingApplication.appContext.showToast(stringResource(R.string.api_error_message))
                    }
                }
            }
        }
    }
}

@Composable
fun showExploreList(exploreList: List<FoodCategory?>, mainNavController: NavHostController, modifier: Modifier) {
    LazyColumn(
        modifier = modifier
    ) {
        items(exploreList.chunked(2)) { rowItems ->
            Row(horizontalArrangement = SpaceEvenly) {
                rowItems.forEach { foodCategory ->

                    Column(
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 10.dp
                        )
                    ) {
                        if (foodCategory != null) {
                            foodThumbnail(
                                item = foodCategory,
                                navController = mainNavController
                            )
                        }
                    }
                }

            }
        }
    }
}