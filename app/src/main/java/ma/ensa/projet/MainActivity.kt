package ma.ensa.projet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ma.ensa.projet.data.sampleData
import ma.ensa.projet.ui.CustomDrawerContent
import ma.ensa.projet.ui.FavoriteMoviesScreen
import ma.ensa.projet.ui.MovieDetailScreen
import ma.ensa.projet.ui.MovieListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Crée un NavController
            val navController = rememberNavController()

            // Crée le DrawerState pour contrôler le tiroir
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val coroutineScope = rememberCoroutineScope()

            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    CustomDrawerContent(navController = navController)
                }
            ) {
                NavHost(navController = navController, startDestination = "movie_list") {
                    composable("movie_list") {
                        MovieListScreen(navController = navController)
                    }
                    composable("detail/{title}") { backStackEntry ->
                        val title = backStackEntry.arguments?.getString("title")
                        val item = sampleData.first { it.title == title }
                        MovieDetailScreen(
                            item = item,
                            onBackPressed = { navController.popBackStack() },
                            onToggleFavorite = { newFavoriteStatus ->
                                // Mettez à jour l'état du film dans sampleData
                                val updatedSampleData = sampleData.map {
                                    if (it.title == title) it.copy(isFavorite = newFavoriteStatus) else it
                                }
                                sampleData = updatedSampleData // Si vous utilisez un mutableListOf ou similaire
                            }
                        )
                    }

                    composable("favorites") {
                        FavoriteMoviesScreen(navController = navController)
                    }
                }
            }

        }
    }
}