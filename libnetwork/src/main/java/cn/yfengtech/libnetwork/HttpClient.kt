package cn.yfengtech.libnetwork

import cn.yfengtech.libnetwork.internal.GetRequestBuilder
import cn.yfengtech.libnetwork.internal.PostRequestBuilder
import com.google.gson.Gson
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object HttpClient {

    internal val GSON by lazy { Gson() }

    private val DEFAULT by lazy {
        OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .callTimeout(10, TimeUnit.SECONDS)
            .build()
    }
    private var mCustomOkHttpClient: OkHttpClient? = null

    internal fun getOkHttp() = mCustomOkHttpClient ?: DEFAULT

    fun setOkHttp(okHttpClient: OkHttpClient) {
        mCustomOkHttpClient = okHttpClient
    }

    fun get(url: String): GetRequestBuilder {
        return GetRequestBuilder(url)
    }

    fun post(url: String): PostRequestBuilder {
        return PostRequestBuilder(url)
    }
}