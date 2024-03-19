package com.example.cooking.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cooking.R
import com.example.cooking.Screen
import com.example.cooking.ui.theme.Black
import com.example.cooking.ui.theme.Grey2
import com.example.cooking.ui.theme.searchBar
import com.example.cooking.viewmodel.SearchViewModel
import com.example.cooking.viewmodel.SearchVisibleViewState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchTab(
    modifier: Modifier,
    textDisable: Boolean,
    navController: NavHostController,
    viewModel: SearchViewModel?
) {
    var query by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var showClearIcon by rememberSaveable { mutableStateOf(false) }

    if (viewModel != null) {
        if (query.isEmpty()) {
            viewModel.searchVisibleText.value = SearchVisibleViewState.EmptyState
            showClearIcon = false
        } else if (query.isNotEmpty()) {
            LaunchedEffect(Unit) {
                viewModel.getMealSearchText(query)
            }
            viewModel.searchVisibleText.value = SearchVisibleViewState.Success

            showClearIcon = true
        }
    }


    Surface(
        modifier = modifier,
    ) {

        TextField(
            value = query,
            onValueChange = { onQueryChanged ->
                query = onQueryChanged
                if (viewModel != null) {
                    if (onQueryChanged.isNotEmpty()) {
                        viewModel.searchVisibleText.value = SearchVisibleViewState.Success

                        viewModel.getMealSearchText(onQueryChanged)

                    } else {
                        viewModel.searchVisibleText.value = SearchVisibleViewState.EmptyState
                    }
                }
            },
            singleLine = true,
            maxLines = 1,
            readOnly = textDisable,
            placeholder = {
                Text(modifier = Modifier
                    .clickable(
                        enabled = textDisable,
                        onClick = { navController.navigate(Screen.Search.route) }),
                    text = stringResource(R.string.searchbar_hint_text),
                    fontSize = 14.sp,
                    color = Grey2
                )
            },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
            trailingIcon = {
                if (showClearIcon) {
                    IconButton(onClick = { query = "" }) {
                        Icon(
                            imageVector = Icons.Rounded.Clear,
                            tint = Black,
                            contentDescription = "Clear Icon"
                        )
                    }
                }
            },
            modifier = Modifier
                .border(BorderStroke(0.dp, Color.Transparent)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = searchBar,
                unfocusedContainerColor = searchBar,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                // Hide the keyboard after submitting the search
                keyboardController?.hide()
                //or hide keyboard
                focusManager.clearFocus()

            })
        )

    }


}
