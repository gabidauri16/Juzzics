package com.example.juzzics.navigation

import com.google.gson.Gson

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("HomeScreen")
    data object MusicsScreen : Screen("MusicsScreen")
    data object PlaylistsScreen : Screen("PlaylistsScreen")

    fun <T> withArgs(arg: T): String {
        return buildString {
            append(route)
            val string = Gson().toJson(arg)
            append("/$string")
        }
    }

    inline fun <reified T> String.getFromGson(): T? {
        val argument = this.split("/").drop(0)
        return Gson().fromJson(argument.first(), T::class.java)
    }
}
