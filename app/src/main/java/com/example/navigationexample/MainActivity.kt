package com.example.navigationexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.example.navigationexample.ui.NavigationExampleTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App(){
    val navController = rememberNavController()

    NavHost(navController, startDestination = "page1") {
        composable("page1") { Page1(navController) }
        composable("page2") { Page2(navController) }
        composable("page3/{someId}",
            arguments = listOf(navArgument("someId") { type = NavType.StringType })
        ) {
            Page3(navController, it.arguments?.getString("someId", "0")!!)
        }
        composable("page4?someId={someId}",
            arguments = listOf(navArgument("someId") { defaultValue = "0" })
        ) {
            Page4(navController, it.arguments?.getString("someId"))
        }
    }
}

@Composable
fun Page1(navController: NavController){
    ShowContent("page 1", color = Color.Blue, onClick = {
        navController.navigate(route = "page2")
    })
}

@Composable
fun Page2(navController: NavController){
    ShowContent("page 2", color = Color.Green, onClick = {
        navController.navigate(route = "page3/90000")
    })
}

@Composable
fun Page3(navController: NavController, someId: String){
    ShowContent("page 3", color = Color.Red, argumentValue = someId, onClick = {
        navController.navigate(route = "page4?someId=mijagi")
    })
}

@Composable
fun Page4(navController: NavController, optionalId: String?){
    ShowContent("page 4", color = Color.Yellow, argumentValue = optionalId, onClick = {
        navController.popBackStack()
    })
}

@Composable
fun ShowContent(text: String, color: Color = Color.Red, onClick: () -> Unit = {}, argumentValue: String? = null){
    Column(
        modifier = Modifier.fillMaxSize(1f).background(color = color),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(text, modifier = Modifier.padding(10.dp).clickable(onClick = { onClick() }))
        if (argumentValue != null){
            Text("Argument value: $argumentValue")
        }
    }
}
