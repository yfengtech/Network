package cn.yfengtech.libnetwork

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun init() {
        mockWebServer = MockWebServer()

        val str = """
           {
               "child":{
                    "name":"jack"
               }
           } 
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(str))
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testNetwork() {
        val url = "http://${mockWebServer.hostName}:${mockWebServer.port}"
        println(url)
        val s = HttpClient.get(url)
            .build()
            .enqueue(object : ICallback<String> {
                override fun onSuccess(value: String) {
                    println(value)
                }

                override fun onFailure(exception: Exception) {
                    println(exception)
                }
            })
        Thread.sleep(500)
    }
}

