package app.comics.domain.usecase

import app.comics.data.model.ComicDataWrapper
import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError

interface GetComicsUseCase {
    suspend operator fun invoke(byName: String? = null): Either<CallError, ComicDataWrapper>
}
