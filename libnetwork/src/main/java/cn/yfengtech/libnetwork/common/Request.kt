package cn.yfengtech.libnetwork.common

import cn.yfengtech.libnetwork.HttpClient
import cn.yfengtech.libnetwork.ICallback
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException
import java.lang.Exception


/**
 * 包装okhttp request
 */
class Request private constructor(private val realRequest: okhttp3.Request) {

    fun <T> executeAsJson(clazz: Class<T>): T {
        val response = HttpClient.getOkHttp().newCall(realRequest).execute()
        val content = response.body?.string() ?: throw IllegalArgumentException("response is null")
        return HttpClient.GSON.fromJson<T>(content, clazz)
    }

    fun executeAsString(): String {
        val response = HttpClient.getOkHttp().newCall(realRequest).execute()
        return response.body?.string() ?: throw IllegalArgumentException("response is null")
    }

    fun enqueue(callback: ICallback<String>) {
        HttpClient.getOkHttp().newCall(realRequest).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onComplete()
                callback.onFailure(e)
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onComplete()
                callback.onSuccess(response.body!!.string())
            }
        })
    }

    fun <T> enqueueAsJson(clazz: Class<T>, callback: ICallback<T>) {
        HttpClient.getOkHttp().newCall(realRequest).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onComplete()
                callback.onFailure(e)
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val t = HttpClient.GSON.fromJson(response.body!!.string(), clazz)
                    callback.onComplete()
                    callback.onSuccess(t)
                } catch (e: Exception) {
                    callback.onComplete()
                    callback.onFailure(e)
                }
            }
        })
    }

    companion object {
        internal fun create(request: okhttp3.Request): Request {
            return Request(request)
        }
    }
}