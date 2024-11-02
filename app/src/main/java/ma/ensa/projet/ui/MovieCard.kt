package ma.ensa.projet.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import ma.ensa.projet.data.Item

@Composable
fun MovieCard(item: Item, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0x7E121212), Color(0xFF0C0C29)) // Dégradé pour un fond plus dynamique
                    )
                )
                .padding(12.dp)
        ) {
            Row {
                // Image du film avec arrondi et ombre
                val painter = rememberImagePainter(item.imageUrl)
                Image(
                    painter = painter,
                    contentDescription = item.title,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(end = 8.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))

                // Colonne pour le texte
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = item.title,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            shadow = Shadow(
                                color = Color.Black,
                                offset = Offset(4f, 4f),
                                blurRadius = 8f
                            )
                        ),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        maxLines = 3
                    )

                    // Rating bar
                    Spacer(modifier = Modifier.height(8.dp))
                    RatingBar(rating = item.rating)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

