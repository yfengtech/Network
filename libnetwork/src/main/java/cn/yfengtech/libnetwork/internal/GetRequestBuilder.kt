package cn.yfengtech.libnetwork.internal

import cn.yfengtech.libnetwork.common.Priority
import cn.yfengtech.libnetwork.common.Request
import cn.yfengtech.libnetwork.common.RequestBuilder
import okhttp3.Headers
import java.util.*
import java.util.concurrent.Executor

class GetRequestBuilder(private val url: String) : RequestBuilder<GetRequestBuilder> {
    private var mPriority: Priority = Priority.MEDIUM
    private var mTag: Any? = null
    private var mExecutor: Executor? = null
    private val mHeadersMap = HashMap<String, String>()
    private val mQueryParameterMap = HashMap<String, String>()

    override fun setPriority(priority: Priority): GetRequestBuilder {
        mPriority = priority
        return this
    }

    override fun setTag(tag: Any): GetRequestBuilder {
        mTag = tag
        return this
    }

    override fun addHeader(key: String, value: String): GetRequestBuilder {
        mHeadersMap[key] = value
        return this
    }

    override fun addHeaders(headerMap: Map<String, String>): GetRequestBuilder {
        mHeadersMap.putAll(headerMap)
        return this
    }

    fun addQueryParameter(key: String, value: String): GetRequestBuilder {
        mQueryParameterMap[key] = value
        return this
    }

    fun addQueryParameter(pair: Pair<String, String>): GetRequestBuilder {
        mQueryParameterMap[pair.first] = pair.second
        return this
    }

    fun addQueryParameters(queryParameterMap: Map<String, String>): GetRequestBuilder {
        mQueryParameterMap.putAll(queryParameterMap)
        return this
    }

    override fun setExecutor(executor: Executor): GetRequestBuilder {
        mExecutor = executor
        return this
    }

    override fun build(): Request {
        val headers = Headers.Builder().apply {
            mHeadersMap.entries.forEach { entry ->
                add(entry.key, entry.value)
            }
        }.build()
        val request = okhttp3.Request.Builder()
            .url(url)
            .get()
            .headers(headers)
            .tag(mTag)
            .build()
        return Request.create(request)
    }
}