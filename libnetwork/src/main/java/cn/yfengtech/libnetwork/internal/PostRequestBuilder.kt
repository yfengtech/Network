package cn.yfengtech.libnetwork.internal

import cn.yfengtech.libnetwork.common.Priority
import cn.yfengtech.libnetwork.common.Request
import cn.yfengtech.libnetwork.common.RequestBuilder
import okhttp3.FormBody
import okhttp3.Headers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.*
import java.util.concurrent.Executor

class PostRequestBuilder(private val url: String) : RequestBuilder<PostRequestBuilder> {
    private var mPriority: Priority = Priority.MEDIUM
    private var mTag: Any? = null
    private var mExecutor: Executor? = null
    private val mHeadersMap = HashMap<String, String>()
    private val mPostFormBodyMap by lazy { HashMap<String, String>() }
    private var mPostJsonBodyStr: String? = null

    override fun setPriority(priority: Priority): PostRequestBuilder {
        mPriority = priority
        return this
    }

    override fun setTag(tag: Any): PostRequestBuilder {
        mTag = tag
        return this
    }

    override fun addHeader(key: String, value: String): PostRequestBuilder {
        mHeadersMap[key] = value
        return this
    }

    override fun addHeaders(headerMap: Map<String, String>): PostRequestBuilder {
        mHeadersMap.putAll(headerMap)
        return this
    }

    fun addFormParameter(key: String, value: String): PostRequestBuilder {
        mPostFormBodyMap[key] = value
        return this
    }

    fun addFormParameter(queryParameterMap: Map<String, String>): PostRequestBuilder {
        mPostFormBodyMap.putAll(queryParameterMap)
        return this
    }

    fun addJsonBody(jsonStr: String): PostRequestBuilder {
        mPostJsonBodyStr = jsonStr
        return this
    }

    override fun setExecutor(executor: Executor): PostRequestBuilder {
        mExecutor = executor
        return this
    }

    override fun build(): Request {
        val headers = Headers.Builder().apply {
            mHeadersMap.entries.forEach { entry ->
                add(entry.key, entry.value)
            }
        }.build()

        val requestBody = when {
            mPostJsonBodyStr != null -> {
                mPostJsonBodyStr!!.toRequestBody("application/json; charset=utf-8".toMediaType())
            }
            mPostFormBodyMap.size > 0 -> {
                FormBody.Builder().apply {
                    mPostFormBodyMap.entries.forEach { entry ->
                        add(entry.key, entry.value)
                    }
                }.build()
            }
            else -> {
                FormBody.Builder().build()
            }
        }

        val request = okhttp3.Request.Builder()
            .url(url)
            .post(requestBody)
            .headers(headers)
            .tag(mTag)
            .build()
        return Request.create(request)
    }
}