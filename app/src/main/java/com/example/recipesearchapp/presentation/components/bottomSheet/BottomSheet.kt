package com.example.recipesearchapp.presentation.components.bottomSheet

import android.widget.Toast
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.recipesearchapp.data.remote.model.SearchRecipeModel.SearchRecipeApiResponse
import com.example.recipesearchapp.viewmodel.RecipeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    recipe: SearchRecipeApiResponse?,
    recipeViewModel: RecipeViewModel,
    onDismiss: () -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState(
       skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = {BottomSheetDefaults.DragHandle()},
        containerColor = Color.White
    ) {
        if (recipe != null) {
            MainRecipeBottomSheet(
                recipe,
                recipeViewModel,
                onDismissRequest = { onDismiss() }
            )
        } else
        {
            Toast.makeText(LocalContext.current, "Recipe not found", Toast.LENGTH_SHORT).show()
        }
    }
}