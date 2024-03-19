package com.example.cooking.view.adapter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cooking.view.components.foodThumbnail
import com.example.cooking.view.response.FoodCategory

@Composable
fun MyRecyclerView(dataList: List<FoodCategory>, navController: NavHostController) {
    LazyRow {
        items(dataList) { item ->
            MyRecyclerViewItem(item = item, navController = navController)
        }
    }
}

@Composable
fun MyRecyclerViewItem(item: FoodCategory, navController: NavHostController) {
    Row(
        modifier = Modifier
            .width(140.dp)
            .height(250.dp)
            .padding(3.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        foodThumbnail(item = item, navController = navController)
    }
}