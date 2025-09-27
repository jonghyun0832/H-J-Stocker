package com.example.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.presentation.navigation.MainNav
import com.example.presentation.ui.home.HomeScreen
import com.example.presentation.util.NavigationUtils
import com.example.presentation.viewmodel.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel<MainViewModel>()
) {
    val navHostController = rememberNavController()
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { MainTopBar(navHostController = navHostController, currentRoute = currentRoute) },
        bottomBar = {
            if (MainNav.isMainRoute(currentRoute)) {
                MainNavigationBar(navHostController, currentRoute)
            }

        }
    ) { innerPadding ->
        MainNavigationScreen(
            mainViewModel = viewModel,
            navHostController = navHostController,
            innerPaddings = innerPadding
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(navHostController: NavHostController, currentRoute: String?) {
    if (!MainNav.isMainRoute(currentRoute)) {
        TopAppBar(
            title = {
                Text(
                    text = NavigationUtils.findDestination(currentRoute).title,
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = { navHostController.popBackStack() }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back Icon"
                    )
                }
            }
        )
    } else {
        null
    }
}

// TODO : 토큰 받는거, 토큰 취소 동작 체크

@Composable
fun MainNavigationBar(navHostController: NavHostController, currentRoute: String?) {
    val navigationItems = listOf(
        MainNav.Home,
        MainNav.Menu2,
        MainNav.Menu3,
        MainNav.Menu4
    )

    NavigationBar {
        navigationItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    NavigationUtils.navigate(
                        navHostController = navHostController,
                        routeName = item.route,
                        backStackRouteName = navHostController.graph.startDestinationRoute
                    )
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(text = item.title)
                }
            )
        }
    }
}

@Composable
fun MainNavigationScreen(
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    innerPaddings: PaddingValues
) {
    NavHost(
        navController = navHostController,
        startDestination = MainNav.Home.route,
        modifier = Modifier.padding(innerPaddings)
    ) {
        composable(
            route = MainNav.Home.route
        ) {
            HomeScreen()
        }

        composable(
            route = MainNav.Menu2.route
        ) {
            // TODO : Tab Menu 2
        }

        composable(
            route = MainNav.Menu3.route
        ) {
            // TODO : Tab Menu 3
        }

        composable(
            route = MainNav.Menu4.route
        ) {
            // TODO : Tab Menu 4
        }
    }
}