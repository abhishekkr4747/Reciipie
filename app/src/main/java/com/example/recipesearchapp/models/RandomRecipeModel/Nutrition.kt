package com.example.recipesearchapp.models.RandomRecipeModel

data class Nutrition(
    val nutrients: List<Nutrient>,
    val properties: List<Property>,
    val flavonoids: List<Flavonoid>,
    val ingredients: List<Ingredient>,
    val caloricBreakdown: CaloricBreakdown,
    val weightPerServing: WeightPerServing
)
