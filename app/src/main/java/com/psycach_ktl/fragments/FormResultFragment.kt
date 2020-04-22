package com.psycach_ktl.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.psycach_ktl.BR
import com.psycach_ktl.entities.FormResult
import com.psycach_ktl.enums.AuthenticationState
import com.psycach_ktl.viewmodels.AuthViewModel
import com.psycach_ktl.viewmodels.ResultViewModel

abstract class FormResultFragment<BindingT:ViewDataBinding, ViewModelT:ResultViewModel>(
    private val bindingClass: Class<BindingT>,
    private val viewModelClass: Class<ViewModelT>
): Fragment() {
    private lateinit var binding: BindingT
    private lateinit var viewModelFactory: ResultViewModel.Factory
    private val authViewModel: AuthViewModel by lazy {
        ViewModelProvider(this).get(AuthViewModel::class.java)
    }

    private lateinit var viewModel: ViewModelT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = ResultFragmentArgs.fromBundle(requireArguments())
        val formResult = when {
            args.formParcel != null -> FormResult.from(args.formParcel)
            args.formResultParcel != null -> FormResult.from(args.formResultParcel)
            else -> null
        }!!
        viewModelFactory = ResultViewModel.Factory(formResult)
        viewModel = ViewModelProvider(this, viewModelFactory).get(viewModelClass)

        if (formResult.isNewRecord()) saveResult()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflateMethod = bindingClass.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        binding = inflateMethod.invoke(null, inflater, container, false) as BindingT
        binding.setVariable(BR.viewModel, viewModel)
        binding.lifecycleOwner = this

        return binding.root
    }

    private fun saveResult() {
        if (authViewModel.authenticationState.value == AuthenticationState.AUTHENTICATED) {
            viewModel.saveResult(authViewModel.currentUser.value!!.uid)
        }
    }
}