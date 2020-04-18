package cn.yfengtech.network

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import cn.yfengtech.libnetwork.HttpClient

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("cachepath:${HttpClient.getCache()}")
    }
}
