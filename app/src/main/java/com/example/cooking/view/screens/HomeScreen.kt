package com.example.cooking.view.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cooking.R
import com.example.cooking.Screen
import com.example.cooking.utils.Utility.isInternetAvailable
import com.example.cooking.view.components.BottomNavigationComponent

@Composable
fun HomeScreen(mainNavController: NavHostController) {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White
    ) {
        Scaffold(
            bottomBar = {
                BottomNavigationComponent(
                    navController = navController
                )
            }
        ) {
            NavHost(
                modifier = Modifier.padding(bottom = 80.dp),
                navController = navController,
                startDestination = Screen.Home.route
            ) {
                composable(Screen.Home.route) {
                    if (isInternetAvailable(LocalContext.current)){
                        Home(mainNavController = mainNavController)
                    } else{
                        NoInternet()
                    }
                }
                composable(Screen.Random.route) {
                    if (isInternetAvailable(LocalContext.current)){
                        singleMealDetails(
                            idMeal = null,
                            disableBackButton = true,
                            navController = mainNavController,
                        )
                    } else{
                        NoInternet()
                    }
                }
                composable(route = Screen.Saved.route) {
                    ExploreMore(
                        title = stringResource(id = R.string.saved),
                        mainNavController = mainNavController
                    )
                }
            }
        }
    }
}