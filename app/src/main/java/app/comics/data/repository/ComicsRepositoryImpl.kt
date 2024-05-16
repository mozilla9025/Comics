package app.comics.data.repository

import app.comics.data.model.ComicDataWrapper
import app.comics.data.network.service.ComicsService
import app.comics.domain.repository.ComicsRepository
import app.comics.util.CoroutineDispatcherProvider
import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ComicsRepositoryImpl @Inject constructor(
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val comicsService: ComicsService
) : ComicsRepository {

    override suspend fun getComics(byName: String?): Either<CallError, ComicDataWrapper> {
        return withContext(coroutineDispatcherProvider.io) {
            comicsService.getComics(byName)
        }
    }
}
