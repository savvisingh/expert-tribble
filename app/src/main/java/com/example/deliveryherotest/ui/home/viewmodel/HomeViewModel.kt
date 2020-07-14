package com.example.deliveryherotest.ui.home.viewmodel

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.deliveryherotest.base.BaseViewModel
import com.example.deliveryherotest.repository.IDataRepository
import com.example.deliveryherotest.repository.api.helper.Resource
import com.example.deliveryherotest.repository.api.model.FilterData
import com.example.deliveryherotest.repository.api.model.Filters
import com.example.deliveryherotest.repository.api.model.Restaurant
import com.example.deliveryherotest.repository.api.model.RestaurantsResponse
import com.example.deliveryherotest.repository.config.IConfigManager
import com.example.deliveryherotest.ui.home.HomeEventHandler
import com.example.deliveryherotest.utils.BindingUtils
import com.example.deliveryherotest.utils.event.Event
import com.example.deliveryherotest.utils.resource.IResourceService
import com.example.deliveryherotest.utils.scheduler.ISchedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeViewModel
@Inject constructor(var dataRepository: IDataRepository,
                    var appSchedulers: ISchedulers,
                    var configManager: IConfigManager,
                    var resourceService: IResourceService) : ViewModel() {

    companion object{
        const val TAG = "HomeViewModel"
    }


    var compositeDisposable = CompositeDisposable()
    var previousListDisposable : Disposable? = null
    var items = ObservableArrayList<BaseViewModel>()
    val searchQuery: ObservableField<String> = ObservableField("")
    var allItems = ArrayList<BaseViewModel>()
    var selectedFilter: FilterItemViewModel? = null
    val eventHandler = MutableLiveData<Event<HomeEventHandler>>()



    var filterClickAction: (FilterItemViewModel) -> Unit = {
        if(selectedFilter != it){
            selectedFilter?.setSelected(false)
            selectedFilter = it
            selectedFilter?.setSelected(true)
            it.data?.let {
                filterRestaurants(it)
            }
        }

    }

    var favouriteClickHandler: (Boolean, Int)-> Unit = { isFavourite: Boolean, id: Int ->
        compositeDisposable.add(dataRepository.markFavourite(isFavourite, id)
            .subscribeOn(appSchedulers.computation())
            .observeOn(appSchedulers.ui())
            .subscribe())
    }

    var itemClickHandler: (Int)-> Unit = { id: Int ->
        eventHandler.value = Event(HomeEventHandler.OpenDetails(id))
    }

    var nearByVm: FilterItemViewModel = FilterItemViewModel(title = "Nearby", action = this.filterClickAction)
    var popularByVm: FilterItemViewModel = FilterItemViewModel(title = "Popular",  action = this.filterClickAction)
    var reviewsByVm: FilterItemViewModel = FilterItemViewModel(title = "Top Reviews",  action = this.filterClickAction)


    fun init(){
        compositeDisposable.add(
            BindingUtils.toObservable(searchQuery)
                .debounce(200, TimeUnit.MILLISECONDS)
                .subscribeOn(appSchedulers.ui())
                .observeOn(appSchedulers.ui())
                .subscribe ({loadSearchItems(it.trim()) }, { Log.e(TAG, it.message, it)})
        )

        fetchConfigs()
    }

    private fun fetchConfigs(){
        items.clear()
        items.add(LoadingViewModel())
        compositeDisposable.add(
            dataRepository.checkAndFetchConfiguration()
                .subscribe {
                    if(it is Resource.Success) {
                        fetchData()
                    }
                }
        )
    }
    private fun fetchData(){
        compositeDisposable.add(dataRepository.fetchRestaurants()
            .subscribe ({
                when(it){
                    is Resource.Loading -> {
                        Log.d(TAG, "Loading")
                    }

                    is Resource.Error -> {
                        Log.d(TAG, it.message)
                    }

                    is Resource.Success -> {
                        Log.d(TAG, "Success")
                        handleFetchDataSuccess(it)
                    }
                }
            },  {Log.e(TAG, "Error", it)})
        )
    }

    private fun handleFetchDataSuccess(it: Resource.Success<Filters>) {

        it.data.apply {
            nearByVm.data = this?.nearby
            popularByVm.data = this?.popular
            reviewsByVm.data = this?.topReviews
        }

        filterClickAction.invoke(nearByVm)

    }

    private fun addRestaurantViewModels(restList: List<Restaurant?>) {
        val data = mutableListOf<BaseViewModel>()
        restList.forEach { res ->
            res?.let {
                data.add(RestaurantItemViewModel(res, favouriteClickHandler, itemClickHandler,
                    configManager, resourceService))
            }
        }
        items.clear()
        if(data.size > 0){
            items.addAll(data)
            allItems.clear()
            allItems.addAll(data)
        } else {
            items.add(EmptyStateViewModel())
        }
    }

    private fun filterRestaurants(it: FilterData) {
        items.clear()
        items.add(LoadingViewModel())
        previousListDisposable?.let {
            if(!it.isDisposed){
                it.dispose()
            }
        }

        previousListDisposable = dataRepository.filterRestaurants(it)
            .subscribe({
                when(it){
                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        it.data?.let {
                            addRestaurantViewModels(it)
                        }
                    }

                    is Resource.Error -> {
                        items.clear()
                    }
                }
            }, {Log.e(TAG, "Error", it)})
        previousListDisposable?.let {
            compositeDisposable.add(it)
        }

    }


    private fun loadSearchItems(query: String){
        if(query.isNotEmpty()){
            val filteredList = allItems.filter { (it as RestaurantItemViewModel)
                .restaurantName.toLowerCase().contains(query.toLowerCase())}

            items.clear()
            if(filteredList.isNotEmpty()){
                items.addAll(filteredList)
            }else {
                items.add(EmptyStateViewModel())
            }

        }else {
            items.clear()
            items.addAll(allItems)
        }
    }

    fun searchClearClick(): () -> Unit = {
        searchQuery.set("")
    }

    override fun onCleared() {
        super.onCleared()
        if(!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }
}