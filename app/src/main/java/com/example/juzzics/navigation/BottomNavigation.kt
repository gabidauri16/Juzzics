package com.example.juzzics.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.juzzics.features.home.HomeScreen
import com.example.juzzics.features.musics.ui.MusicVM
import com.example.juzzics.features.musics.ui.MusicsScreen
import com.example.juzzics.features.playlists.PlaylistsScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun BottomNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(Screen.HomeScreen.route) {
            HomeScreen()
        }
        composable(Screen.MusicsScreen.route) {
            val vm: MusicVM = koinViewModel()
            MusicsScreen(
                states = vm.stateList,
                uiEvent = vm.uiEvent,
                onAction = vm::onAction
            )
        }
        composable(Screen.PlaylistsScreen.route) {
            PlaylistsScreen()
        }
    }
}