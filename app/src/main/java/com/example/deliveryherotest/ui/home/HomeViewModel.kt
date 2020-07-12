package com.example.deliveryherotest.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.deliveryherotest.repository.IDataRepository
import com.example.deliveryherotest.repository.api.helper.Resource
import com.example.deliveryherotest.utils.scheduler.ISchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomeViewModel
@Inject constructor(var dataRepository: IDataRepository,
                    var appSchedulers: ISchedulers) : ViewModel() {

    var compositeDisposable = CompositeDisposable()

    fun fetchData(){
        compositeDisposable.add(dataRepository.fetchRestaurants(false)
            .subscribeOn(appSchedulers.io())
            .observeOn(appSchedulers.ui())
            .subscribe {
                when(it){
                    is Resource.Loading -> {
                        Log.d("HomeViewModel", "Loading")
                    }

                    is Resource.Error -> {
                        Log.d("HomeViewModel", "Error")
                    }

                    is Resource.Success -> {
                        Log.d("HomeViewModel", "Sucess")
                        Log.d("HomeViewModel", it.data?.toString())

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