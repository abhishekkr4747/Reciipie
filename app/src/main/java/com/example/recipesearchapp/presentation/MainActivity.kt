package com.example.recipesearchapp.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.recipesearchapp.presentation.components.bottomNavigationBar.BottomBar
import com.example.recipesearchapp.presentation.navigation.SetupNavGraph
import com.example.recipesearchapp.presentation.theme.RecipeSearchAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    RecipeSearchAppTheme {
        val navController: NavHostController = rememberNavController()
        var buttonsVisible by remember { mutableStateOf(false) }

        Scaffold(
            bottomBar = {
                if (buttonsVisible) {
                    BottomBar(
                        navController = navController,
                        state = buttonsVisible,
                        modifier = Modifier
                    )
                }
            }
            ) { paddingValues ->
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



