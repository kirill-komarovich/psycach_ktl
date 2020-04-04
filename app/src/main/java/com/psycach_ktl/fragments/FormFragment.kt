package com.psycach_ktl.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.psycach_ktl.R
import com.psycach_ktl.adapters.FormItemsAdapter
import com.psycach_ktl.databinding.FormFragmentBinding
import com.psycach_ktl.viewmodels.FormViewModel

class FormFragment : Fragment() {
    private lateinit var binding: FormFragmentBinding
    private lateinit var viewModel: FormViewModel
    private lateinit var viewModelFactory: FormViewModel.Factory
    private var adapter = FormItemsAdapter(FormItemsAdapter.SubmitListener {
        Toast.makeText(context, viewModel.form.value?.items?.joinToString(", "), Toast.LENGTH_SHORT).show()
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = FormFragmentArgs.fromBundle(arguments!!) // TODO: move to onCreate

        viewModelFactory = FormViewModel.Factory(args.methodologyType)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FormViewModel::class.java)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.form_fragment,
            container,
            false
        )
        binding.formItemsList.adapter = adapter
        binding.lifecycleOwner = this

        viewModel.formItems.observe(viewLifecycleOwner, Observer {
            it?.let { formItems ->
                adapter.submitList(formItems)
            }
        })

        return binding.root
    }
}
