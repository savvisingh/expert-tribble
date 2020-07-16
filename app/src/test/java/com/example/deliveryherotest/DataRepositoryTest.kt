package com.example.deliveryherotest

import android.content.SharedPreferences
import com.example.deliveryherotest.repository.DataRepository
import com.example.deliveryherotest.repository.IDataRepository
import com.example.deliveryherotest.repository.api.IDeliveryHeroAPI
import com.example.deliveryherotest.repository.api.helper.ITransformer
import com.example.deliveryherotest.repository.api.helper.Resource
import com.example.deliveryherotest.repository.api.model.Restaurant
import com.example.deliveryherotest.repository.api.transformer.EntityToRestaurantTransformer
import com.example.deliveryherotest.repository.api.transformer.RestaurantToEntityTransformer
import com.example.deliveryherotest.repository.config.IConfigManager
import com.example.deliveryherotest.repository.db.InAppDataBase
import com.example.deliveryherotest.repository.db.entitiy.RestaurantEntity
import com.example.deliveryherotest.utils.MockedSchedulers
import com.example.deliveryherotest.utils.network.IConnectionUtil
import com.example.deliveryherotest.utils.scheduler.ISchedulers
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.subscribers.TestSubscriber
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DataRepositoryTest {

    lateinit var dataRepository: IDataRepository

    @Mock
    lateinit var deliveryAPI: IDeliveryHeroAPI

    @Mock
    lateinit var appDataBase: InAppDataBase

    var schedulersProvider = MockedSchedulers()

    @Mock
    lateinit var sharedPreferences: SharedPreferences

    @Mock
    lateinit var configManager: IConfigManager

    @Mock
    lateinit var connectionUtil: IConnectionUtil

    var restaurantToEntityTransformer = RestaurantToEntityTransformer()

    var entityToRestaurantTransformerTransformer = EntityToRestaurantTransformer()

    @Before
    fun init(){
        dataRepository = DataRepository(deliveryAPI, appDataBase, schedulersProvider,
            sharedPreferences, configManager, connectionUtil,
            restaurantToEntityTransformer, entityToRestaurantTransformerTransformer)
    }


    @Test
    fun `Test checkAndFetchConfiguration`(){
        doNothing().`when`(configManager).saveConfigurations(any())
        whenever(configManager.isConfigFetched()).thenReturn(true)
        whenever(configManager.shouldFetch()).thenReturn(false)

        val testSubscriber : TestSubscriber<Resource<Boolean>> = TestSubscriber()
        dataRepository.checkAndFetchConfiguration().subscribe(testSubscriber)

        assertEquals(testSubscriber.values()[0].data, true)

    }

}