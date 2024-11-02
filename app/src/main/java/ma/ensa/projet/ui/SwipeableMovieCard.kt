package ma.ensa.projet.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ma.ensa.projet.data.Item
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun SwipeableMovieCard(
    item: Item,
    onSwipe: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var offsetX by remember { mutableStateOf(0f) }  // Position horizontale du swipe
    val animatedOffsetX by animateDpAsState(targetValue = offsetX.dp)  // Animation du swipe

    Box(
        modifier = modifier
            .fillMaxWidth()
            .offset(x = animatedOffsetX)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        // Si le seuil de glissement est atteint, déclencher le swipe
                        if (offsetX < -100f) {
                            onSwipe()
                        }
                        // Retourner à la position d'origine si le seuil n'est pas atteint
                        offsetX = 0f
                    },
                    onHorizontalDrag = { change, dragAmount ->
                        // Mettre à jour la position de glissement
                        offsetX += dragAmount
                        change.consume()
                    }
                )
            }
    ) {
        MovieCard(item = item) {
            navController.navigate("detail/${item.title}")
        }
    }
}
