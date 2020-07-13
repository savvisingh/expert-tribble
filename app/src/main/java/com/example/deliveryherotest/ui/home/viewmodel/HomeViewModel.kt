package com.example.deliveryherotest.ui.home.viewmodel

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import com.example.deliveryherotest.base.BaseViewModel
import com.example.deliveryherotest.repository.IDataRepository
import com.example.deliveryherotest.repository.api.helper.Resource
import com.example.deliveryherotest.utils.scheduler.ISchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomeViewModel
@Inject constructor(var dataRepository: IDataRepository,
                    var appSchedulers: ISchedulers) : ViewModel() {

    var compositeDisposable = CompositeDisposable()

    var items = ObservableArrayList<BaseViewModel>()

    fun fetchData(){
        items.add(LoadingViewModel())
        compositeDisposable.add(dataRepository.fetchRestaurants(true)
            .subscribeOn(appSchedulers.io())
            .observeOn(appSchedulers.ui())
            .subscribe {
                when(it){
                    is Resource.Loading -> {
                        Log.d("HomeViewModel", "Loading")
                    }

                    is Resource.Error -> {
                        Log.d("HomeViewModel", "Error")
                        items.clear()
                    }

                    is Resource.Success -> {
                        Log.d("HomeViewModel", "Sucess")
                        val data = mutableListOf<BaseViewModel>()
                        it.data?.items?.forEach { res ->
                            res?.let {
                                data.add(RestaurantItemViewModel(res))
                            }
                        }

                        if(data.size > 0){
                            items.clear()
                            items.addAll(data)
                            items.addAll(data)
                            items.addAll(data)
                            items.addAll(data)
                        }

                    }
                }
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        if(!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }
}