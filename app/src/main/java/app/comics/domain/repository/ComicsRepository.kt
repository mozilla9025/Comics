package app.comics.domain.repository

import app.comics.data.model.ComicDataWrapper
import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError

interface ComicsRepository {
    suspend fun getComics(byName: String? = null): Either<CallError, ComicDataWrapper>
}
