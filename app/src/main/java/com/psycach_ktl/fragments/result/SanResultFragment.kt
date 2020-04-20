package com.psycach_ktl.fragments.result

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.psycach_ktl.databinding.SanResultFragmentBinding
import com.psycach_ktl.entities.FormResult
import com.psycach_ktl.enums.AuthenticationState
import com.psycach_ktl.fragments.ResultFragmentArgs
import com.psycach_ktl.viewmodels.AuthViewModel
import com.psycach_ktl.viewmodels.ResultViewModel
import com.psycach_ktl.viewmodels.result.SanResultViewModel

class SanResultFragment : Fragment() {
    private lateinit var binding: SanResultFragmentBinding
    private lateinit var viewModel: SanResultViewModel
    private lateinit var viewModelFactory: ResultViewModel.Factory
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = ResultFragmentArgs.fromBundle(arguments!!)

        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        val formResult = FormResult.from(args.formParcel)

        viewModelFactory = ResultViewModel.Factory(formResult)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SanResultViewModel::class.java)
        saveResult()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SanResultFragmentBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    private fun saveResult() {
        if (authViewModel.authenticationState.value == AuthenticationState.AUTHENTICATED) {
            viewModel.saveResult(authViewModel.currentUser.value!!.uid)
        }
    }
}
