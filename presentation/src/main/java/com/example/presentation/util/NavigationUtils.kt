package com.example.presentation.util

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.PopUpToBuilder
import com.example.presentation.navigation.Destination
import com.example.presentation.navigation.MainNav
import com.example.presentation.navigation.NavigationRouteName

object NavigationUtils {
    fun navigate(
        navHostController: NavHostController,
        routeName: String,
        backStackRouteName: String? = null,
        isLaunchSingleTop: Boolean = true,
        needToRestoreState: Boolean = true
    ) {
        navHostController.navigate(routeName) {
            if (backStackRouteName != null) {
                popUpTo(backStackRouteName) { saveState = true }
            }
            launchSingleTop = isLaunchSingleTop
            restoreState = needToRestoreState
        }
    }

    fun findDestination(route: String?) : Destination {
        return when(route) {
            NavigationRouteName.MAIN_HOME -> MainNav.Home
            NavigationRouteName.MAIN_MENU2 -> MainNav.Menu2
            NavigationRouteName.MAIN_MENU3 -> MainNav.Menu3
            NavigationRouteName.MAIN_MENU4 -> MainNav.Menu4
            else -> MainNav.Home
        }
    }
}