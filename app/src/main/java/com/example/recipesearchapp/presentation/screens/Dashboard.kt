package com.example.recipesearchapp.presentation.screens



import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.recipesearchapp.R
import com.example.recipesearchapp.data.remote.model.RandomRecipeModel.Recipe
import com.example.recipesearchapp.presentation.components.AllRecipeCard
import com.example.recipesearchapp.presentation.components.ComponentAllRecipe
import com.example.recipesearchapp.presentation.components.ComponentPopularRecipe
import com.example.recipesearchapp.presentation.components.RecipeCard
import com.example.recipesearchapp.presentation.components.SectionTitle
import com.example.recipesearchapp.presentation.components.ShimmerEffect
import com.example.recipesearchapp.presentation.google_sign_in.UserData
import com.example.recipesearchapp.presentation.navigation.Screen
import com.example.recipesearchapp.viewmodel.MainViewModel
import com.example.recipesearchapp.viewmodel.SharedViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    userData: UserData?,
    onSignOut: () -> Unit
) {
    val viewModel: MainViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(userData?.username != null) {
                GreetingSection(userName = userData.username)
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.logout_vector),
                contentDescription = null,
                tint = Color(0xFFEA4335),
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onSignOut() }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        SearchBar(navController)
        Spacer(modifier = Modifier.height(20.dp))
        SectionTitle(title = "Popular Recipes")
        Spacer(modifier = Modifier.height(16.dp))
        RecipeRow(viewModel, sharedViewModel, navController)
        Spacer(modifier = Modifier.height(25.dp))
        SectionTitle(title = "All recipes")
        Spacer(modifier = Modifier.height(16.dp))
        AllRecipeColumn(viewModel, sharedViewModel, navController)
    }
}

@Composable
fun GreetingSection(userName: String = "User") {
    Column {
        Text(
            text = "👋 Hey $userName",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Discover tasty and healthy recipes",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}


@Composable
fun SearchBar(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(12.dp))
            .background(color = Color(0xFFF2F7FD))
            .clickable { navController.navigate(Screen.SearchScreen.route) }
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(8.dp)
                .height(40.dp)
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Search any recipe")
        }
    }
}

@Composable
fun RecipeRow(
    viewModel: MainViewModel,
    sharedViewModel: SharedViewModel,
    navController: NavHostController
    ) {
    val isLoading by viewModel.isPopularRecipeLoading
    val response by viewModel.randomRecipesState

    LaunchedEffect(Unit) {
        viewModel.getRandomRecipe()
    }

    if (isLoading) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(10) {
                ShimmerEffect(modifier = Modifier) {
                    ComponentPopularRecipe()
                }
            }
        }

    } else {
        response?.let {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(it.recipes) { recipeItem ->
                    RecipeCard(
                        title = recipeItem.title,
                        cookingTime = recipeItem.readyInMinutes.toString(),
                        imageUrl = recipeItem.image
                    ) {
                        val recipe = Recipe(
                            vegetarian = recipeItem.vegetarian,
                            vegan = recipeItem.vegan,
                            glutenFree = recipeItem.glutenFree,
                            dairyFree = recipeItem.dairyFree,
                            veryHealthy = recipeItem.veryHealthy,
                            cheap = recipeItem.cheap,
                            veryPopular = recipeItem.veryPopular,
                            sustainable = recipeItem.sustainable,
                            lowFodmap = recipeItem.lowFodmap,
                            weightWatcherSmartPoints = recipeItem.weightWatcherSmartPoints,
                            gaps = recipeItem.gaps,
                            preparationMinutes = recipeItem.preparationMinutes,
                            cookingMinutes = recipeItem.cookingMinutes,
                            aggregateLikes = recipeItem.aggregateLikes,
                            healthScore = recipeItem.healthScore,
                            creditsText = recipeItem.creditsText,
                            license = recipeItem.license,
                            sourceName = recipeItem.sourceName,
                            pricePerServing = recipeItem.pricePerServing,
                            extendedIngredients = recipeItem.extendedIngredients,
                            id = recipeItem.id,
                            title = recipeItem.title,
                            author = recipeItem.author,
                            readyInMinutes = recipeItem.readyInMinutes,
                            servings = recipeItem.servings,
                            sourceUrl = recipeItem.sourceUrl,
                            image = recipeItem.image,
                            imageType = recipeItem.imageType,
                            nutrition = recipeItem.nutrition,
                            summary = recipeItem.summary,
                            cuisines = recipeItem.cuisines,
                            dishTypes = recipeItem.dishTypes,
                            diets = recipeItem.diets,
                            occasions = recipeItem.occasions,
                            instructions = recipeItem.instructions,
                            analyzedInstructions = recipeItem.analyzedInstructions,
                            originalId = recipeItem.originalId,
                            spoonacularScore = recipeItem.spoonacularScore,
                            spoonacularSourceUrl = recipeItem.spoonacularSourceUrl
                        )
                        sharedViewModel.addRecipe(newRecipe = recipe)
                        navController.navigate(Screen.RecipeView.route)
                    }
                }
            }
        }
    }
}

