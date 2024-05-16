package app.comics.di

import app.comics.domain.usecase.GetComicsUseCase
import app.comics.domain.usecase.impl.GetComicsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseBindingModule {

    @Binds
    fun bindGetComicsUseCase(impl: GetComicsUseCaseImpl): GetComicsUseCase
}
