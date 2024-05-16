package app.comics.di

import app.comics.data.repository.ComicsRepositoryImpl
import app.comics.domain.repository.ComicsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryBindingModule {

    @Binds
    fun bindComicsRepository(impl: ComicsRepositoryImpl): ComicsRepository
}
