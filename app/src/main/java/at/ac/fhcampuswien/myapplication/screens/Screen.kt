package at.ac.fhcampuswien.myapplication

sealed class Screen(val route: String)
object HomeScreen:  Screen("home")
object DetailsScreen: Screen("detailsScreen")
object FavoritesScreen: Screen("favorites")

