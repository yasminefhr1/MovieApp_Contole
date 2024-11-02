package ma.ensa.projet.data

data class Item(
    val title: String,
    val description: String,
    val imageUrl: String,
    val backgroundUrl: String,
    val resume: String,
    val videoUrl: String,
    val releaseDate: String,
    val genre: List<String>,
    val rating: Float,
    val director: String,
    val duration: Int,
    val country: String,
    val trailerUrl: String?,
    val isFavorite: Boolean = false,
    val flagUrl: String
)
