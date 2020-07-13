package com.example.deliveryherotest.ui.details.viewmodel

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.deliveryherotest.base.BaseViewModel
import com.example.deliveryherotest.repository.IDataRepository
import com.example.deliveryherotest.repository.api.helper.Resource
import com.example.deliveryherotest.repository.api.model.Restaurant
import com.example.deliveryherotest.ui.home.viewmodel.HomeViewModel
import com.example.deliveryherotest.ui.home.viewmodel.LoadingViewModel
import com.example.deliveryherotest.utils.scheduler.ISchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class DetailsViewModel
@Inject constructor(var dataRepository: IDataRepository,
                    var appSchedulers: ISchedulers) : ViewModel() {

    var items = ObservableArrayList<BaseViewModel>()
    var imageUrl = ObservableField("")
    var title = ObservableField("")

    var compositeDisposable = CompositeDisposable()

    fun init(id: Int){
        fetchRestaurantDetails(id)
    }

    private fun fetchRestaurantDetails(id: Int){
        items.add(LoadingViewModel())
        compositeDisposable.add(
            dataRepository.getRestaurantDetails(id)
                .subscribeOn(appSchedulers.io())
                .observeOn(appSchedulers.ui())
                .subscribe({
                    when(it){
                        is Resource.Loading -> {
                            Log.d(HomeViewModel.TAG, "Loading")
                        }

                        is Resource.Error -> {
                            Log.d(HomeViewModel.TAG, it.message)
                            items.clear()
                        }

                        is Resource.Success -> {
                            Log.d(HomeViewModel.TAG, "Success")
                            handleFetchDataSuccess(it)
                        }
                    }
                }, {
                    Log.e(HomeViewModel.TAG, "Error", it)
                })
        )
    }

    private fun handleFetchDataSuccess(it: Resource.Success<Restaurant>) {
        var templist = ArrayList<BaseViewModel>()
        it.data?.let {
            it.menu.forEach {
                templist.add(CategoryHeaderViewModel(it.categoryTitle))
                it.products.forEach {
                    templist.add(MenuItemViewModel(it))
                }
            }
            imageUrl.set(it.image)
            title.set(it.name)
        }

        items.clear()
        items.addAll(templist)
    }

    override fun onCleared() {
        super.onCleared()
        if(!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }

}