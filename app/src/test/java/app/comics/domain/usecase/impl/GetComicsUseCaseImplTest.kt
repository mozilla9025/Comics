package app.comics.domain.usecase.impl

import app.comics.domain.getComicDataWrapper
import app.comics.domain.repository.ComicsRepository
import arrow.core.left
import arrow.core.right
import arrow.retrofit.adapter.either.networkhandling.HttpError
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetComicsUseCaseImplTest {

    private val comicsRepository: ComicsRepository = mockk(relaxed = true)
    private val getComicsUseCase = GetComicsUseCaseImpl(comicsRepository)

    @Test
    fun `getComicsUseCase should return data when succeed`() = runTest {
        // Given
        val expected = getComicDataWrapper()
        coEvery { comicsRepository.getComics(any()) } returns expected.right()
        // When

        val actual = getComicsUseCase("query")

        // Then
        assert(actual.isRight())
        assert(actual.getOrNull() != null)
        assert(actual.leftOrNull() == null)
        assert(expected == actual.getOrNull())
        coVerify(exactly = 1) { comicsRepository.getComics("query") }
    }

    @Test
    fun `getComicsUseCase should return error when failed`() = runTest {
        // Given
        val expected = HttpError(400, "", "")
        coEvery { comicsRepository.getComics(any()) } returns expected.left()
        // When

        val actual = getComicsUseCase("query")

        // Then
        assert(actual.isLeft())
        assert(actual.getOrNull() == null)
        assert(actual.leftOrNull() != null)
        assert(expected == actual.leftOrNull())
        coVerify(exactly = 1) { comicsRepository.getComics("query") }
    }
}
