package app.comics.di

import app.comics.data.network.service.ComicsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class CloudServiceModule {

    @Provides
    fun provideDeviceService(retrofit: Retrofit): ComicsService {
        return retrofit.create(ComicsService::class.java)
    }
}
