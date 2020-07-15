package com.example.deliveryherotest

import android.content.SharedPreferences
import com.example.deliveryherotest.repository.config.ConfigManager
import com.example.deliveryherotest.repository.config.IConfigManager
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ConfigManagerTest {

    lateinit var configmanager: IConfigManager

    @Mock
    lateinit var mockedSharedPref: SharedPreferences

    @Before
    fun init(){
        configmanager = ConfigManager(mockedSharedPref)
    }

    @Test
    fun `Test shouldFetch config never fetched`(){
        whenever(mockedSharedPref.contains(ConfigManager.PARAM_LAST_CONFIG_FETCH)).thenReturn(false)

        assertEquals(configmanager.shouldFetch(), true)
    }

    @Test
    fun `Test shouldFetch when config fetched in last x millis`(){
        whenever(mockedSharedPref.contains(ConfigManager.PARAM_LAST_CONFIG_FETCH)).thenReturn(true)
        whenever(mockedSharedPref.contains(ConfigManager.PARAM_CACHE_TIME)).thenReturn(true)
        whenever(mockedSharedPref.getLong(ConfigManager.PARAM_LAST_CONFIG_FETCH, 0)).thenReturn(System.currentTimeMillis()-200)
        whenever(mockedSharedPref.getLong(ConfigManager.PARAM_CACHE_TIME, 0)).thenReturn(300)

        assertEquals(configmanager.shouldFetch(), false)
    }

    @Test
    fun `Test shouldFetch when config not fetched in last x millis`(){
        whenever(mockedSharedPref.contains(ConfigManager.PARAM_LAST_CONFIG_FETCH)).thenReturn(true)
        whenever(mockedSharedPref.contains(ConfigManager.PARAM_CACHE_TIME)).thenReturn(true)
        whenever(mockedSharedPref.getLong(ConfigManager.PARAM_LAST_CONFIG_FETCH, 0)).thenReturn(System.currentTimeMillis()-400)
        whenever(mockedSharedPref.getLong(ConfigManager.PARAM_CACHE_TIME, 0)).thenReturn(300)

        assertEquals(configmanager.shouldFetch(), true)
    }
}