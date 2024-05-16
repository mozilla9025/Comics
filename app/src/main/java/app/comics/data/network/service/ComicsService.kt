package app.comics.data.network.service

import app.comics.data.model.ComicDataWrapper
import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import retrofit2.http.GET
import retrofit2.http.Query

interface ComicsService {
    @GET("v1/public/comics")
    suspend fun getComics(@Query("titleStartsWith") byName: String?): Either<CallError, ComicDataWrapper>
}
