package com.example.cooking.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cooking.CookingApplication
import com.example.cooking.R
import com.example.cooking.ui.theme.Grey
import com.example.cooking.utils.showToast
import com.example.cooking.view.components.FoodTab
import com.example.cooking.view.components.SearchTab
import com.example.cooking.view.components.ShowLoader
import com.example.cooking.view.response.FoodCategory
import com.example.cooking.viewmodel.HomeViewModel
import com.example.cooking.viewmodel.HomeViewState

@Composable
fun Home(mainNavController: NavHostController) {
    val viewModel: HomeViewModel = hiltViewModel()
    val scrollState = rememberScrollState()
    val modifier = Modifier.verticalScroll(scrollState)


    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = Color.White
    ) {
        Column(modifier = modifier) {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchTab(
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(53.dp)
                        .padding(start = 12.dp, end = 12.dp)
                        .background(Color.Transparent)
                        .clip(
                            RoundedCornerShape(8.dp)
                        ),
                    textDisable = true,
                    navController = mainNavController, viewModel = null
                )

                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.user_profile_icon),
                    modifier = Modifier
                        .clickable(onClick = { /*TODO*/ })
                        .size(36.dp),
                    tint = Color(0xFF6F6F6F),
                    contentDescription = stringResource(R.string.profile_icon)
                )
                Spacer(modifier = Modifier.width(12.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = 0.5.dp,
                color = Grey
            )
            Column {
                for (i in 0..3) {
                    if (i == 0) {
                        LaunchedEffect(Unit) {
                            viewModel.foodByCategoryBreakfast(CookingApplication.getString(R.string.breakfast))
                        }
                    }
                    if (i == 1) {
                        LaunchedEffect(Unit) {
                            viewModel.foodByCategoryStarter(
                                CookingApplication.getString(
                                    R.string.starter
                                )
                            )
                        }
                    }
                    if (i == 2) {
                        LaunchedEffect(Unit) {
                            viewModel.foodByCategoryVegan(
                                CookingApplication.getString(
                                    R.string.vegan
                                )
                            )
                        }
                    }
                    if (i == 3) {
                        LaunchedEffect(Unit) {
                            viewModel.foodByCategoryMiscellaneous(
                                CookingApplication.getString(
                                    R.string.miscellaneous
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                viewModel.homeViewState.collectAsState().value.let { homeViewState ->
                    when (homeViewState) {
                        is HomeViewState.EmptyState -> {
                            //no op
                        }
                        is HomeViewState.Loading -> {
                            ShowLoader()
                        }
                        is HomeViewState.Success -> {
                            viewModel.breakfastViewState.value.let { foodState ->
                                when (foodState) {
                                    is HomeViewState.Success -> {
                                        FoodTabState(
                                            list = foodState.data,
                                            mainNavController = mainNavController,
                                            heading = foodState.heading
                                        )
                                    }
                                    else -> {
                                        //no op
                                    }
                                }
                            }
                            viewModel.starterViewState.value.let { foodState ->
                                when (foodState) {
                                    is HomeViewState.Success -> {
                                        FoodTabState(
                                            list = foodState.data,
                                            mainNavController = mainNavController,
                                            heading = foodState.heading
                                        )
                                    }
                                    else -> {
                                        //no op
                                    }
                                }
                            }
                            viewModel.veganViewState.value.let { foodState ->
                                when (foodState) {
                                    is HomeViewState.Success -> {
                                        FoodTabState(
                                            list = foodState.data,
                                            mainNavController = mainNavController,
                                            heading = foodState.heading
                                        )
                                    }
                                    else -> {
                                        //no op
                                    }
                                }
                            }
                            viewModel.miscellaneousViewState.value.let { foodState ->
                                when (foodState) {
                                    is HomeViewState.Success -> {
                                        FoodTabState(
                                            list = foodState.data,
                                            mainNavController = mainNavController,
                                            heading = foodState.heading
                                        )
                                    }
                                    else -> {
                                        //no op
                                    }
                                }
                            }
                        }
                        is HomeViewState.Error -> {
                            CookingApplication.appContext.showToast(stringResource(R.string.api_error_message))
                        }
                    }
                }

            }
        }

    }
}

@Composable
fun FoodTabState(
    list: List<FoodCategory>,
    mainNavController: NavHostController,
    heading: String

) {

    FoodTab(
        mainNavController = mainNavController,
        myList = list,
        heading = heading,
        exploreIcon = R.drawable.arrow_circle_right
    )
}