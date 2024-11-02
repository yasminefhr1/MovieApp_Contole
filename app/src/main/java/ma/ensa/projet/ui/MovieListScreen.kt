package ma.ensa.projet.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ma.ensa.projet.data.Item
import ma.ensa.projet.data.sampleData
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(navController: NavController, modifier: Modifier = Modifier) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedMovie by remember { mutableStateOf<Item?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val moviesState = remember { mutableStateOf(sampleData) }

    // Filtrer les films selon la recherche
    val filteredMovies = sampleData.filter {
        it.title.contains(searchQuery, ignoreCase = true)
    }

    // Fonction pour mettre à jour la note du film
    fun updateMovieRating(movie: Item, newRating: Float) {
        // Créer une nouvelle liste mise à jour
        val updatedMovies = sampleData.map {
            if (it.title == movie.title) it.copy(rating = newRating) else it
        }
        // Mettre à jour l'état avec la nouvelle liste
        sampleData = updatedMovies
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { CustomDrawerContent(navController = navController) }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Black, Color.DarkGray, Color.Black)
                    )
                )
        ) {
            // Barre de recherche
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF1A1A1A)),
                title = {
                    if (isSearching) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search...", color = Color.Gray) },
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier.fillMaxWidth(),
                        )
                    } else {
                        Text(
                            "Movies",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { isSearching = !isSearching }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = if (isSearching) "Fermer la recherche" else "Ouvrir la recherche",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.toggle()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Ouvrir le tiroir",
                            tint = Color.White
                        )
                    }
                }
            )

            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .fillMaxSize()
            ) {

                items(filteredMovies) { item ->
                        SwipeableMovieCard(
                            item = item,
                            onSwipe = {
                                selectedMovie = item
                                showDialog = true
                            },
                            navController = navController,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }



            // Boîte de dialogue de notation
            if (showDialog && selectedMovie != null) {
                RatingDialog(
                    movie = selectedMovie!!,
                    onDismiss = { showDialog = false },
                    onRatingChange = { newRating ->
                        updateMovieRating(selectedMovie!!, newRating)
                        showDialog = false
                    }
                )
            }
    }
}



// Extension pour basculer l'état du tiroir
private suspend fun DrawerState.toggle() {
    if (isClosed) {
        open()
    } else {
        close()
    }
}


// Composable pour afficher la boîte de dialogue de notation
@Composable
fun RatingDialog(movie: Item, onDismiss: () -> Unit, onRatingChange: (Float) -> Unit) {
    var rating by remember { mutableStateOf(movie.rating) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Noter ${movie.title}", fontWeight = FontWeight.Bold)
        },
        text = {
            Column {
                Text(text = "Attribuez une note à ce film", color = Color.Gray)
                RatingBar(rating = rating, onRatingChange = { newRating ->
                    rating = newRating
                    onRatingChange(newRating)
                })
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Confirmer")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )
}

// Barre de notation interactive
@Composable
fun RatingBar(rating: Float, onRatingChange: (Float) -> Unit) {
    Row(modifier = Modifier.padding(8.dp)) {
        for (i in 1..5) {
            Icon(
                imageVector = if (i <= rating) Icons.Filled.Star else Icons.Filled.StarBorder,
                contentDescription = "Étoile",
                tint = if (i <= rating) Color.Yellow else Color.Gray,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onRatingChange(i.toFloat())
                    }
            )
        }
        Text(
            text = " ($rating)",
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}