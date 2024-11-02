package ma.ensa.projet.ui

import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberImagePainter
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import ma.ensa.projet.R
import ma.ensa.projet.data.Item

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    item: Item,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onToggleFavorite: (Boolean) -> Unit
) {

    val isFavorite = remember { mutableStateOf(item.isFavorite) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Black, Color.Black, Color.Gray, Color.Black, Color.Black)
                )
            )
    ) {
        // Top AppBar
        TopAppBar(
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF1A1A1A)),
            title = {
                Text(
                    "Details",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackPressed) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
            },
            actions = {
                IconButton(
                    onClick = {
                        isFavorite.value = !isFavorite.value
                        onToggleFavorite(isFavorite.value)
                    }
                ) {
                    Icon(
                        imageVector = if (isFavorite.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = if (isFavorite.value) "Unfavorite" else "Favorite",
                        tint = Color.Red
                    )
                }
            }
        )

        // Scrollable content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Background image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(Brush.verticalGradient(listOf(Color.Black, Color.Transparent)))
            ) {
                Image(
                    painter = rememberImagePainter(item.backgroundUrl),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Movie details card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(13.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.DarkGray)

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    // Movie image
                    Image(
                        painter = rememberImagePainter(item.imageUrl),
                        contentDescription = item.title,
                        modifier = Modifier
                            .size(140.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .padding(end = 10.dp)
                    )

                    // Title and description
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(end = 10.dp)
                    ) {
                        Text(
                            text = item.title,
                            style = TextStyle(
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                shadow = Shadow(
                                    color = Color.Black,
                                    offset = Offset(4f, 4f),
                                    blurRadius = 8f
                                )
                            ),
                            modifier = Modifier
                                .padding(bottom = 4.dp)
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(Color.Cyan, Color.Magenta)
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = item.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.LightGray,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                // Rating bar
                Spacer(modifier = Modifier.height(5.dp))
                RatingBar(rating = item.rating)
                Spacer(modifier = Modifier.height(5.dp))

                // Overview section
                Text(
                    text = "Overview",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFFB0B0B0),
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = item.resume,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))

                // Additional information section
                AdditionalInfoSection(item)
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Country:",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFFB0B0B0),
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = "${item.country}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                    // Flag image
                    Image(
                        painter = rememberImagePainter(
                            data = item.flagUrl,
                            builder = {
                                placeholder(R.drawable.ic_placeholder)
                            }
                        ),
                        contentDescription = "Flag of ${item.country}",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Transparent)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // YouTube trailer section
            Text(
                text = "Trailer",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFFB0B0B0),
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )

            // YouTube video
            VideoPlayer(videoUrl = item.trailerUrl ?: item.videoUrl)

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun AdditionalInfoSection(item: Item) {
    Column(modifier = Modifier.padding(8.dp)) {
        InfoRow(label = "Release Date:", value = item.releaseDate)
        InfoRow(label = "Genre:", value = item.genre.joinToString(", "))
        InfoRow(label = "Director:", value = item.director)
        InfoRow(label = "Duration:", value = "${item.duration} minutes")
    }
}

@Composable
fun InfoRow(label: String, value: String, showFlag: Boolean = false) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFFB0B0B0),
            modifier = Modifier.padding(end = 4.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

// Composable to display the rating bar
@Composable
fun RatingBar(rating: Float) {
    Row(modifier = Modifier.padding(8.dp)) {
        for (i in 1..5) {
            Icon(
                imageVector = if (i <= rating) Icons.Filled.Star else Icons.Filled.StarBorder,
                contentDescription = "Star",
                tint = if (i <= rating) Color.Yellow else Color.Gray,
                modifier = Modifier.size(24.dp)
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

// Composable to display the video
@Composable
fun VideoPlayer(videoUrl: String) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .padding(12.dp)
            .background(Color.Black, RoundedCornerShape(12.dp)),
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.cacheMode = WebSettings.LOAD_NO_CACHE
                webViewClient = WebViewClient()
                loadUrl(videoUrl)
            }
        }
    )
}
