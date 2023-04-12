package at.ac.fhcampuswien.myapplication.screens

sealed class Screen(val route: String)
object HomeScreen:  Screen("home")
object DetailsScreen: Screen("detailsScreen")
object FavoritesScreen: Screen("favorites")

