package com.example.recipesearchapp.widgets

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipesearchapp.R

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun RecipeCard() {
    Box(
        modifier = Modifier
            .size(156.dp)
            .clip(shape = RoundedCornerShape(14.dp))
            .background(Color.Black)
    ) {
        Image(
            painter = painterResource(id = R.drawable.paneer),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.5f)
        )
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = "Shahi Paneer",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.White
            )
            Text(
                text = "Ready in 25 min",
                fontSize = 12.sp,
                color = Color(0xFFE7F0F8),
            )
        }
    }
}

@Composable
fun RecipeRow() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(5) {
            RecipeCard()
        }
    }
}

@Composable
fun AllRecipeCard() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(12.dp)),
        shadowElevation = 8.dp,
        border = BorderStroke(width = 1.dp,
            color = Color(0xFFE7F0F8))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.recipes_image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp)
            )

            Column {
                Text(
                    text = "Recipe name goes here",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.Black
                )

                Text(
                    text = "Ready in 25 minutes",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color(0xFF606F89)
                )
            }
        }
    }
}

@Composable
fun AllRecipeColumn() {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(5) {
            AllRecipeCard()
        }
    }
}

@Composable
fun BottomNavigationBar() {
    NavigationBar(
        containerColor = Color.White
    ) {
        NavigationBarItem(
            icon = {
                Icon(imageVector = ImageVector.vectorResource(R.drawable.home),
                    contentDescription = null
                ) },
            label = { Text("Home") },
            selected = true,
            onClick = { /* Handle navigation */ }
        )
        NavigationBarItem(
            icon = { Icon(
                imageVector = ImageVector.vectorResource(R.drawable.favourite),
                contentDescription = null
            ) },
            label = { Text("Favourite") },
            selected = false,
            onClick = { /* Handle navigation */ }
        )
    }
}

@Composable
fun RecipeInfoCard(
    title: String = "Ready in",
    info: String = "25 min",
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .height(56.dp)
            .width(104.dp)
            .border(
                width = 1.dp,
                color = Color(0xFFE7F0F8),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Text(
            text = title,
            color = Color(0xFF606F89),
            modifier = Modifier.padding(top = 8.dp),
            fontSize = 12.sp
        )
        Text(
            text = info,
            color = Color(0xFFE54900),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CircularItemElement(
    @DrawableRes drawable: Int = R.drawable.ingridient_image,
    text: String = "Paneer",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(86.dp)
                .clip(CircleShape)
        ) {
            Image(
                painter = painterResource(drawable),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            text = text,
            modifier = Modifier.paddingFromBaseline(top = 20.dp),
            fontSize = 12.sp,
        )
    }
}

@Composable
fun CircularItemRow(
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(5) {
            CircularItemElement()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpandableSection(
    title: String = "abc",
    text: String = "Lorem ipsum dolor sit amet consectetur. Sagittis facilisis aliquet aenean lorem ullamcorper et. Risus lectus id sed fermentum in. At porta sed ut lorem volutpat elementum mi sollicitudin. Laoreet tempor nullam velit dui amet mauris sed ac sem",
    expand: Boolean = true
) {
    var expanded by remember { mutableStateOf(expand) }
    Column(
        modifier = Modifier.fillMaxWidth()
            .background(color = Color(0xFFF2F7FD))
            .padding(top = 8.dp, bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SectionTitle(title = title)

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = if (expanded) ImageVector.vectorResource(R.drawable.arrow_drop_up_circle) else ImageVector.vectorResource(R.drawable.arrow_drop_down_circle),
                contentDescription = null,
                modifier = Modifier.clickable { expanded = !expanded }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        if(expanded) {

            Text(
                text = text,
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
    }
}

