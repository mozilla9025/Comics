package app.comics.di

import app.comics.util.CoroutineDispatcherProvider
import app.comics.util.DefaultCoroutineDispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CoroutineDispatcherBindingModule {

    @Binds
    fun bindDefaultCoroutineDispatcherProvider(
        impl: DefaultCoroutineDispatcherProvider
    ): CoroutineDispatcherProvider
}
