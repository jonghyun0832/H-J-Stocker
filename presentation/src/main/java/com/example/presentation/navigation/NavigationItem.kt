package com.example.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.SsidChart
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.navDeepLink

sealed class MainNav(
    override val route: String,
    override val title: String,
    override val deepLinks: List<NavDeepLink> = listOf(navDeepLink {
        uriPattern = "${NavigationRouteName.DEEP_LINK_SCHEME}$route"
    }),
    val icon: ImageVector
) : Destination {
    object Home : MainNav(
        route = NavigationRouteName.MAIN_HOME,
        title = NavigationTitle.MAIN_HOME,
        icon = Icons.Filled.Home
    )

    object Menu2 : MainNav(
        route = NavigationRouteName.MAIN_MENU2,
        title = NavigationTitle.MAIN_MENU2,
        icon = Icons.Filled.AttachMoney
    )

    object Menu3 : MainNav(
        route = NavigationRouteName.MAIN_MENU3,
        title = NavigationTitle.MAIN_MENU3,
        icon = Icons.Filled.SsidChart
    )

    object Menu4 : MainNav(
        route = NavigationRouteName.MAIN_MENU4,
        title = NavigationTitle.MAIN_MENU4,
        icon = Icons.Filled.Newspaper
    )

    companion object {
        fun isMainRoute(route: String?): Boolean {
            return when (route) {
                NavigationRouteName.MAIN_HOME, NavigationRouteName.MAIN_MENU2, NavigationRouteName.MAIN_MENU3, NavigationRouteName.MAIN_MENU4 -> true
                else -> false
            }
        }
    }
}

//object ChartEnrollNav: DestinationArg<ChartModel?> {
//    override val route: String = NavigationRouteName.CHART_ENROLL
//    override val title: String = NavigationTitle.CHART_ENROLL
//    override val deepLinks: List<NavDeepLink> = listOf(
//        navDeepLink { uriPattern = "${NavigationRouteName.DEEP_LINK_SCHEME}$route" }
//    )
//    override val argName: String = "chart"
//    override val arguments: List<NamedNavArgument> = listOf(navArgument(argName) {
//        type = NavType.StringType
//        nullable = true
//        defaultValue = null
//    })
//
//    override fun navigateWithArg(item: ChartModel?): String {
//        return if (item == null) {
//            route
//        } else {
//            val arg = GsonUtils.toJson(item)
//            "$route/$arg"
//        }
//    }
//
//    override fun findArgument(navBackStackEntry: NavBackStackEntry): ChartModel? {
//        val chartString = navBackStackEntry.arguments?.getString(argName)
//        return GsonUtils.fromJson<ChartModel>(chartString)
//    }
//}

interface Destination {
    val route: String
    val title: String
    val deepLinks: List<NavDeepLink>
}

interface DestinationArg<T> : Destination {
    val argName: String
    val arguments: List<NamedNavArgument>

    fun routeWithArgName() = "$route/{$argName}"
    fun navigateWithArg(item: T): String
    fun findArgument(navBackStackEntry: NavBackStackEntry): T?
}