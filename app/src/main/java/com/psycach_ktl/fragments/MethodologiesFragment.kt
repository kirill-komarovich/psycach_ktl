package com.psycach_ktl.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.psycach_ktl.databinding.MethodologiesFragmentBinding
import com.psycach_ktl.adapters.MethodologiesAdapter
import com.psycach_ktl.entities.Methodology
import com.psycach_ktl.viewmodels.MethodologiesViewModel

class MethodologiesFragment : Fragment() {
    private lateinit var binding: MethodologiesFragmentBinding
    private lateinit var viewModel: MethodologiesViewModel
    private lateinit var viewModelFactory: MethodologiesViewModel.Factory
    private var adapter = MethodologiesAdapter(MethodologiesAdapter.Listener { methodologyType ->
        this.findNavController().navigate(MethodologiesFragmentDirections.actionMethodologiesToInstructions(methodologyType))
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModelFactory = MethodologiesViewModel.Factory(Methodology.supportedMethodologies)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MethodologiesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MethodologiesFragmentBinding.inflate(inflater)

        binding.methodologiesList.adapter = adapter
        binding.lifecycleOwner = this

        viewModel.methodologies.observe(viewLifecycleOwner, Observer {
            it?.let { methodologies ->
                adapter.submitList(methodologies)
            }
        })

        return binding.root
    }
}
