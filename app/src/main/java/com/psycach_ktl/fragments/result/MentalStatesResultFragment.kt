package com.psycach_ktl.fragments.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.psycach_ktl.databinding.MentalStatesResultFragmentBinding
import com.psycach_ktl.entities.FormResult
import com.psycach_ktl.fragments.ResultFragmentArgs
import com.psycach_ktl.viewmodels.ResultViewModel
import com.psycach_ktl.viewmodels.result.MentalStatesResultViewModel

class MentalStatesResultFragment : Fragment() {
    private lateinit var binding: MentalStatesResultFragmentBinding
    private lateinit var viewModel: MentalStatesResultViewModel
    private lateinit var viewModelFactory: ResultViewModel.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = ResultFragmentArgs.fromBundle(arguments!!)

        val formResult = FormResult.from(args.formParcel)

        viewModelFactory = ResultViewModel.Factory(formResult)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MentalStatesResultViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MentalStatesResultFragmentBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }
}