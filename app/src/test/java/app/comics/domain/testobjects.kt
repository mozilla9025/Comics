package app.comics.domain

import app.comics.data.model.ComicData
import app.comics.data.model.ComicDataWrapper
import app.comics.data.model.ComicInfo

fun getComicDataWrapper(
    code: Int = 200,
    status: String = "ok",
    data: ComicData = getComicData()
) = ComicDataWrapper(
    code = code,
    status = status,
    data = data,
)

fun getComicData(
    offset: Int = 0,
    limit: Int = 0,
    total: Int = 0,
    count: Int = 0,
    results: List<ComicInfo> = emptyList(),
) = ComicData(
    offset = offset,
    limit = limit,
    total = total,
    count = count,
    results = results,
)
