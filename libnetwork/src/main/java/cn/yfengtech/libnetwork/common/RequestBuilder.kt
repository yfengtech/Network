package cn.yfengtech.libnetwork.common

import java.util.concurrent.Executor


interface RequestBuilder<T : RequestBuilder<T>> {

    fun setPriority(priority: Priority): T

    fun setTag(tag: Any): T

    fun addHeader(key: String, value: String): T

    fun addHeaders(headerMap: Map<String, String>): T

    fun setExecutor(executor: Executor): T

    fun build():Request
}