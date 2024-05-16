package app.comics.ui.model

import kotlinx.collections.immutable.ImmutableList

data class DisplayComic(
    val id: Int,
    val title: String,
    val variantDescription: String?,
    val description: String?,
    val modified: String?,
    val pageCount: Int?,
    val resourceURI: String?,
    val prices: ImmutableList<DisplayPrice>,
    val thumbnail: String?,
    val isFavorite: Boolean
) {
    data class DisplayPrice(
        val type: String,
        val price: Float,
    )
}
