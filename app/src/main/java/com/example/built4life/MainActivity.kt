package com.example.built4life

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.built4life.destinations.AddProgramRoute
import com.example.built4life.destinations.HomeRoute
import com.example.built4life.ui.addprogram.AddProgramPage
import com.example.built4life.ui.home.HomePage
import com.example.built4life.ui.theme.Built4LifeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Built4LifeApp()
        }
    }
}

@Composable
fun Built4LifeApp(modifier: Modifier = Modifier) {
    Built4LifeTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController, startDestination = HomeRoute
        ) {
            composable<HomeRoute> {
                HomePage(
                    navigateToAddProgram = { navController.navigate(AddProgramRoute) },
                    modifier = modifier
                )
            }

            composable<AddProgramRoute> {
                AddProgramPage(
                    navigateUp = { navController.navigateUp() },
                    modifier = modifier
                )
            }
        }
    }
}