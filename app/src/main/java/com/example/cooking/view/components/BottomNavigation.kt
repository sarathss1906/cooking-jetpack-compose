package com.example.cooking.view.components

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cooking.R
import com.example.cooking.Screen

@Composable
fun BottomNavigationComponent(navController: NavHostController) {
    BottomAppBar(tonalElevation = 5.dp) {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry.value?.destination

        val iconHome = painterResource(id = R.drawable.home)
        val iconRandom = painterResource(id = R.drawable.shopping_bag)
        val iconSaved = painterResource(id = R.drawable.bookmark_icon)

        this.NavigationBarItem(selected = currentDestination?.route == Screen.Home.route,
            onClick = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route)
                    launchSingleTop = true
                }
            },
            icon = { Icon(painter = iconHome, contentDescription = null) },
            label = { Text(text = Screen.Home.route) })

        this.NavigationBarItem(selected = currentDestination?.route == Screen.Random.route,
            onClick = {
                navController.navigate(Screen.Random.route) {
                    popUpTo(Screen.Random.route)
                    launchSingleTop = true
                }
            },
            icon = { Icon(painter = iconRandom, contentDescription = null) },
            label = { Text(text = Screen.Random.route) })

        this.NavigationBarItem(selected = currentDestination?.route == Screen.Saved.route,
            onClick = {
                navController.navigate(Screen.Saved.route) {
                    popUpTo(Screen.Saved.route)
                    launchSingleTop = true
                }
            },
            icon = { Icon(painter = iconSaved, contentDescription = null) },
            label = { Text(text = Screen.Saved.route) })
    }
}