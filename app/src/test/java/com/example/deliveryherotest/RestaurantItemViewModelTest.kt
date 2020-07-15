package com.example.deliveryherotest

import com.example.deliveryherotest.repository.api.model.Restaurant
import com.example.deliveryherotest.repository.config.IConfigManager
import com.example.deliveryherotest.ui.home.viewmodel.RestaurantItemViewModel
import com.example.deliveryherotest.utils.resource.IResourceService
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RestaurantItemViewModelTest {

    lateinit var viewModelUnderTest: RestaurantItemViewModel

    @Mock
    lateinit var configManager: IConfigManager

    @Mock
    lateinit var resourceService: IResourceService

    var mockedFilterClickAction :(Boolean, Int) -> Unit = { _, _ ->

    }

    var mockedItemClickAction :(Int) -> Unit = {

    }

    private val MOCKED_ID = 100
    private val MOCKED_NAME = "mockedName"
    private val MOCKED_IMAGE = "mockedIMAGEUrl"
    private val MOCKED_RATING = 4.0
    private val MOCKED_REVIEWS = 40
    private val MOCKED_CUISINES = listOf("Chinese", "Indian")
    private val MOCKED_DISTANCE = 1200
    private val MOCKED_PRICE_TIER = 2
    private val MOCKED_POPULARITY_SCORE = 10

    @Before
    fun init(){
        whenever(configManager.getCurrencySymbol()).thenReturn("$")
        whenever(configManager.getMaxPriceTier()).thenReturn(5)
        whenever(configManager.isAddToFavEnabled()).thenReturn(true)
        val restaurant = Restaurant(MOCKED_ID, MOCKED_NAME, MOCKED_IMAGE, MOCKED_RATING, MOCKED_REVIEWS, MOCKED_CUISINES, MOCKED_DISTANCE,
        MOCKED_PRICE_TIER, MOCKED_POPULARITY_SCORE, listOf(), true)
        viewModelUnderTest = RestaurantItemViewModel(restaurant, mockedFilterClickAction, mockedItemClickAction, configManager, resourceService)
    }

    @Test
    fun testInitializations(){

        assertEquals(viewModelUnderTest.restaurantName, MOCKED_NAME)
        assertEquals(viewModelUnderTest.imageUrl, MOCKED_IMAGE)
        assertEquals(viewModelUnderTest.averageRating.toInt(), MOCKED_RATING.toInt())
        assertEquals(viewModelUnderTest.noOfReviews, "$MOCKED_REVIEWS reviews")
        assertEquals(viewModelUnderTest.distance, "1.2 km")
        assertEquals(viewModelUnderTest.cuisinesText, "Chinese | Indian")
        assertEquals(viewModelUnderTest.isFavouriteEnabled, true)
        assertEquals(viewModelUnderTest.favourite.get(), true)
    }
}