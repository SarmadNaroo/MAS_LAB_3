package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import android.annotation.SuppressLint
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.example.myapplication.ui.theme.MyApplicationTheme
class MainActivity : ComponentActivity() {
    // State variables for each text
    private var onCreateText by mutableStateOf("")
    private var onHomePressedText by mutableStateOf("")
    private var onRestartText by mutableStateOf("")
    private var onRotateText by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the onCreateText when the activity is first created
        onCreateText = "What callbacks are called when an app is first launched?"

        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LifecycleAwareTexts(onCreateText, onHomePressedText, onRestartText, onRotateText)
                }
            }
        }

        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_STOP -> {
                        onHomePressedText = "What callbacks occur when Home is pressed?"
                    }
                    Lifecycle.Event.ON_PAUSE -> {
                        onRestartText = "What callbacks occur when an app is restarted from the launcher?"
                    }
                    Lifecycle.Event.ON_DESTROY -> {
                        onRotateText = "What callbacks occur when the device is rotated?"
                    }
                    else -> {}
                }
            }
        })
    }

    override fun onConfigurationChanged(newConfig: android.content.res.Configuration) {
        super.onConfigurationChanged(newConfig)
        // Update text to indicate a rotation has occurred
        onRotateText = "What callbacks occur when the device is rotated?"
    }
}

@Composable
fun MyApplicationTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        content()
    }
}

@Composable
fun LifecycleAwareTexts(onCreateText: String, onHomePressedText: String, onRestartText: String, onRotateText: String) {
    Column {
        Text("MainActivity")
        if (onCreateText.isNotEmpty()) Text(onCreateText)
        if (onHomePressedText.isNotEmpty()) Text(onHomePressedText)
        if (onRestartText.isNotEmpty()) Text(onRestartText)
        if (onRotateText.isNotEmpty()) Text(onRotateText)
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
        LifecycleAwareTexts("", "", "","")
    }
}