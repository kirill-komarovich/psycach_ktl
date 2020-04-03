package com.psycach_ktl.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.psycach_ktl.R
import com.psycach_ktl.databinding.MethodologyInstructionsFragmentBinding
import com.psycach_ktl.viewmodels.MethodologyInstructionsViewModel

class MethodologyInstructionsFragment : Fragment() {
    private lateinit var binding: MethodologyInstructionsFragmentBinding
    private lateinit var viewModel: MethodologyInstructionsViewModel
    private lateinit var viewModelFactory: MethodologyInstructionsViewModel.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = MethodologyInstructionsFragmentArgs.fromBundle(arguments!!)

        viewModelFactory = MethodologyInstructionsViewModel.Factory(args.methodologyType)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MethodologyInstructionsViewModel::class.java)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.methodology_instructions_fragment,
            container,
            false
        )
        binding.instuctionsViewModel = viewModel
        binding.lifecycleOwner = this
        binding.startButton.setOnClickListener {
            this.findNavController().navigate(
                MethodologyInstructionsFragmentDirections.actionMethodologyInstructionsFragmentToFormFragment(args.methodologyType)
            )
        }

        return binding.root
    }
}
