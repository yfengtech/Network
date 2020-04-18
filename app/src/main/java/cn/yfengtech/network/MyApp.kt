package cn.yfengtech.network

import android.app.Application
import cn.yfengtech.libnetwork.HttpClient

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        HttpClient.init(this)
    }
}