package com.example.recipesearchapp.presentation.screens

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.recipesearchapp.data.room.database.FavouriteRecipeDatabase
import com.example.recipesearchapp.data.room.model.FavRecipeEquipments
import com.example.recipesearchapp.data.room.model.FavRecipeIngredients
import com.example.recipesearchapp.data.room.model.FavRecipeInstructions
import com.example.recipesearchapp.data.room.model.FavouriteRecipe
import com.example.recipesearchapp.presentation.components.CircularItemElement
import com.example.recipesearchapp.presentation.components.ExpandableSection
import com.example.recipesearchapp.presentation.components.RecipeInfoCard
import com.example.recipesearchapp.viewmodel.MainViewModel
import com.example.recipesearchapp.viewmodel.RecipeViewModel
import com.example.recipesearchapp.viewmodel.SharedViewModel
import kotlinx.coroutines.flow.first

@Composable
fun FavouriteRecipeDetailedScreen(
    sharedViewModel: SharedViewModel,
    recipeViewModel: RecipeViewModel
) {
    val favRecipe by sharedViewModel.favRecipeState

    val context = LocalContext.current

    var filled by remember { mutableStateOf<Boolean?>(null) }
    var initialLoad by remember { mutableStateOf(true) }
    val favRecipes by recipeViewModel.favouriteRecipesLiveData.observeAsState()

    favRecipe?.let {

        val favIngredients = mutableListOf<FavRecipeIngredients>()
        val favEquipments = mutableListOf<FavRecipeEquipments>()
        val favInstructions = mutableListOf<FavRecipeInstructions>()

        for (i in it.ingredientsName.indices) {
            favIngredients.add(
                FavRecipeIngredients(
                    ingredientsName = it.ingredientsName[i],
                    ingredientsImage = it.ingredientsImage[i]
                )
            )
        }

        for (i in it.equipmentsName.indices) {
            favEquipments.add(
                FavRecipeEquipments(
                    equipmentsName = it.equipmentsName[i],
                    equipmentsImage = it.equipmentsImage[i]
                )
            )
        }

        for (i in it.stepNumber.indices) {
            favInstructions.add(
                FavRecipeInstructions(
                    stepNumber = it.stepNumber[i],
                    stepValue = it.step[i]
                )
            )
        }

        LaunchedEffect(Unit) {
            val favoriteRecipes = favRecipes
            filled = favoriteRecipes?.any { favRecipe -> favRecipe.title == it.title }
            initialLoad = false
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = Color.White)
                .systemBarsPadding()
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
                Row(
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    IconButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/*"
                                putExtra(Intent.EXTRA_TEXT, "Check out this tasty recipe: ${it.sourceUrl}")
                            }
                            try {
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                Log.d("TAG", "RecipeView: $e")
                            }
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .background(Color.White, shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = null,
                            tint = Color.Red
                        )
                    }
                    IconButton(
                        onClick = {
                            if (!initialLoad) {
                                filled?.let {

                                    filled = !filled!!
                                    val message = if (filled == true) {
                                        "Marked as favourite"
                                    } else {
                                        "Removed from favourite"
                                    }
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .background(Color.White, shape = CircleShape)
                    ) {
                        Icon(
                            painter = if (filled == true) painterResource(id = R.drawable.filled_favourite) else painterResource(
                                id = R.drawable.favourite
                            ),
                            contentDescription = null,
                            tint = Color.Red
                        )
                    }
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

                val favouriteRecipe = FavouriteRecipe(
                    id = it.id,
                    title = it.title,
                    image = it.image,
                    readyInMinutes = it.readyInMinutes,
                    servings = it.servings.toString(),
                    pricePerServing = it.pricePerServing.toString(),
                    ingredientsName = it.ingredientsName,
                    ingredientsImage = it.ingredientsImage,
                    instructions = it.instructions,
                    it.stepNumber,
                    it.step,
                    equipmentsName = it.equipmentsName,
                    equipmentsImage = it.equipmentsImage,
                    summary = it.summary,
                    sourceUrl = it.sourceUrl
                )
                //marking favourite recipe
                if (!initialLoad) {
                    LaunchedEffect(filled) {
                        if(filled != null) {
                            if (filled == true) {
                                recipeViewModel.addFavouriteRecipe(favouriteRecipe)
                            } else {
                                recipeViewModel.deleteFavouriteRecipe(favouriteRecipe)
                            }
                        }
                    }
                }

                CommonTitle(title = "Ingredients")

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(favIngredients) {ingredient->
                        CircularItemElement(name = ingredient.ingredientsName, imageUrl = "https://img.spoonacular.com/ingredients_100x100/${ingredient.ingredientsImage}")
                    }
                }

                CommonTitle(title = "Instructions")

                LazyColumn(
                    modifier = Modifier.heightIn(min = 400.dp, max = 700.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    userScrollEnabled = false
                ) {
                    items(favInstructions) {instruction->
                        Row {
                            Text(
                                text = "Step ${instruction.stepNumber}:",
                                color = Color.Gray,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = instruction.stepValue,
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }

                    }
                }

                CommonTitle(title = "Equipments")

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(favEquipments) {equipment->
                        CircularItemElement(name = equipment.equipmentsName, imageUrl = equipment.equipmentsImage)
                    }
                }

                CommonTitle(title = "Quick Summary")

                Text(
                    text = it.summary,
                    color = Color.Gray,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

            }
        }

    }
}