@Composable
fun AllRecipeColumn(
    viewModel: MainViewModel,
    sharedViewModel: SharedViewModel,
    navController: NavHostController
) {
    val isLoading by viewModel.isAllRecipeLoading
    val response by viewModel.allRecipeState

    LaunchedEffect(Unit) {
        viewModel.getAllRecipe()
    }

    if (isLoading) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(10) {
                ShimmerEffect(modifier = Modifier) {
                    ComponentAllRecipe()
                }
            }
        }

    } else {
        response?.let {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(it.recipes) { recipeItem ->
                    AllRecipeCard(
                        title = recipeItem.title,
                        cookingTime = recipeItem.readyInMinutes.toString(),
                        imageUrl = recipeItem.image
                    ) {
                        val recipe = Recipe(
                            vegetarian = recipeItem.vegetarian,
                            vegan = recipeItem.vegan,
                            glutenFree = recipeItem.glutenFree,
                            dairyFree = recipeItem.dairyFree,
                            veryHealthy = recipeItem.veryHealthy,
                            cheap = recipeItem.cheap,
                            veryPopular = recipeItem.veryPopular,
                            sustainable = recipeItem.sustainable,
                            lowFodmap = recipeItem.lowFodmap,
                            weightWatcherSmartPoints = recipeItem.weightWatcherSmartPoints,
                            gaps = recipeItem.gaps,
                            preparationMinutes = recipeItem.preparationMinutes,
                            cookingMinutes = recipeItem.cookingMinutes,
                            aggregateLikes = recipeItem.aggregateLikes,
                            healthScore = recipeItem.healthScore,
                            creditsText = recipeItem.creditsText,
                            license = recipeItem.license,
                            sourceName = recipeItem.sourceName,
                            pricePerServing = recipeItem.pricePerServing,
                            extendedIngredients = recipeItem.extendedIngredients,
                            id = recipeItem.id,
                            title = recipeItem.title,
                            author = recipeItem.author,
                            readyInMinutes = recipeItem.readyInMinutes,
                            servings = recipeItem.servings,
                            sourceUrl = recipeItem.sourceUrl,
                            image = recipeItem.image,
                            imageType = recipeItem.imageType,
                            nutrition = recipeItem.nutrition,
                            summary = recipeItem.summary,
                            cuisines = recipeItem.cuisines,
                            dishTypes = recipeItem.dishTypes,
                            diets = recipeItem.diets,
                            occasions = recipeItem.occasions,
                            instructions = recipeItem.instructions,
                            analyzedInstructions = recipeItem.analyzedInstructions,
                            originalId = recipeItem.originalId,
                            spoonacularScore = recipeItem.spoonacularScore,
                            spoonacularSourceUrl = recipeItem.spoonacularSourceUrl
                        )
                        sharedViewModel.addRecipe(newRecipe = recipe)
                        navController.navigate(Screen.RecipeView.route)
                    }
                }
            }
        }
    }
}




