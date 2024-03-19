package com.example.cooking

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cooking.ui.theme.CookingTheme
import com.example.cooking.utils.AppConstants.AppConstants.EXPLORE_MORE_ROUTE
import com.example.cooking.utils.AppConstants.AppConstants.HOME
import com.example.cooking.utils.AppConstants.AppConstants.MAIN
import com.example.cooking.utils.AppConstants.AppConstants.RANDOM
import com.example.cooking.utils.AppConstants.AppConstants.SAVED
import com.example.cooking.utils.AppConstants.AppConstants.SEARCH
import com.example.cooking.utils.AppConstants.AppConstants.SEARCH_BY_CUISINE_ROUTE
import com.example.cooking.utils.AppConstants.AppConstants.SEARCH_BY_INGREDIENT_ROUTE
import com.example.cooking.utils.AppConstants.AppConstants.SINGLE_MEAL_ROUTE
import com.example.cooking.view.*
import com.example.cooking.view.screens.SearchScreen
import com.example.cooking.view.screens.ExploreMore
import com.example.cooking.view.screens.HomeScreen
import com.example.cooking.view.screens.singleMealDetails
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navControllerParent = rememberNavController()

            CookingTheme(darkTheme = false) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.White
                ) {
                    Scaffold {

                        NavHost(
                            navController = navControllerParent,
                            startDestination = Screen.Main.route
                        ) {
                            composable(Screen.Main.route) {
                                HomeScreen(navControllerParent)
                            }
                            composable(Screen.Search.route){
                                SearchScreen(navController = navControllerParent)
                            }
                            composable(route = Screen.SearchByIngredient.route,
                                arguments = listOf(
                                    navArgument(getString(R.string.heading)) { type = NavType.StringType }
                                )) { navBackStackEntry ->
                                val exploreMoreHeader = navBackStackEntry.arguments?.getString(CookingApplication.getString(R.string.heading))
                                ExploreMore(
                                    title = "Ingredient $exploreMoreHeader",
                                    mainNavController = navControllerParent
                                )
                            }
                            composable(route = Screen.SearchByCuisine.route,
                                arguments = listOf(
                                    navArgument(getString(R.string.heading)) { type = NavType.StringType }
                                )) { navBackStackEntry ->
                                val exploreMoreHeader = navBackStackEntry.arguments?.getString(CookingApplication.getString(R.string.heading))
                                ExploreMore(
                                    title = "Cuisine $exploreMoreHeader",
                                    mainNavController = navControllerParent
                                )
                            }
                            composable(route = Screen.SingleProductView.route,
                                arguments = listOf(
                                    navArgument(getString(R.string.meal_id)) { type = NavType.StringType }
                                )
                            ) { navBackStackEntry ->
                                val idMeal = navBackStackEntry.arguments?.getString(CookingApplication.getString(R.string.meal_id))
                                singleMealDetails(
                                    idMeal = idMeal ?: "",
                                    navController = navControllerParent,
                                    disableBackButton = false
                                )
                            }

                            composable(route = Screen.ExploreMore.route,
                                arguments = listOf(
                                    navArgument(getString(R.string.heading)) { type = NavType.StringType }
                                )) { navBackStackEntry ->
                                val exploreMoreHeader = navBackStackEntry.arguments?.getString(CookingApplication.getString(R.string.heading))
                                ExploreMore(
                                    title = exploreMoreHeader,
                                    mainNavController = navControllerParent
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}

sealed class Screen(val route: String) {
    object Main : Screen(MAIN)
    object Search : Screen(SEARCH)
    object Home : Screen(HOME)
    object Random : Screen(RANDOM)
    object Saved : Screen(route = SAVED)
    object ExploreMore : Screen(route = EXPLORE_MORE_ROUTE){
        fun header(
            heading:String?
        ): String{
            return "Explore_More/${heading}"
        }
    }
    object SearchByIngredient : Screen(route = SEARCH_BY_INGREDIENT_ROUTE){
        fun header(
            heading:String?
        ): String{
            return "Explore_More/Ingredient/${heading}"
        }
    }
    object SearchByCuisine : Screen(route = SEARCH_BY_CUISINE_ROUTE){
        fun header(
            heading:String?
        ): String{
            return "Explore_More/Cuisine/${heading}"
        }
    }
    object SingleProductView :
        Screen(route = SINGLE_MEAL_ROUTE) {
        fun navigate(
            idMeal: String?
        ): String {
            return "single_product_view/${idMeal}"
        }
    }
}