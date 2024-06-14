package com.example.recipesearchapp.bottomSheet

import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.recipesearchapp.models.SearchRecipeModel.SearchRecipeApiResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    recipe: SearchRecipeApiResponse,
    onDismiss: () -> Unit
) {
    val navController: NavHostController = rememberNavController()
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = {BottomSheetDefaults.DragHandle()},
        containerColor = Color.White
    ) {
        MainRecipeBottomSheet(recipe)
    }
}