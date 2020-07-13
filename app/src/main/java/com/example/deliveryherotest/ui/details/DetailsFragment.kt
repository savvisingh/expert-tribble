package com.example.deliveryherotest.ui.details

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.deliveryherotest.BR
import com.example.deliveryherotest.R
import com.example.deliveryherotest.base.fragment.DataBindingFragment
import com.example.deliveryherotest.databinding.DetailsFragmentBinding
import com.example.deliveryherotest.ui.details.viewmodel.DetailsViewModel
import javax.inject.Inject

class DetailsFragment: DataBindingFragment<DetailsFragmentBinding, DetailsViewModel>() {

    companion object {
        const val TAG = "DetailsFragment"
        const val PARAM_RESTAURANT_ID = "restaurant_id"

        fun newInstance(id: Int) = DetailsFragment().apply {
            arguments = bundleOf(Pair(PARAM_RESTAURANT_ID, id))
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var detailsViewModel: DetailsViewModel

    override fun getBindingVariableId(): Int {
        return BR.vm
    }

    override fun getLayout(): Int {
        return R.layout.details_fragment
    }

    override fun getViewModel(): DetailsViewModel {
        detailsViewModel =  ViewModelProvider(this, viewModelFactory)[DetailsViewModel::class.java]
        return detailsViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsViewModel.init(arguments?.getInt(PARAM_RESTAURANT_ID)?:0)

    }
}