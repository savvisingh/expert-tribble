package com.example.deliveryherotest.api

import com.example.deliveryherotest.repository.api.IDeliveryHeroAPI
import okhttp3.mockwebserver.MockWebServer

class DeliveryHeroAPITest {

    private lateinit var mockedServer: MockWebServer
    private lateinit var apiService: IDeliveryHeroAPI

}