package com.psycach_ktl.fragments.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.psycach_ktl.R
import com.psycach_ktl.databinding.JersildResultFragmentBinding
import com.psycach_ktl.entities.FormResult
import com.psycach_ktl.fragments.ResultFragmentArgs
import com.psycach_ktl.viewmodels.ResultViewModel
import com.psycach_ktl.viewmodels.result.JersildResultViewModel

class JersildResultFragment : Fragment() {
    private lateinit var binding: JersildResultFragmentBinding
    private lateinit var viewModel: JersildResultViewModel
    private lateinit var viewModelFactory: ResultViewModel.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = ResultFragmentArgs.fromBundle(arguments!!)

        val formResult = FormResult.from(args.formParcel)

        viewModelFactory = ResultViewModel.Factory(formResult)
        viewModel = ViewModelProvider(this, viewModelFactory).get(JersildResultViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.jersild_result_fragment,
            container,
            false
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }
}