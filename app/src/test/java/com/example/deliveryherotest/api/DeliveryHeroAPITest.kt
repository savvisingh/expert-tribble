package com.example.deliveryherotest.api

import com.example.deliveryherotest.repository.api.IDeliveryHeroAPI
import com.example.deliveryherotest.repository.api.model.RestaurantsResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.reactivex.subscribers.TestSubscriber
import junit.framework.Assert.assertEquals
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.TimeUnit

class DeliveryHeroAPITest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: IDeliveryHeroAPI
    protected lateinit var okHttpClient: OkHttpClient

    companion object{
        private val FILE_RESTAURANTS = "api-response/all-restaurant-response.json"
    }


    @Before
    fun createServer(){
        mockWebServer = MockWebServer()

        okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .build()
        apiService = Retrofit.Builder()
            .addConverterFactory(
                Json.nonstrict
                    .asConverterFactory(("application/json").toMediaType()))
            .baseUrl(mockWebServer.url("/"))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build().create(IDeliveryHeroAPI::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }


    @Test
    fun getAllRestaurants(){
        val mockedResponse = MockResponse().setBody(getResponseString(FILE_RESTAURANTS))
        mockedResponse.setResponseCode(200)
        mockWebServer.enqueue(mockedResponse)

        val testSubscriber : TestSubscriber<Response<RestaurantsResponse>> = TestSubscriber()
        apiService.getRestaurants().subscribe(testSubscriber)

        testSubscriber.assertNoErrors()
        val response = testSubscriber.values()[0].body()
        assertEquals(response?.items?.size, 4)
    }


    fun getResponseString(fileName: String?): String {
        try {
            val stream: InputStream? = javaClass.classLoader?.getResourceAsStream(fileName)
            val source = stream?.source()?.buffer()
            return source?.readUtf8() ?: ""
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }

}