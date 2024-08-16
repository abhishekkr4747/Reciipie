package com.example.recipesearchapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesearchapp.BuildConfig
import com.example.recipesearchapp.data.remote.ApiService
import com.example.recipesearchapp.data.remote.model.AutoCompleteRecipeModel.AutoCompleteRecipeSearchApiResponse
import com.example.recipesearchapp.data.remote.model.RandomRecipeModel.RandomRecipeApiResponse
import com.example.recipesearchapp.data.remote.model.SearchRecipeModel.SearchRecipeApiResponse
import com.example.recipesearchapp.data.room.dao.FavouriteRecipeDao
import com.example.recipesearchapp.data.room.model.FavouriteRecipe
import com.example.recipesearchapp.presentation.google_sign_in.SignInResult
import com.example.recipesearchapp.presentation.google_sign_in.SignInState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel: ViewModel() {
    private val _navigateToRecipeView = MutableLiveData(false)
    val navigateToRecipeView: LiveData<Boolean> = _navigateToRecipeView

    fun setNavigateToRecipeView(value: Boolean) {
        _navigateToRecipeView.value = value
    }

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        ) }
    }

    fun resetState() {
        _state.update { SignInState() }
    }


}