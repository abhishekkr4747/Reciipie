package com.example.recipesearchapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipesearchapp.api.ApiService
import com.example.recipesearchapp.api.RetrofitInstance
import com.example.recipesearchapp.models.RandomRecipeApiResponse
import com.example.recipesearchapp.screens.SignInScreen
import com.example.recipesearchapp.ui.theme.RecipeSearchAppTheme
import com.example.recipesearchapp.widgets.BottomNavigationBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    RecipeSearchAppTheme {
        SignInScreen()
        val retrofit = Retrofit.Builder()
            .baseUrl(RetrofitInstance.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val call = apiService.getRandomRecipe(
            apiKey = "1fb613da06e142b39477505f8347765c",
            number = "10"
        )

        call.enqueue(object : Callback<RandomRecipeApiResponse>{
            override fun onResponse(
                call: Call<RandomRecipeApiResponse>,
                response: Response<RandomRecipeApiResponse>
            ) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()

                    println(apiResponse)
                    Log.d("TAG", "onResponse: $apiResponse")
                } else {
                    println(response.message())
                    Log.d("TAG", "onResponse: $response.message()")
                }
            }

            override fun onFailure(call: Call<RandomRecipeApiResponse>, t: Throwable) {
                println(t.message)
                Log.d("TAG", "onResponse: ${t.message}")
            }

        })
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
   MyApp()
}



