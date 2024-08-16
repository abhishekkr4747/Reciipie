package com.example.recipesearchapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipesearchapp.data.repository.RecipeRepository
import javax.inject.Inject

class RecipeViewModelFactory @Inject constructor(private val repository: RecipeRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecipeViewModel(repository) as T
    }
}