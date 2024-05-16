package app.comics

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class ComicsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val builder = ImageLoader.Builder(applicationContext)
            .logger(DebugLogger())
            .okHttpClient {
                OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
                    .build()
            }
        Coil.setImageLoader(builder.build())
    }
}
