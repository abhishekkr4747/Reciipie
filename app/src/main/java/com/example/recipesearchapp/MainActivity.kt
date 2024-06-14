package com.example.recipesearchapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.recipesearchapp.bottomNavigationBar.BottomBar
import com.example.recipesearchapp.navigation.SetupNavGraph
import com.example.recipesearchapp.screens.Dashboard
import com.example.recipesearchapp.ui.theme.RecipeSearchAppTheme
import com.example.recipesearchapp.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
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
        val navController: NavHostController = rememberNavController()
        var buttonsVisible by remember { mutableStateOf(true) }

        Scaffold(
            bottomBar = {
                if (buttonsVisible) {
                    BottomBar(
                        navController = navController,
                        state = buttonsVisible,
                        modifier = Modifier
                    )
                }
            }) { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                SetupNavGraph(navController = navController) {
                        isVisible ->
                    buttonsVisible = isVisible
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
   MyApp()
}



