package com.example.recipesearchapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.recipesearchapp.data.remote.model.SearchRecipeModel.SearchRecipeApiResponse
import com.example.recipesearchapp.presentation.components.SearchRecipeElement
import com.example.recipesearchapp.presentation.components.bottomSheet.BottomSheet
import com.example.recipesearchapp.viewmodel.MainViewModel

@Preview(showBackground = true)
@Composable
fun SearchScreen(
    navController: NavHostController = NavHostController(LocalContext.current)
) {
    val viewModel: MainViewModel = viewModel()
    var searchValue by remember { mutableStateOf("") }
    var showSheet by remember { mutableStateOf(false) }
    var searchRecipe = remember { mutableStateOf<SearchRecipeApiResponse?>(null) }

    val autoCompleteResponse = viewModel.autoCompleteRecipeState
    val searchResponse = viewModel.searchRecipeState


    if (showSheet) {
        searchRecipe.value?.let {
            BottomSheet(it) {
                showSheet = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color.White)
            .systemBarsPadding()
    ) {
        SearchBar(
            value = searchValue,
            onValueChange = {searchValue = it},
            onSearch = {  },
            onBackClick = { navController.popBackStack() },
            onClearClick = { searchValue = "" }
        )

        if (searchValue.isNotEmpty()) {
            LaunchedEffect(searchValue) {
                viewModel.getAutoCompleteRecipe(query = searchValue)
            }
        }

        if (autoCompleteResponse.isNotEmpty()) {
            val recipeIds = autoCompleteResponse.map { it.id.toString() }.joinToString(",")
            LaunchedEffect(recipeIds) {
                viewModel.getSearchRecipe(ids = recipeIds)
            }
        }

        if(searchValue.isNotEmpty()) {
            LazyColumn {
                items(autoCompleteResponse) { recipeItem ->
                    SearchRecipeElement(title = recipeItem.title){
                        val recipe = searchResponse.filter {
                            it.id == recipeItem.id
                        }
                        searchRecipe.value = recipe.first()
                        showSheet = true
                    }
                }
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    onBackClick: () -> Unit,
    onClearClick: () -> Unit
) {
    TextField(
        value = value,
        onValueChange = { newValue->
            onValueChange(newValue)
            onSearch() },
        placeholder = { Text("Search any recipe") },
        leadingIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        trailingIcon = {
            if (value.isNotEmpty()) IconButton(onClick = onClearClick) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = null)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(12.dp)),

        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF2F7FD),
            unfocusedContainerColor = Color(0xFFF2F7FD),
            focusedIndicatorColor = Color(0xFFF2F7FD),
            unfocusedIndicatorColor = Color(0xFFF2F7FD)
        )
    )
}
