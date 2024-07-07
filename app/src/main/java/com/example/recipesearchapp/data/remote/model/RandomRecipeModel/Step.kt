package com.example.recipesearchapp.data.remote.model.RandomRecipeModel

data class Step(
    val number: Long,
    val step: String,
    val ingredients: List<Ingredient2>,
    val equipment: List<Equipment>,
    val length: Length?
)
