package app.comics.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ComicInfo(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "variantDescription") val variantDescription: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "modified") val modified: String?,
    @Json(name = "pageCount") val pageCount: Int?,
    @Json(name = "resourceURI") val resourceURI: String?,
    @Json(name = "series") val series: Series?,
    @Json(name = "prices") val prices: List<Price>,
    @Json(name = "thumbnail") val thumbnail: Thumbnail?,
) {
    @JsonClass(generateAdapter = true)
    data class Series(
        @Json(name = "resourceURI") val resourceURI: String,
        @Json(name = "name") val name: String,
    )

    @JsonClass(generateAdapter = true)
    data class Price(
        @Json(name = "type") val type: String,
        @Json(name = "price") val price: Float,
    )

    @JsonClass(generateAdapter = true)
    data class Thumbnail(
        @Json(name = "path") val path: String,
        @Json(name = "extension") val extension: String,
    )
}

