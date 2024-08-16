package com.example.recipesearchapp.presentation.navigation

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.recipesearchapp.presentation.components.bottomNavigationBar.BottomNavigationItems
import com.example.recipesearchapp.presentation.google_sign_in.GoogleAuthUiClient
import com.example.recipesearchapp.presentation.screens.Dashboard
import com.example.recipesearchapp.presentation.screens.FavouriteRecipeDetailedScreen
import com.example.recipesearchapp.presentation.screens.FavouriteRecipeView
import com.example.recipesearchapp.presentation.screens.RecipeView
import com.example.recipesearchapp.presentation.screens.SearchScreen
import com.example.recipesearchapp.presentation.screens.SignInScreen
import com.example.recipesearchapp.viewmodel.MainViewModel
import com.example.recipesearchapp.viewmodel.RecipeViewModel
import com.example.recipesearchapp.viewmodel.SharedViewModel
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

@Composable
fun SetupNavGraph(navController: NavHostController,
                  recipeViewModel: RecipeViewModel,
                  onBottomBarVisibilityChanged: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val sharedViewModel : SharedViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()
    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }

    NavHost(
        navController = navController,
        startDestination = if (googleAuthUiClient.getSignedInUser() != null) BottomNavigationItems.Dashboard.route
        else Screen.SignInScreen.route
    ) {
        composable(route = Screen.SignInScreen.route) {
            val mainViewModel = viewModel<MainViewModel>()
            val state by mainViewModel.state.collectAsState()


            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result: ActivityResult->
                    if (result.resultCode == RESULT_OK) {
                        coroutineScope.launch {
                            val signInResult = googleAuthUiClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            mainViewModel.onSignInResult(signInResult)
                        }
                    }
                }
            )

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    Toast.makeText(
                        context,
                        "Sign in successfull",
                        Toast.LENGTH_SHORT
                    ).show()

                    navController.navigate(BottomNavigationItems.Dashboard.route)
                    mainViewModel.resetState()
                }
            }

            onBottomBarVisibilityChanged(false)
            SignInScreen(
                state = state,
                onSignInClick = {
                    coroutineScope.launch {
                        val signInIntentSender = googleAuthUiClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                }
            )
        }

        composable(route = BottomNavigationItems.Dashboard.route) {
            onBottomBarVisibilityChanged(true)
            Dashboard(
                navController = navController,
                sharedViewModel = sharedViewModel,
                userData = googleAuthUiClient.getSignedInUser(),
                recipeViewModel = recipeViewModel,
                onSignOut = {
                    coroutineScope.launch {
                        googleAuthUiClient.signOut()
                        Toast.makeText(
                            context,
                            "Signed out",
                            Toast.LENGTH_LONG
                        ).show()

                        navController.navigate(Screen.SignInScreen.route)

                    }
                }
            )
        }

        composable(route = BottomNavigationItems.Favourite.route) {
            onBottomBarVisibilityChanged(true)
            FavouriteRecipeView(navController = navController, sharedViewModel = sharedViewModel, recipeViewModel)
        }

        composable(route = Screen.RecipeView.route) {
            onBottomBarVisibilityChanged(false)
            RecipeView(sharedViewModel = sharedViewModel, recipeViewModel)
        }

        composable(route = Screen.FavouriteRecipeDetailedScreen.route) {
            onBottomBarVisibilityChanged(false)
            FavouriteRecipeDetailedScreen(sharedViewModel = sharedViewModel, recipeViewModel)
        }

        composable(route = Screen.SearchScreen.route) {
            onBottomBarVisibilityChanged(false)
            SearchScreen(navController = navController, recipeViewModel)
        }
    }
}