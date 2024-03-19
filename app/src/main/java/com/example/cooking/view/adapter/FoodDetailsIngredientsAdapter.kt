package com.example.cooking.view.adapter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.cooking.R
import com.example.cooking.ui.theme.Black
import com.example.cooking.ui.theme.Grey
import com.example.cooking.ui.theme.SingleMealBlue
import com.example.cooking.ui.theme.White
import com.example.cooking.view.response.SingleMealDetail

@Composable
fun SingleMealLazyColumn(mealList: SingleMealDetail) {

    LazyColumn {
        item {
            SingleMealDetails(item = mealList)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SingleMealDetails(item: SingleMealDetail) {
    var size = 0
    Column {
        GlideImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp),
            model = item.image, contentDescription = stringResource(R.string.single_meal_detail_image), contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SingleMealBlue)
        ) {
            item.name?.let {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 24.dp, end = 24.dp)
                        .height(40.dp),
                    text = it,
                    fontSize = 18.sp,
                    color = White
                )
            }

            Row(
                modifier = Modifier
                    .padding(start = 20.dp, top = 8.dp, end = 24.dp, bottom = 10.dp)
                    .fillMaxWidth()
                    .height(24.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                item.area?.let {
                    Text(
                        text = it,
                        fontSize = 16.sp,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.width(18.dp))
                item.category?.let {
                    Text(
                        text = it,
                        fontSize = 16.sp,
                        color = White
                    )
                }
            }
        }

        for(ingredients in item.ingredients){
            if(ingredients?.ingredients.isNullOrEmpty().not()){
                size+=1
            }
        }

        Text(
            modifier = Modifier
                .padding(start = 20.dp, end = 24.dp, top = 32.dp, bottom = 4.dp),
            text = stringResource(R.string.ingredient_size,size),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            border = BorderStroke(width = 1.dp, Grey)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            for (ingredients in item.ingredients) {
                if (ingredients?.ingredients.isNullOrEmpty().not()) {

                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            GlideImage(
                                model = ingredients?.image,
                                contentDescription = stringResource(R.string.ingredient_image),
                                modifier = Modifier
                                    .size(36.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                ingredients?.ingredients?.let {
                                    Text(
                                        text = it.trimStart(),
                                        fontSize = 14.sp,
                                        color = Black,
                                        textAlign = TextAlign.Left,
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.displaySmall,
                                        maxLines = 1
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                ingredients?.measurement?.let {
                                    Text(
                                        text = it.trimStart(),
                                        fontSize = 12.sp,
                                        textAlign = TextAlign.Left,
                                        style = MaterialTheme.typography.displaySmall,
                                        maxLines = 1
                                    )
                                }
                            }

                        }
                    }

                }

            }


        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 8.dp),
            text = stringResource(R.string.instructions),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .background(White),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            border = BorderStroke(width = 1.dp, Grey)
        ) {
            item.description?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp),
                    fontSize = 13.sp,
                    lineHeight = 20.sp,
                    style = MaterialTheme.typography.displayMedium,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }

}