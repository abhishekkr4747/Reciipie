package com.example.recipesearchapp.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipesearchapp.data.room.converter.Converters
import com.example.recipesearchapp.data.room.dao.FavouriteRecipeDao
import com.example.recipesearchapp.data.room.model.FavouriteRecipe


@Database(
    entities = [FavouriteRecipe::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class FavouriteRecipeDatabase: RoomDatabase() {
    abstract fun dao(): FavouriteRecipeDao
}