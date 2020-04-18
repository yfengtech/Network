package cn.yfengtech.libnetwork

import java.io.IOException
import java.lang.Exception

interface ICallback<T> {
    fun onSuccess(value: T)
    fun onFailure(exception: Exception)
    fun onComplete() {}
}