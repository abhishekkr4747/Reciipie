package com.example.recipesearchapp.presentation.screens


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.recipesearchapp.R
import com.example.recipesearchapp.data.remote.model.RandomRecipeModel.Equipment
import com.example.recipesearchapp.data.remote.model.RandomRecipeModel.Ingredient2
import com.example.recipesearchapp.data.remote.model.RandomRecipeModel.Step
import com.example.recipesearchapp.data.room.database.FavouriteRecipeDatabase
import com.example.recipesearchapp.data.room.model.FavouriteRecipe
import com.example.recipesearchapp.viewmodel.MainViewModel
import com.example.recipesearchapp.viewmodel.SharedViewModel
import com.example.recipesearchapp.presentation.components.CircularItemRowEquipments
import com.example.recipesearchapp.presentation.components.CircularItemRowIngredients
import com.example.recipesearchapp.presentation.components.ExpandableSection
import com.example.recipesearchapp.presentation.components.RecipeInfoCard
import com.example.recipesearchapp.presentation.components.SectionTitle
import kotlinx.coroutines.flow.first

@Composable
fun RecipeView(
    sharedViewModel: SharedViewModel
) {
    val viewModel: MainViewModel = viewModel()
    val recipe by sharedViewModel.recipeState

    val context = LocalContext.current

    //creating instance of database
    val db = Room.databaseBuilder(
        context,
        FavouriteRecipeDatabase::class.java,
        "favouriterecipe-database"
    ).build()

    //getting dao from notes database
    val recipeDao = db.dao()

    recipe?.let {

        var filled by remember { mutableStateOf<Boolean?>(null) }
        var initialLoad by remember { mutableStateOf(true) }

        val ingredients = mutableListOf<Ingredient2>()
        val equipments = mutableListOf<Equipment>()
        val ingredientsName = mutableListOf<String>()
        val ingredientsImage = mutableListOf<String>()
        val equipmentsName = mutableListOf<String>()
        val equipmentsImage = mutableListOf<String>()
        val steps = mutableListOf<Step>()
        val stepsNumber = mutableListOf<String>()
        val stepsValue = mutableListOf<String>()


        for (instruction in it.analyzedInstructions) {
            steps.addAll(instruction.steps)
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

        for (step in steps) {
            stepsNumber.addAll(listOf(step.number.toString()))
            stepsValue.addAll(listOf(step.step))
        }


        LaunchedEffect(Unit) {
            val favoriteRecipes = recipeDao.fetchAllFavorRecipes().first()
            filled = favoriteRecipes.any { favRecipe -> favRecipe.title == it.title }
            initialLoad = false
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(it.image)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(360.dp)
                )
                IconButton(
                    onClick = {
                        if (!initialLoad) {
                            filled = !filled!!
                            val message = if (filled == true) {
                                "Marked as favourite"
                            } else {
                                "Removed from favourite"
                            }
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                        }
                              },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color.White, shape = CircleShape)
                ) {
                    Icon(painter = if (filled == true) painterResource(id = R.drawable.filled_favourite) else painterResource(id = R.drawable.favourite),
                        contentDescription = null,
                        tint = Color.Red)
                }
            }


            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Absolute.SpaceEvenly
                ) {
                    RecipeInfoCard( "Ready in", "${it.readyInMinutes} min")
                    RecipeInfoCard("Servings", "${it.servings}")
                    RecipeInfoCard("Price/serving", "${it.pricePerServing}")
                }

                CommonTitle(title = "Ingredients")

                val favouriteRecipe = FavouriteRecipe(
                    id = it.id,
                    title = it.title,
                    image = it.image,
                    readyInMinutes = it.readyInMinutes,
                    servings = it.servings.toString(),
                    pricePerServing = it.pricePerServing.toString(),
                    ingredientsName = ingredientsName,
                    ingredientsImage = ingredientsImage,
                    instructions = it.instructions,
                    stepsNumber,
                    stepsValue,
                    equipmentsName = equipmentsName,
                    equipmentsImage = equipmentsImage,
                    summary = it.summary
                )
                //marking favourite recipe
                if (!initialLoad) {
                    LaunchedEffect(filled) {
                        if(filled != null) {
                            if (filled == true) {
                                viewModel.upsertFavouriteRecipe(recipeDao = recipeDao, recipe = favouriteRecipe)
                            } else {
                                viewModel.deleteFavouriteRecipe(recipeDao = recipeDao, recipe = favouriteRecipe)
                            }
                        }
                    }
                }

                CircularItemRowIngredients(ingredients)

                CommonTitle(title = "Instructions")



                LazyColumn(
                    modifier = Modifier.heightIn(min = 400.dp, max = 700.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    userScrollEnabled = false
                ) {
                    items(steps) {instruction->
                        Row {
                            Text(
                                text = "Step ${instruction.number}:",
                                color = Color.Gray,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = instruction.step,
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }

                    }
                }

                CommonTitle(title = "Equipments")

                CircularItemRowEquipments(equipments)

                CommonTitle(title = "Quick Summary")

                Text(
                    text = it.summary,
                    color = Color.Gray,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                ExpandableSection(title = "Nutrition", expand = true)

                Spacer(modifier = Modifier.height(16.dp))

                ExpandableSection(title = "Bad for health nutrition", expand = false)

                Spacer(modifier = Modifier.height(16.dp))

                ExpandableSection(title = "Good for health nutrition", expand = false)
            }
        }
        
    }

}


@Composable
fun CommonTitle(title: String) {
    Spacer(modifier = Modifier.height(20.dp))

    SectionTitle(title = title)

    Spacer(modifier = Modifier.height(16.dp))
}



