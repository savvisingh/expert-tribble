package com.example.deliveryherotest.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.deliveryherotest.BR
import com.example.deliveryherotest.R
import com.example.deliveryherotest.base.fragment.DataBindingFragment
import com.example.deliveryherotest.databinding.HomeFragmentBinding
import javax.inject.Inject

class HomeFragment : DataBindingFragment<HomeFragmentBinding, HomeViewModel>() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    override fun getBindingVariableId(): Int {
        return BR.vm
    }

    override fun getLayout(): Int {
        return R.layout.home_fragment
    }

    override fun getViewModel(): HomeViewModel {
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        return homeViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.fetchData()
    }

}