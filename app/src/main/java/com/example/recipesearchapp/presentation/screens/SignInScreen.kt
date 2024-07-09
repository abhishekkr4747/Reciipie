package com.example.recipesearchapp.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipesearchapp.R
import com.example.recipesearchapp.presentation.google_sign_in.SignInState

@Composable
fun SignInScreen(
    state: SignInState,
    onSignInClick: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }
        Image(
            painter = painterResource(id = R.drawable.login_background_image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)

        ) {
            Content(onSignInClick)
        }

}

@Composable
private fun Content(onSignInClick: () -> Unit) {
    Text(
        text = "Welcome to",
        color = Color.White,
        fontSize = 48.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic
    )
    Text(
        text = "Reciipie",
        color = Color.White,
        fontSize = 48.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "Please sign in to continue",
        color = Color.White,
        fontSize = 16.sp
    )
    Spacer(modifier = Modifier.height(20.dp))
    Button(
        onClick = { onSignInClick() },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEA4335)),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_google_logo),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Continue with Google", color = Color.White)
    }
    Spacer(modifier = Modifier.height(20.dp))
}

