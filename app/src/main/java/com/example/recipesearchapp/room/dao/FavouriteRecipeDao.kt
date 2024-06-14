package com.example.recipesearchapp.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.recipesearchapp.room.model.FavouriteRecipe
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteRecipeDao {

    @Upsert
    suspend fun upsertRecipe(recipe: FavouriteRecipe)

    @Delete
    suspend fun deleteRecipe(recipe: FavouriteRecipe)

    @Query("SELECT * FROM `favouriterecipe-table`")
    fun fetchAllFavorRecipes(): Flow<List<FavouriteRecipe>>
}