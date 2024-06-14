package com.example.recipesearchapp.models.SearchRecipeModel

data class WinePairing(
    val pairedWines: List<String>,
    val pairingText: String,
    val productMatches: List<ProductMatch>
)
