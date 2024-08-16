package com.example.recipesearchapp.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.room.Room
import com.example.recipesearchapp.data.room.database.FavouriteRecipeDatabase
import com.example.recipesearchapp.data.room.model.FavouriteRecipe
import com.example.recipesearchapp.presentation.components.AllRecipeCard
import com.example.recipesearchapp.presentation.components.SectionTitle
import com.example.recipesearchapp.presentation.navigation.Screen
import com.example.recipesearchapp.viewmodel.RecipeViewModel
import com.example.recipesearchapp.viewmodel.SharedViewModel


@Composable
fun FavouriteRecipeView(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    recipeViewModel: RecipeViewModel
) {
    val favouriteRecipes by recipeViewModel.favouriteRecipesLiveData.observeAsState()

    LaunchedEffect(Unit) {
        recipeViewModel.getFavouriteRecipes()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color.White)
    ) {
        SectionTitle(title = "Favourite Recipes")

        Spacer(modifier = Modifier.height(20.dp))

        favouriteRecipes?.let {
            if (favouriteRecipes!!.isNotEmpty()) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(favouriteRecipes!!) { recipeItem ->
                        AllRecipeCard(
                            title = recipeItem.title,
                            cookingTime = recipeItem.readyInMinutes.toString(),
                            imageUrl = recipeItem.image
                        ) {
                            val favouriteRecipe = FavouriteRecipe(
                                id = recipeItem.id,
                                title = recipeItem.title,
                                image = recipeItem.image,
                                readyInMinutes = recipeItem.readyInMinutes,
                                servings = recipeItem.servings,
                                pricePerServing = recipeItem.pricePerServing,
                                ingredientsName = recipeItem.ingredientsName,
                                ingredientsImage = recipeItem.ingredientsImage,
                                instructions = recipeItem.instructions,
                                recipeItem.stepNumber,
                                recipeItem.step,
                                equipmentsName = recipeItem.equipmentsName,
                                equipmentsImage = recipeItem.equipmentsImage,
                                summary = recipeItem.summary,
                                sourceUrl = recipeItem.sourceUrl
                            )

                            sharedViewModel.addFavRecipe(newFavRecipe = favouriteRecipe)
                            navController.navigate(Screen.FavouriteRecipeDetailedScreen.route)
                        }
                    }
                }
            } else {
                Text(
                    text = "No Favourite Recipe Added",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}
