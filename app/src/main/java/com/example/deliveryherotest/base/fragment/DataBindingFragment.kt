package com.example.deliveryherotest.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class DataBindingFragment<VB : ViewDataBinding, VM: ViewModel> : DaggerFragment(){


    lateinit var binding: VB

    /**
     * This is a dummy variable to ensure that dagger does not generate duplicate providers
     * for this class in all the modules depending on this
     */
    @Inject
    lateinit var dummyVar: DummyUselessVariable


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        setBindings(binding)
        return binding.root
    }

    @CallSuper
    protected open fun setBindings(binding: VB) {
        binding.setVariable(getBindingVariableId(), getViewModel())
    }

    abstract fun getBindingVariableId(): Int

    @LayoutRes
    abstract fun getLayout(): Int

    abstract fun getViewModel(): VM
}