package com.example.recipesearchapp.bottomSheet

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.room.Room
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.recipesearchapp.R
import com.example.recipesearchapp.models.RandomRecipeModel.Equipment
import com.example.recipesearchapp.models.RandomRecipeModel.Ingredient2
import com.example.recipesearchapp.models.SearchRecipeModel.SearchRecipeApiResponse
import com.example.recipesearchapp.room.database.FavouriteRecipeDatabase
import com.example.recipesearchapp.room.model.FavouriteRecipe
import com.example.recipesearchapp.screens.CommonTitle
import com.example.recipesearchapp.viewmodel.MainViewModel
import com.example.recipesearchapp.widgets.AllRecipeCard
import com.example.recipesearchapp.widgets.CircularItemElement
import com.example.recipesearchapp.widgets.RecipeCard
import com.example.recipesearchapp.widgets.RecipeInfoCard
import com.example.recipesearchapp.widgets.SectionTitle
import kotlinx.coroutines.flow.first

@Composable
fun MainRecipeBottomSheet(
    recipe: SearchRecipeApiResponse
) {
    val viewModel: MainViewModel = viewModel()
    var filled by remember { mutableStateOf(false) }
    var initialLoad by remember { mutableStateOf(true) }
    var fullRecipe by remember { mutableStateOf(false) }
    var expandedSection by remember { mutableStateOf(ExpandedSection.INGREDIENTS) }

    val context = LocalContext.current

    //creating instance of database
    val db = Room.databaseBuilder(
        context,
        FavouriteRecipeDatabase::class.java,
        "favouriterecipe-database"
    ).build()

    //getting dao from notes database
    val recipeDao = db.dao()

    LaunchedEffect(Unit) {
        val favoriteRecipes = recipeDao.fetchAllFavorRecipes().first()
        filled = favoriteRecipes.any { favRecipe -> favRecipe.title == recipe.title }
        initialLoad = false
    }

    val ingredients = mutableListOf<Ingredient2>()
    val equipments = mutableListOf<Equipment>()
    val ingredientsName = mutableListOf<String>()
    val ingredientsImage = mutableListOf<String>()
    val equipmentsName = mutableListOf<String>()
    val equipmentsImage = mutableListOf<String>()

    for (instruction in recipe.analyzedInstructions) {
        for (step in instruction.steps) {
            equipments.addAll(step.equipment)
            ingredients.addAll(step.ingredients)
        }
    }

    for (ingredient in ingredients) {
        ingredientsName.addAll(listOf(ingredient.name))
        ingredientsImage.addAll(listOf(ingredient.image))
    }

    for (equipment in equipments) {
        equipmentsName.addAll(listOf(equipment.name))
        equipmentsImage.addAll(listOf(equipment.image))
    }

    val favouriteRecipe = FavouriteRecipe(
        id = recipe.id,
        title = recipe.title,
        image = recipe.image,
        readyInMinutes = recipe.readyInMinutes,
        servings = recipe.servings.toString(),
        pricePerServing = recipe.pricePerServing.toString(),
        ingredientsName = ingredientsName,
        ingredientsImage = ingredientsImage,
        instructions = recipe.instructions,
        equipmentsName = equipmentsName,
        equipmentsImage = equipmentsImage,
        summary = recipe.summary
    )
    //marking favourite recipe
    if (!initialLoad) {
        LaunchedEffect(filled) {
            if (filled) {
                viewModel.upsertFavouriteRecipe(recipeDao = recipeDao, recipe = favouriteRecipe)
                Toast.makeText(context, "Marked as Favourite", Toast.LENGTH_LONG).show()
            } else {
                viewModel.deleteFavouriteRecipe(recipeDao = recipeDao, recipe = favouriteRecipe)
                Toast.makeText(context, "Removed from Favourite", Toast.LENGTH_LONG).show()
            }
        }
    }

    Surface {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = recipe.title,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .weight(1f),
                        fontWeight = FontWeight.Bold
                    )

                    Icon(painter = if (filled) painterResource(id = R.drawable.filled_favourite) else painterResource(id = R.drawable.favourite),
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier
                            .clickable { filled = !filled }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (fullRecipe) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            ExpandableSection(
                                title = "Ingredients",
                                isExpanded = expandedSection == ExpandedSection.INGREDIENTS,
                                onClick = {
                                    expandedSection =
                                        if (expandedSection == ExpandedSection.INGREDIENTS) {
                                            ExpandedSection.FULL_RECIPE
                                        } else {
                                            ExpandedSection.INGREDIENTS
                                        }
                                }
                            ) {
                                Spacer(modifier = Modifier.height(16.dp))
                                IngredientsColumn(recipe)
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            ExpandableSection(
                                title = "Full recipe",
                                isExpanded = expandedSection == ExpandedSection.FULL_RECIPE,
                                onClick = {
                                    expandedSection =
                                        if (expandedSection == ExpandedSection.FULL_RECIPE) {
                                            ExpandedSection.SIMILAR_RECIPE
                                        } else {
                                            ExpandedSection.FULL_RECIPE
                                        }
                                }
                            ) {
                                FullRecipeColumn(recipe)
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            ExpandableSection(
                                title = "Similar recipes",
                                isExpanded = expandedSection == ExpandedSection.SIMILAR_RECIPE,
                                onClick = {
                                    expandedSection =
                                        if (expandedSection == ExpandedSection.SIMILAR_RECIPE) {
                                            ExpandedSection.NONE
                                        } else {
                                            ExpandedSection.SIMILAR_RECIPE
                                        }
                                }
                            ) {
                                Spacer(modifier = Modifier.height(16.dp))
                                SimilarRecipeColumn(recipe)
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = { fullRecipe = !fullRecipe },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEA4335)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .padding(horizontal = 16.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Get recipe overview",
                                color = Color.White,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Outlined.KeyboardArrowRight,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                    }

                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        RecipeOverView(recipe)

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = { fullRecipe = !fullRecipe },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEA4335)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .padding(horizontal = 16.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Get full recipe",
                                color = Color.White,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Outlined.KeyboardArrowRight,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

            }
        }
    }
}

