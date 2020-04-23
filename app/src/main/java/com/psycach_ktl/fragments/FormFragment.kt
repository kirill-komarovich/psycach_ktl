package com.psycach_ktl.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.psycach_ktl.R
import com.psycach_ktl.adapters.FormItemsAdapter
import com.psycach_ktl.builders.SnackbarBuilder
import com.psycach_ktl.databinding.FormFragmentBinding
import com.psycach_ktl.viewmodels.FormViewModel

class FormFragment : Fragment() {
    private lateinit var binding: FormFragmentBinding
    private lateinit var viewModel: FormViewModel
    private lateinit var viewModelFactory: FormViewModel.Factory
    private lateinit var snackbarBuilder: SnackbarBuilder
    private var adapter = FormItemsAdapter(FormItemsAdapter.SubmitListener {
        val form = viewModel.form.value!!
        if (form.isValid()) {
            this.findNavController()
                .navigate(FormFragmentDirections.actionFormToResult(formParcel =  form.toParcel()))
        } else {
            snackbarBuilder.error(R.string.error_presence_all)
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = FormFragmentArgs.fromBundle(requireArguments())

        viewModelFactory = FormViewModel.Factory(args.methodologyType)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FormViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FormFragmentBinding.inflate(inflater)

        binding.formItemsList.adapter = adapter
        binding.lifecycleOwner = this

        viewModel.formItems.observe(viewLifecycleOwner, Observer {
            it?.let { formItems ->
                adapter.submitList(formItems)
            }
        })
        snackbarBuilder = SnackbarBuilder(container!!)

        return binding.root
    }
}
