package com.example.deliveryherotest.ui.home.viewmodel

import androidx.databinding.ObservableInt
import com.example.deliveryherotest.R
import com.example.deliveryherotest.repository.api.model.FilterData

class FilterItemViewModel(var data: FilterData? = null,
                          val title: String,
                          val action: (FilterItemViewModel) -> Unit){

    var textColor = ObservableInt(R.color.white_grey)

    fun onFilterClick(): () -> Unit = {
        action.invoke(this)
    }

    fun setSelected(isSelected: Boolean){
        if(isSelected){
            textColor.set(R.color.white)
        }else {
            textColor.set(R.color.white_grey)
        }
    }
}