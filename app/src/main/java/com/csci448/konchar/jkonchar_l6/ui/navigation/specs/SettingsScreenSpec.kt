package com.csci448.konchar.jkonchar_l6.ui.navigation.specs

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.csci448.konchar.jkonchar_l6.ui.screens.SettingsScreen
import com.csci448.konchar.jkonchar_l6.viewmodels.I_GeoLocatrViewModel

object SettingsScreenSpec: I_ScreenSpec {
    override val route: String
        get() = "SettingsScreenSped"
    override val arguments: List<NamedNavArgument>
        get() = TODO("Not yet implemented")

    @Composable
    override fun Content(
        viewModel: I_GeoLocatrViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry,
        snackBarHostState: SnackbarHostState
    ) {


       SettingsScreen(viewModel)
    }

    @Composable
    override fun TopAppBarActions(navController: NavHostController) {
        TODO("Not yet implemented")
    }

    override fun navigateTo(vararg args: String?): String {
       return route
    }

}