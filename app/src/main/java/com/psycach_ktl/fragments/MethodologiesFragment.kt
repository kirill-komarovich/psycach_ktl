package com.psycach_ktl.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.psycach_ktl.databinding.MethodologiesFragmentBinding
import com.psycach_ktl.R
import com.psycach_ktl.adapters.MethodologiesAdapter
import com.psycach_ktl.viewmodels.MethodologiesViewModel

class MethodologiesFragment : Fragment() {
    private lateinit var binding: MethodologiesFragmentBinding
    private lateinit var viewModel: MethodologiesViewModel
    private var adapter = MethodologiesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(MethodologiesViewModel::class.java)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.methodologies_fragment,
            container,
            false
        )
        binding.methodologiesList.adapter = adapter

        binding.lifecycleOwner = this

        viewModel.methodologies.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}
