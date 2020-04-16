package com.psycach_ktl.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.psycach_ktl.R
import com.psycach_ktl.databinding.AboutMethodologyFragmentBinding
import com.psycach_ktl.viewmodels.MethodologyInstructionsViewModel

class AboutMethodologyFragment : Fragment() {
    private lateinit var binding: AboutMethodologyFragmentBinding
    private lateinit var viewModel: MethodologyInstructionsViewModel
    private lateinit var viewModelFactory: MethodologyInstructionsViewModel.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = AboutMethodologyFragmentArgs.fromBundle(arguments!!)

        viewModelFactory = MethodologyInstructionsViewModel.Factory(args.methodologyType)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MethodologyInstructionsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.about_methodology_fragment,
            container,
            false
        )
        binding.instuctionsViewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }
}