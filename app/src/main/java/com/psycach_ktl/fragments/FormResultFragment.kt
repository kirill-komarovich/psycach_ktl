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
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


abstract class FormResultFragment<BindingT:ViewDataBinding, ViewModelT:FormResultViewModel>(
    private val formResult: FormResult
): Fragment() {
    private lateinit var binding: BindingT
    private lateinit var viewModelFactoryForm: FormResultViewModel.Factory

    private lateinit var viewModel: ViewModelT

    private val types: Array<Type> = (this.javaClass.genericSuperclass as ParameterizedType?)!!.actualTypeArguments
    @Suppress("UNCHECKED_CAST")
    private val bindingClass = types[BINDING_TYPE_INDEX] as Class<BindingT>
    @Suppress("UNCHECKED_CAST")
    private val viewModelClass = types[VIEW_MODEL_TYPE_INDEX] as Class<ViewModelT>


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

    companion object {
        private const val BINDING_TYPE_INDEX = 0
        private const val VIEW_MODEL_TYPE_INDEX = 1
    }
}