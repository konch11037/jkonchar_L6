package com.csci448.konchar.jkonchar_l6.ui.navigation.specs

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.csci448.konchar.jkonchar_l6.R
import com.csci448.konchar.jkonchar_l6.viewmodels.GeoLocatrViewModel


sealed interface I_ScreenSpec {
    companion object {
        val startDestination: String = LocationScreenSpec.route
        val allScreens = I_ScreenSpec::class.sealedSubclasses.associate { it.objectInstance?.route to it.objectInstance }

        @Composable
        fun TopBar(navController: NavHostController, navBackStackEntry: NavBackStackEntry?){
            val route = navBackStackEntry?.destination?.route ?: ""
            if (route != "") allScreens[route]?.TopAppBarContent(navController = navController)
        }
    }

    val title: Int
        get() = R.string.app_name
    val route: String
    val arguments: List<NamedNavArgument>

    @Composable fun Content(viewModel: GeoLocatrViewModel, navController: NavHostController, backStackEntry: NavBackStackEntry)
    @Composable fun TopAppBarActions(navController: NavHostController)

    @Composable
    private fun TopAppBarContent(navController: NavHostController){
        TopAppBar(
            title = {
                navController.currentBackStackEntry?.destination?.route?.let { Text(it) }
            },
            navigationIcon = {
                if (navController.previousBackStackEntry != null) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                } else null
            },
            actions = { TopAppBarActions(navController = navController) }
        )
    }

    fun navigateTo(vararg args: String?): String
}


