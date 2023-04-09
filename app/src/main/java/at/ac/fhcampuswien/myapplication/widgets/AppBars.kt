package at.ac.fhcampuswien.myapplication.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SimpleAppBar(title: String, navController: NavController){
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            Icon(
                modifier = Modifier
                    .clickable { navController.popBackStack() }
                    .size(30.dp),
                contentDescription = "Back to Home_Screen",
                imageVector = Icons.Default.ArrowBack
            )
        }
    )
}



@Composable
fun HomeAppBar( onDropDownEdit: () -> Unit, onDropDownFavorite: () -> Unit ){
    var expandedMenu by remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text(text = "Movies") },
        actions = {
            Icon(
                modifier = Modifier
                    .clickable { expandedMenu = !expandedMenu }
                    .size(35.dp),
                contentDescription = "More Options",
                imageVector = Icons.Default.MoreVert
            )
            DropdownMenu(
                expanded = expandedMenu,
                onDismissRequest = { expandedMenu = false}) {
                DropdownMenuItem(onClick = {
                    onDropDownEdit()
                }) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Add Movie",
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(text = "Add Movie",
                            modifier = Modifier
                                .width(100.dp)
                                .padding(4.dp)
                        )
                    }
                }
                DropdownMenuItem(onClick = {
                    onDropDownFavorite()
                }) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorites",
                            modifier = Modifier.padding(4.dp),
                        )
                        //Spacer(Modifier.size(10.dp))
                        Text(text = "Favorites",
                            modifier = Modifier
                                .width(100.dp)
                                .padding(4.dp)
                        )
                    }
                }
            }
        }
    )
}