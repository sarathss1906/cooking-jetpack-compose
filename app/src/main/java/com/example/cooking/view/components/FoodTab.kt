package com.example.cooking.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cooking.R
import com.example.cooking.Screen
import com.example.cooking.ui.theme.Offwhite
import com.example.cooking.view.adapter.MyRecyclerView
import com.example.cooking.view.response.FoodCategory

@Composable
fun FoodTab(
    mainNavController: NavHostController,
    myList: List<FoodCategory>,
    heading: String,
    exploreIcon: Int
) {
    Surface(
        color = Offwhite,
        modifier = Modifier
            .fillMaxWidth()
            .height(270.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = CenterVertically
            ) {
                Text(
                    text = heading,
                    modifier = Modifier,
                    fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
                Row {
                    Text(
                        text = stringResource(R.string.explore_more),
                        modifier = Modifier.clickable {
                            mainNavController.navigate(
                                Screen.ExploreMore.header(
                                    heading = heading
                                )
                            )
                        },
                        fontSize = 12.sp,
                        color = Color(0xFFD16D01),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(2.dp))

                    Icon(
                        imageVector = ImageVector.vectorResource(id = exploreIcon),
                        modifier = Modifier
                            .clickable(onClick = {
                                mainNavController.navigate(
                                    Screen.ExploreMore.header(
                                        heading = heading
                                    )
                                )
                            })
                            .size(16.dp),
                        tint = Color(0xFFD16D01),
                        contentDescription = stringResource(R.string.explore_more_icon)
                    )
                }
            }
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Row {
                MyRecyclerView(myList, navController = mainNavController)
            }

        }
    }
}