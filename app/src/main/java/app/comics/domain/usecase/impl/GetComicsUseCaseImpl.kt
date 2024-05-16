package app.comics.domain.usecase.impl

import app.comics.data.model.ComicDataWrapper
import app.comics.domain.repository.ComicsRepository
import app.comics.domain.usecase.GetComicsUseCase
import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import javax.inject.Inject

class GetComicsUseCaseImpl @Inject constructor(
    private val comicsRepository: ComicsRepository
) : GetComicsUseCase {

    override suspend fun invoke(byName: String?): Either<CallError, ComicDataWrapper> {
        val query = if (byName.isNullOrBlank()) null else byName
        return comicsRepository.getComics(query)
    }
}