@Composable
private fun SimilarRecipeColumn(recipe: SearchRecipeApiResponse) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .background(color = Color.White)
            .height(350.dp)
    ) {
        items(5) {
            AllRecipeCard(
                title = recipe.title,
                cookingTime = recipe.readyInMinutes.toString(),
                imageUrl = recipe.image
            ) {

            }
        }
    }
}

@Composable
private fun FullRecipeColumn(recipe: SearchRecipeApiResponse) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        CommonTitle(title = "Instructions")

        Text(
            text = recipe.instructions,
            color = Color.Gray,
            fontSize = 16.sp
        )

        CommonTitle(title = "Equipments")

        val equipments = mutableListOf<Equipment>()
        for (instruction in recipe.analyzedInstructions) {
            for (step in instruction.steps) {
                equipments.addAll(step.equipment)
            }
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(equipments) {equipmentItem->
                CircularItemElement(
                    name = equipmentItem.name,
                    imageUrl = equipmentItem.image
                )
            }
        }

        CommonTitle(title = "Quick Summary")

        Text(
            text = recipe.summary,
            color = Color.Gray,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        com.example.recipesearchapp.widgets.ExpandableSection(
            title = "Nutrition",
            expand = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        com.example.recipesearchapp.widgets.ExpandableSection(
            title = "Bad for health nutrition",
            expand = false
        )

        Spacer(modifier = Modifier.height(16.dp))

        com.example.recipesearchapp.widgets.ExpandableSection(
            title = "Good for health nutrition",
            expand = false
        )
    }
}

@Composable
private fun IngredientsColumn(recipe: SearchRecipeApiResponse) {

    val ingredients = mutableListOf<Ingredient2>()
    for (instruction in recipe.analyzedInstructions) {
        for (step in instruction.steps) {
            ingredients.addAll(step.ingredients)
        }
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.height(384.dp)
    ) {
        items(ingredients) {ingredientItem->
            CircularItemElementIngredient(
                name = ingredientItem.name,
                imageUrl = ingredientItem.image
            )
        }
    }
}

@Composable
private fun RecipeOverView(recipe: SearchRecipeApiResponse) {

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(recipe.image)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)
    )

    Spacer(modifier = Modifier.height(10.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly
    ) {
        RecipeInfoCard("Ready in", "${recipe.readyInMinutes} min")
        RecipeInfoCard("Servings", "${recipe.servings}")
        RecipeInfoCard("Price/serving", "${recipe.pricePerServing}")
    }
}

@Composable
fun ExpandableSection(
    title: String,
    isExpanded: Boolean,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
            )
        }
        if(isExpanded) {
            content()
        }
    }
}

@Composable
fun CircularItemElementIngredient(
    name: String,
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(104.dp)
                .clip(CircleShape)
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            text = name,
            modifier = Modifier.paddingFromBaseline(top = 20.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


enum class ExpandedSection {
    INGREDIENTS,
    FULL_RECIPE,
    SIMILAR_RECIPE,
    NONE
}

