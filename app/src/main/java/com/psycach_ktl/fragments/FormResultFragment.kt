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
import com.psycach_ktl.viewmodels.FormResultViewModel

abstract class FormResultFragment<BindingT:ViewDataBinding, ViewModelT:FormResultViewModel>(
    private val bindingClass: Class<BindingT>,
    private val viewModelClass: Class<ViewModelT>,
    private val formResult: FormResult
): Fragment() {
    private lateinit var binding: BindingT
    private lateinit var viewModelFactoryForm: FormResultViewModel.Factory

    private lateinit var viewModel: ViewModelT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModelFactoryForm = FormResultViewModel.Factory(formResult)
        viewModel = ViewModelProvider(this, viewModelFactoryForm).get(viewModelClass)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflateMethod = bindingClass.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        @Suppress("UNCHECKED_CAST")
        binding = inflateMethod.invoke(null, inflater, container, false) as BindingT
        binding.setVariable(BR.viewModel, viewModel)
        binding.lifecycleOwner = this

        return binding.root
    }
}