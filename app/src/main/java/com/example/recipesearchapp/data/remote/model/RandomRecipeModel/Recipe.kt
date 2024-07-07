package com.example.recipesearchapp.data.remote.model.RandomRecipeModel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Recipe(
    val vegetarian: Boolean,
    val vegan: Boolean,
    val glutenFree: Boolean,
    val dairyFree: Boolean,
    val veryHealthy: Boolean,
    val cheap: Boolean,
    val veryPopular: Boolean,
    val sustainable: Boolean,
    val lowFodmap: Boolean,
    val weightWatcherSmartPoints: Long,
    val gaps: String?,
    val preparationMinutes: @RawValue Any?,
    val cookingMinutes: @RawValue Any?,
    val aggregateLikes: Long,
    val healthScore: Long,
    val creditsText: String?,
    val license: String?,
    val sourceName: String?,
    val pricePerServing: Double,
    val extendedIngredients: @RawValue List<ExtendedIngredient>,
    val id: Long,
    val title: String,
    val author: String?,
    val readyInMinutes: Long,
    val servings: Long,
    val sourceUrl: String,
    val image: String,
    val imageType: String,
    val nutrition: @RawValue Nutrition,
    val summary: String,
    val cuisines: List<String>,
    val dishTypes: List<String>,
    val diets: List<String>,
    val occasions: List<String>,
    val instructions: String,
    val analyzedInstructions: @RawValue List<AnalyzedInstruction>,
    val originalId: @RawValue Any?,
    val spoonacularScore: Double,
    val spoonacularSourceUrl: String
): Parcelable
