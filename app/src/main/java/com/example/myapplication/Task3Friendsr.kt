package com.example.myapplication

import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import android.os.Bundle
import android.util.Half.toFloat
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting("Android")
//                    LoginScreen()
//                    RegisterScreen()
                    val navController = rememberNavController()
                    MainNavHost(navController = navController)
//                    MainScreen()
//                    DetailScreen()
                }
            }
        }
    }
}


object MainDestinations {
    const val HOME_ROUTE = "home"
    // Updated route to include both userId and imageRes as arguments
    const val DETAILS_ROUTE = "details/{userId}/{imageResId}"
    // Helper function to create a route with arguments
    fun detailsRoute(userId: Int, imageResId: Int) = "details/$userId/$imageResId"
}


@Composable
fun MainNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = MainDestinations.HOME_ROUTE) {
        composable(MainDestinations.HOME_ROUTE) {
            MainScreen(navController = navController)
        }
        composable(
            route = MainDestinations.DETAILS_ROUTE,
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType },
                navArgument("imageResId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: return@composable
            val imageResId = backStackEntry.arguments?.getInt("imageResId") ?: return@composable
            DetailScreen(navController = navController, userId = userId, imageResId = imageResId )
        }
    }
}



@Composable
fun MainScreen(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Text(
            text = "Friends",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth(),
                textAlign = TextAlign.Center
        )
        Text(
            text = "Click on an eligible single user to learn more and see if you are compatible for a date!",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(16.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
        ){
            // Here we use 'item' for individual composables
            item { UserProfile(navController, imageRes = R.drawable.chandler, name = "Chandler", 0) }
            item { UserProfile(navController, imageRes = R.drawable.joey, name = "Joey", 1) }
            item { UserProfile(navController, imageRes = R.drawable.monica, name = "Monica", 2) }
            item { UserProfile(navController, imageRes = R.drawable.phoebe, name = "Phoebe", 3) }
            item { UserProfile(navController, imageRes = R.drawable.ross, name = "Ross", 4) }
            item { UserProfile(navController, imageRes = R.drawable.rachel, name = "Rachel", 5) }

        }
    }
}

@Composable
fun UserProfile(navController: NavHostController,@DrawableRes imageRes: Int, name: String, userId: Int) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(4.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = name,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, MaterialTheme.colorScheme.onSurfaceVariant, RoundedCornerShape(16.dp))
                .clickable {
                    navController.navigate(MainDestinations.detailsRoute(userId, imageRes))
                }
        )
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, userId: Int,imageResId: Int) {
    val friendDetailsArray = LocalContext.current.resources.getStringArray(R.array.friend_details)
    val profileDetails = friendDetailsArray[userId]

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("DetailsActivity") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            item {
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ){
                    RatingBar()
                }// Custom composable or third-party composable for rating.
                Image(
                    painter = painterResource(imageResId),
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .aspectRatio(1f) // Maintain a square aspect ratio
                        .clip(shape = RoundedCornerShape(8.dp))

                )
                Text(
                    text = profileDetails,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}



// Dummy composable for a rating bar
@Composable
fun RatingBar() {
    val rating = 4.0f // Fixed rating value
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.sizeIn(maxWidth = 200.dp, minHeight = 32.dp)
    ) {
        repeat(5) {
            val starColor = if (it < rating) Color.Green else Color.Gray
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = starColor,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    Text(
        text = "Hello $name !",
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
//        Greeting("Android")
////        LoginScreen()
//        RegisterScreen()
        val navController = rememberNavController()
        MainNavHost(navController = navController)
////        MainScreen()
//        DetailScreen()
    }
}