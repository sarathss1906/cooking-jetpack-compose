package com.example.cooking.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.cooking.R
import com.example.cooking.Screen
import com.example.cooking.view.response.FoodCategory

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun foodThumbnail(
    item: FoodCategory,
    navController: NavHostController
){
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(160.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        GlideImage(
            model = item.strMealThumb,
            contentDescription = stringResource(R.string.food_thumbnail_images),
            modifier = Modifier
                .clip(
                    RoundedCornerShape(16.dp)
                )
                .height(140.dp)
                .clickable(
                    enabled = true
                ) {
                    navController.navigate(
                        Screen.SingleProductView.navigate(
                            idMeal = item.idMeal
                        )
                    )
                }
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .clipToBounds()
                .clickable(
                    enabled = true,
                    onClick = {
                        navController.navigate(
                            Screen.SingleProductView.navigate(
                                idMeal = item.idMeal
                            )
                        )
                    }),
            text = item.strMeal.toString(),
            textAlign = TextAlign.Center,
            lineHeight = 18.sp,
            fontSize = 14.sp
        )
    }
}