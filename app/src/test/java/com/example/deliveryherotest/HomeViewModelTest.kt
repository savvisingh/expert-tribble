package com.example.deliveryherotest

import com.example.deliveryherotest.repository.IDataRepository
import com.example.deliveryherotest.repository.api.helper.Resource
import com.example.deliveryherotest.repository.api.model.FilterData
import com.example.deliveryherotest.repository.api.model.Filters
import com.example.deliveryherotest.repository.api.model.Restaurant
import com.example.deliveryherotest.repository.config.IConfigManager
import com.example.deliveryherotest.ui.home.viewmodel.*
import com.example.deliveryherotest.utils.MockedSchedulers
import com.example.deliveryherotest.utils.resource.IResourceService
import com.example.deliveryherotest.utils.scheduler.ISchedulers
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Flowable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    lateinit var viewModelUnderTest: HomeViewModel

    @Mock
    lateinit var dataRepository: IDataRepository

    var schedulers: ISchedulers = MockedSchedulers()

    @Mock
    lateinit var configManager: IConfigManager

    @Mock
    lateinit var resourceService: IResourceService

    private val MOCK_FILTER_BY = "mockFilterBy"
    private val MOCK_SORT_DIR = "mockSortDir"

    @Before
    fun init(){
        viewModelUnderTest = spy(HomeViewModel(dataRepository, schedulers, configManager, resourceService))
    }


    @Test
    fun testFetchConfigsSuccess(){
        whenever(dataRepository.checkAndFetchConfiguration())
            .thenReturn(Flowable.just(Resource.Success(true)))
        whenever(dataRepository.fetchRestaurants())
            .thenReturn(Flowable.just(Resource.Success(Filters())))

        viewModelUnderTest.fetchConfigsAndData()

        assertEquals(viewModelUnderTest.items.size, 1)
        assert(viewModelUnderTest.items[0] is LoadingViewModel)
        verify(viewModelUnderTest).handleFetchDataSuccess(any())

    }

    @Test
    fun testFetchConfigsError(){
        whenever(dataRepository.checkAndFetchConfiguration())
            .thenReturn(Flowable.just(Resource.Error("")))

        viewModelUnderTest.fetchConfigsAndData()

        verify(viewModelUnderTest).showError()
        assertEquals(viewModelUnderTest.dataLoaded.get(), false)
        assertEquals(viewModelUnderTest.items.size, 1)
        assert(viewModelUnderTest.items[0] is ErrorStateViewModel)

    }

    @Test
    fun `testFetchConfigsError When config never fetched`(){
        whenever(dataRepository.checkAndFetchConfiguration())
            .thenReturn(Flowable.just(Resource.Success(false)))

        viewModelUnderTest.fetchConfigsAndData()

        verify(viewModelUnderTest).showError()
        assertEquals(viewModelUnderTest.dataLoaded.get(), false)
        assertEquals(viewModelUnderTest.items.size, 1)
        assert(viewModelUnderTest.items[0] is ErrorStateViewModel)

    }

    @Test
    fun `showError Test`(){
        viewModelUnderTest.showError()

        assertEquals(viewModelUnderTest.dataLoaded.get(), false)
        assertEquals(viewModelUnderTest.items.size, 1)
        assert(viewModelUnderTest.items[0] is ErrorStateViewModel)

    }

    @Test
    fun `handleFetchDataSuccess Test`(){
        val mockFilterData = FilterData(MOCK_FILTER_BY, MOCK_SORT_DIR)
        whenever(dataRepository.filterRestaurants(any())).thenReturn(
            Flowable.just(Resource.Error(""))
        )
        viewModelUnderTest.handleFetchDataSuccess(Resource.Success(Filters(mockFilterData, mockFilterData, mockFilterData)))

        assertEquals(viewModelUnderTest.nearByVm.data?.filterBy, MOCK_FILTER_BY)
        assertEquals(viewModelUnderTest.nearByVm.data?.sortDir, MOCK_SORT_DIR)

        assertEquals(viewModelUnderTest.popularByVm.data?.filterBy, MOCK_FILTER_BY)
        assertEquals(viewModelUnderTest.popularByVm.data?.sortDir, MOCK_SORT_DIR)

        assertEquals(viewModelUnderTest.reviewsByVm.data?.filterBy, MOCK_FILTER_BY)
        assertEquals(viewModelUnderTest.reviewsByVm.data?.sortDir, MOCK_SORT_DIR)

    }

    @Test
    fun `filterRestaurants Success`(){
        var list = ArrayList<Restaurant?>()
        list.add(mock())
        whenever(dataRepository.filterRestaurants(any())).thenReturn(
            Flowable.just(Resource.Success(list as List<Restaurant?>))
        )
        val mockFilterData = FilterData(MOCK_FILTER_BY, MOCK_SORT_DIR)
        viewModelUnderTest.filterRestaurants(mockFilterData)

        verify(viewModelUnderTest).addRestaurantViewModels(any())
        assertEquals(viewModelUnderTest.dataLoaded.get(), true)
    }

    @Test
    fun `addRestaurantViewModels Test`(){
        var list = ArrayList<Restaurant?>()
        list.add(Restaurant(1, "", "", 0.toDouble(), 0, listOf(), 0,0, 0))
        list.add(Restaurant(1, "", "", 0.toDouble(), 0, listOf(), 0,0, 0))
        list.add(Restaurant(1, "", "", 0.toDouble(), 0, listOf(), 0,0, 0))
        list.add(Restaurant(1, "", "", 0.toDouble(), 0, listOf(), 0,0, 0))

        viewModelUnderTest.addRestaurantViewModels(list)

        assertEquals(viewModelUnderTest.items.size, 4)
        assert(viewModelUnderTest.items[0] is RestaurantItemViewModel)

    }

    @Test
    fun `loadSearchItems Test`(){
        var list = ArrayList<Restaurant?>()
        list.add(Restaurant(1, "Chinese Burger", "", 0.toDouble(), 0, listOf(), 0,0, 0))
        list.add(Restaurant(1, "Noodles", "", 0.toDouble(), 0, listOf(), 0,0, 0))
        list.add(Restaurant(1, "Italian Pizza", "", 0.toDouble(), 0, listOf(), 0,0, 0))
        list.add(Restaurant(1, "Joey's Burger", "", 0.toDouble(), 0, listOf(), 0,0, 0))
        viewModelUnderTest.addRestaurantViewModels(list)

        viewModelUnderTest.loadSearchItems("burger")
        assertEquals(viewModelUnderTest.items.size, 2)
        assert(viewModelUnderTest.items[0] is RestaurantItemViewModel)

        viewModelUnderTest.loadSearchItems("Pizza")
        assertEquals(viewModelUnderTest.items.size, 1)
        assert(viewModelUnderTest.items[0] is RestaurantItemViewModel)

        viewModelUnderTest.loadSearchItems("nothing")
        assertEquals(viewModelUnderTest.items.size, 1)
        assert(viewModelUnderTest.items[0] is EmptyStateViewModel)

        viewModelUnderTest.loadSearchItems("")
        assertEquals(viewModelUnderTest.items.size, 4)
        assert(viewModelUnderTest.items[0] is RestaurantItemViewModel)

    }


}