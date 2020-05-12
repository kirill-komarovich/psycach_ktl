package com.psycach_ktl.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.psycach_ktl.adapters.ClientsAdapter
import com.psycach_ktl.databinding.ClientsFragmentBinding
import com.psycach_ktl.viewmodels.ClientsViewModel

class ClientsFragment: Fragment() {
    private lateinit var binding: ClientsFragmentBinding
    private val viewModel: ClientsViewModel by viewModels()
    private lateinit var adapter: ClientsAdapter
    private val onItemClickListener = ClientsAdapter.Listener {
        this.findNavController().navigate(ClientsFragmentDirections.actionClientsToHistory(it.id))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val options = viewModel.buildPagingOptions(viewModel.initialQuery(), viewLifecycleOwner)
        adapter = ClientsAdapter(options, onItemClickListener)

        binding = ClientsFragmentBinding.inflate(inflater)

        binding.psychologistsList.adapter = adapter
        binding.lifecycleOwner = this

        viewModel.listQuery.observe(viewLifecycleOwner, Observer {
            val newOptions = viewModel.buildPagingOptions(it)

            adapter.updateOptions(newOptions)
        })
        return binding.root
    }
}