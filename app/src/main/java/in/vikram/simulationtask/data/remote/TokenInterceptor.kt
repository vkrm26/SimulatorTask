package `in`.vikram.simulationtask.data.remote

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

class TokenInterceptor : Interceptor {

    override fun intercept(chain: Chain): Response {
        var original = chain.request()
        val url = original.url().newBuilder().addQueryParameter("api_key", "062a6c0c49e4de1d78497d13a7dbb360").build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }
}