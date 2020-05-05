package com.psycach_ktl.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.psycach_ktl.adapters.PsychologistsAdapter
import com.psycach_ktl.databinding.PsychologistsFragmentBinding
import com.psycach_ktl.viewmodels.PsychologistsViewModel

class PsychologistsFragment: Fragment() {
    private lateinit var binding: PsychologistsFragmentBinding
    private val viewModel: PsychologistsViewModel by viewModels()
    private lateinit var adapter: PsychologistsAdapter
    private val dialog = AddPsychologistDialogFragment { email ->
        viewModel.addPsychologist(email)
    }
    private val onItemClickListener = PsychologistsAdapter.Listener {
        Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
        viewModel.removePsychologist(it.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val options = viewModel.buildPagingOptions(viewModel.initialQuery(), viewLifecycleOwner)
        adapter = PsychologistsAdapter(options, onItemClickListener)

        binding = PsychologistsFragmentBinding.inflate(inflater)
        binding.addPsychologistButton.setOnClickListener { openAddPsychologistDialog() }

        binding.psychologistsList.adapter = adapter
        binding.lifecycleOwner = this

        viewModel.listQuery.observe(viewLifecycleOwner, Observer {
            val newOptions = viewModel.buildPagingOptions(it)

            adapter.updateOptions(newOptions)
        })
        return binding.root
    }

    private fun openAddPsychologistDialog() {
        dialog.show(parentFragmentManager, DIALOG_TAG)
    }

    companion object {
        private const val DIALOG_TAG = "AddPsychologistDialogFragment"
    }
}