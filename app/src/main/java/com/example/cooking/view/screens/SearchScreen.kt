package com.example.cooking.view.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cooking.R
import com.example.cooking.Screen
import com.example.cooking.ui.theme.*
import com.example.cooking.view.components.IngredientSearch
import com.example.cooking.view.components.SearchTab
import com.example.cooking.view.components.ShowLoader
import com.example.cooking.view.response.FoodCategory
import com.example.cooking.view.response.MealsCategory
import com.example.cooking.view.response.MealsCuisine
import com.example.cooking.viewmodel.CategoryViewState
import com.example.cooking.viewmodel.CuisinesViewState
import com.example.cooking.viewmodel.IngredientsViewState
import com.example.cooking.viewmodel.SearchViewModel
import com.example.cooking.viewmodel.SearchViewState
import com.example.cooking.viewmodel.SearchVisibleViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController
) {
    val viewModel: SearchViewModel = hiltViewModel()
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(

                title = {
                    Text(
                        text = stringResource(R.string.search),
                        color = White,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Green
                )
            )
        }
    ) {


        Column(
            modifier = Modifier
                .padding(top = 65.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))
            SearchTab(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(53.dp)
                    .padding(start = 12.dp, end = 12.dp)
                    .background(Color.Transparent)
                    .clip(
                        RoundedCornerShape(8.dp)
                    ), textDisable = false, navController = navController, viewModel = viewModel
            )



            viewModel.searchVisibleText.collectAsState().value.let { searchVisibleViewState ->
                when(searchVisibleViewState){
                    is SearchVisibleViewState.Success ->{

                        Column(modifier =Modifier.fillMaxSize()) {
                            viewModel.searchViewState.collectAsState().value.let { searchViewState ->

                                when (searchViewState) {

                                    is SearchViewState.EmptyState -> {
                                        //no op
                                    }

                                    is SearchViewState.Loading -> {
                                        ShowLoader()
                                    }
                                    is SearchViewState.Success -> {
                                        val searchList = searchViewState.data

                                        val foodList = searchList?.map {
                                            FoodCategory(
                                                strMealThumb = it.strMealThumb,
                                                strMeal = it.strMeal,
                                                idMeal = it.idMeal
                                            )
                                        }
                                        if (foodList != null) {
                                            showExploreList(
                                                exploreList = foodList,
                                                mainNavController = navController,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(top = 20.dp)
                                            )
                                        }

                                    }
                                    else -> {
                                        //no op
                                    }
                                }

                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    is SearchVisibleViewState.EmptyState -> {

                        LaunchedEffect(Unit){
                            viewModel.getMealIngredients()
                            viewModel.getMealCategory()
                            viewModel.getMealCuisine()
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        Column(modifier = Modifier
                            .verticalScroll(scrollState)
                            .wrapContentSize()) {

                            viewModel.ingredientsViewState.collectAsState().value.let { ingredientViewState ->

                                when (ingredientViewState) {

                                    is IngredientsViewState.EmptyState -> {
                                        //no op
                                    }

                                    is IngredientsViewState.Loading -> {
                                        ShowLoader()
                                    }

                                    is IngredientsViewState.Success -> {

                                        Divider(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            thickness = 1.dp,
                                            color = Grey
                                        )

                                        val ingredientsList = ingredientViewState.data

                                        Text(
                                            text = stringResource(id = R.string.ingredient),
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            modifier = Modifier.padding(
                                                start = 12.dp,
                                                top = 14.dp,
                                                bottom = 10.dp
                                            )
                                        )

                                        LazyRow(
                                            modifier = Modifier
                                                .height(160.dp)
                                                .padding(
                                                    start = 8.dp,
                                                    end = 10.dp,
                                                    top = 8.dp,
                                                    bottom = 12.dp
                                                )
                                        ) {
                                            items(ingredientsList) {
                                                IngredientSearch(it, navController)
                                            }
                                        }
                                    }
                                    is IngredientsViewState.Error -> {
                                        //no op
                                    }
                                }

                            }

                            viewModel.categoryViewState.collectAsState().value.let { categoryViewState ->

                                when (categoryViewState) {

                                    is CategoryViewState.EmptyState -> {
                                        //no op
                                    }

                                    is CategoryViewState.Loading -> {
                                        ShowLoader()
                                    }

                                    is CategoryViewState.Success -> {

                                        Divider(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            thickness = 1.dp,
                                            color = Grey
                                        )

                                        val categoryList = categoryViewState.data

                                        searchTextCategory(
                                            categoryList = categoryList,
                                            navController = navController
                                        )
                                    }
                                    is CategoryViewState.Error -> {
                                        //no op
                                    }
                                }

                            }

                            viewModel.cuisineViewState.collectAsState().value.let { cuisineViewState ->

                                when (cuisineViewState) {

                                    is CuisinesViewState.EmptyState -> {
                                        //no op
                                    }

                                    is CuisinesViewState.Loading -> {
                                        ShowLoader()
                                    }

                                    is CuisinesViewState.Success -> {

                                        Divider(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            thickness = 1.dp,
                                            color = Grey
                                        )

                                        val cuisineList = cuisineViewState.data

                                        searchTextCuisine(
                                            cuisineList = cuisineList,
                                            navController = navController
                                        )


                                    }
                                    is CuisinesViewState.Error -> {
                                        //no op
                                    }
                                }

                            }
                        }
                    }
                    else -> {
                        ShowLoader()
                    }
                }
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = 0.5.dp,
                color = Grey
            )
        }

    }
}


@Composable
fun SearchTextContainer(search: String?, navController: NavHostController, isCuisine: Boolean) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()
            .clickable(onClick = if (isCuisine) {
                { navController.navigate(Screen.SearchByCuisine.header(search)) }
            } else {
                { navController.navigate(Screen.ExploreMore.header(search)) }
            }),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(SearchTextColour)
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 8.dp),
            text = search ?: "",
            fontSize = 13.sp,
            maxLines = 1
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun searchTextCategory(categoryList: List<MealsCategory>, navController: NavHostController) {
    Text(
        text = stringResource(id = R.string.category),
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(start = 12.dp, top = 14.dp, bottom = 10.dp)
    )


    FlowRow(
        modifier = Modifier
            .padding(horizontal = 14.dp, vertical = 14.dp)
            .wrapContentHeight()
            .wrapContentWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top)
    ) {
        for (category in categoryList)
            SearchTextContainer(
                search = category.strCategory,
                navController = navController,
                isCuisine = false
            )
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun searchTextCuisine(cuisineList: List<MealsCuisine>, navController: NavHostController) {
    Text(
        text = stringResource(id = R.string.cuisine),
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(start = 12.dp, top = 14.dp, bottom = 10.dp)
    )


    FlowRow(
        modifier = Modifier
            .padding(horizontal = 14.dp, vertical = 14.dp)
            .wrapContentHeight()
            .wrapContentWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top)
    ) {
        for (cuisine in cuisineList)
            SearchTextContainer(
                search = cuisine.strArea,
                navController = navController,
                isCuisine = true
            )
    }
    Spacer(modifier = Modifier.height(16.dp))
}
