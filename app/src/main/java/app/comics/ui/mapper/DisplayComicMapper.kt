package app.comics.ui.mapper

import app.comics.data.cache.ComicsCache
import app.comics.data.model.ComicInfo
import app.comics.ui.model.DisplayComic
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

class DisplayComicMapper @Inject constructor(
    private val comicsCache: ComicsCache
) {

    fun map(comic: ComicInfo): DisplayComic {
        return DisplayComic(
            id = comic.id,
            title = comic.title,
            variantDescription = comic.variantDescription,
            description = comic.description ?: comic.variantDescription,
            modified = comic.modified,
            pageCount = comic.pageCount,
            resourceURI = comic.resourceURI,
            prices = comic.prices.map { DisplayComic.DisplayPrice(it.type, it.price) }
                .toImmutableList(),
            thumbnail = comic.thumbnail?.let {
                "${it.path.replace("http", "https")}.${it.extension}"
            },
            isFavorite = comicsCache.data.value.find { it.id == comic.id }?.isFavorite ?: false
        )
    }
}
