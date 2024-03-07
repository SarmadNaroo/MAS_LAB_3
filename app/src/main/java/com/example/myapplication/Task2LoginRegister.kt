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
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
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
                }
            }
        }
    }
}

@Composable
fun LoginScreen(navController: NavHostController) {
    // Local context for creating intents or Toasts if needed
    val context = LocalContext.current

    // State for Email and Password
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // This variable is to control the visibility of the password
    var passwordVisibility by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    // Handle the 'Done' action, like attempting a login
                }),
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    // Handle login logic here
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("LOGIN")
            }
            Spacer(modifier = Modifier.height(24.dp))
            ClickableText(
                text = AnnotatedString("Not a member? Sign up now."),
                onClick = {
                    // Navigate to the RegisterScreen when the user clicks on "Sign up now."
                    navController.navigate(MainDestinations.REGISTER_ROUTE)
                }
            )
        }
    }
}


@Composable
fun RegisterScreen(navController: NavHostController) {
    // Local context for creating intents or Toasts if needed
    val context = LocalContext.current

    // State for Email and Password
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var fullname by remember { mutableStateOf("")}

    // This variable is to control the visibility of the password
    var passwordVisibility by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = fullname,
                onValueChange = { fullname = it },
                label = { Text("Full Name") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    // Handle the 'Done' action, like attempting a login
                }),
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    // Handle login logic here
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("REGISTER")
            }
            Spacer(modifier = Modifier.height(24.dp))
            ClickableText(
                text = AnnotatedString("Already Registered! Login Me."),
                onClick = {
                    // Navigate back to the LoginScreen when the user clicks on "Login Me."
                    navController.popBackStack()
                }
            )
        }
    }
}

object MainDestinations {
    const val LOGIN_ROUTE = "login"
    const val REGISTER_ROUTE = "register"
}

@Composable
fun MainNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = MainDestinations.LOGIN_ROUTE) {
        composable(MainDestinations.LOGIN_ROUTE) {
            LoginScreen(navController = navController)
        }
        composable(MainDestinations.REGISTER_ROUTE) {
            RegisterScreen(navController = navController)
        }
    }
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
//        Greeting("Android")
////        LoginScreen()
//        RegisterScreen()
        val navController = rememberNavController()
        MainNavHost(navController = navController)
    }
}