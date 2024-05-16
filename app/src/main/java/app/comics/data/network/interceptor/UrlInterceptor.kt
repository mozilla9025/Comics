package app.comics.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.security.MessageDigest

class UrlInterceptor(
    private val timeProvider: () -> Long,
    private val publicKey: String,
    private val privateKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()

        val ts = timeProvider().toString()
        val url = oldRequest.url.newBuilder().apply {
            addQueryParameter("ts", ts)
            addQueryParameter("apikey", publicKey)
            addQueryParameter("hash", "$ts$privateKey$publicKey".md5())
        }.build()

        val newRequest = oldRequest.newBuilder().url(url).build()
        return chain.proceed(newRequest)
    }
}

@OptIn(ExperimentalStdlibApi::class)
// TODO: Extract the functionality into a separate class and inject it as a dependency for proper testing
private fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    val digest = md.digest(this.toByteArray())
    return digest.toHexString()
}
