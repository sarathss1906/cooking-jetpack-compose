package com.example.cooking.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.cooking.ui.theme.Grey
import com.example.cooking.ui.theme.White
import com.example.cooking.utils.Utility.getIngredientsImage
import com.example.cooking.view.response.MealsIngredients

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun IngredientSearch(
    ingredientsList:MealsIngredients?,
    navController:NavHostController
){
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(modifier = Modifier
            .size(80.dp)
            .clickable(enabled = true) {
                navController.navigate(
                    Screen.SearchByIngredient.header(
                        heading = ingredientsList?.strIngredient
                    )
                )
            },
            border = BorderStroke(1.dp, Grey),
            shape = CircleShape,
            colors = CardDefaults.cardColors(White)
        ) {
            Box(
            modifier = Modifier
                .clipToBounds()
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ){
            GlideImage(
                model = getIngredientsImage(ingredientsList?.strIngredient),
                contentDescription = stringResource(id = R.string.ingredient_image),
                modifier = Modifier
                    .size(40.dp),
                alignment = Alignment.Center
            )
        }

        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )
        Text(
            modifier = Modifier
                .clipToBounds()
                .clickable(
                    enabled = true,
                    onClick = {
                        navController.navigate(
                            Screen.SearchByIngredient.header(
                                heading = ingredientsList?.strIngredient
                            )
                        )
                    }),
            text = ingredientsList?.strIngredient.toString(),
            textAlign = TextAlign.Center,
            lineHeight = 18.sp,
            fontSize = 14.sp
        )
    }
}