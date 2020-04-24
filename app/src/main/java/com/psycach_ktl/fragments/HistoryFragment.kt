package com.psycach_ktl.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.firebase.ui.firestore.paging.LoadingState
import com.psycach_ktl.adapters.HistoryAdapter
import com.psycach_ktl.databinding.HistoryFragmentBinding
import com.psycach_ktl.viewmodels.HistoryViewModel
import com.psycach_ktl.viewmodels.LoaderViewModel

class HistoryFragment : Fragment() {
    private lateinit var binding: HistoryFragmentBinding
    private val loaderViewModel: LoaderViewModel by activityViewModels()
    private lateinit var viewModel: HistoryViewModel
    private lateinit var adapter: HistoryAdapter
    private val onItemClickListener = HistoryAdapter.Listener {
        this.findNavController().navigate(
            HistoryFragmentDirections.actionHistoryToResult(formResultParcel = it.toParcel())
        )
    }
    private val onLoadingStateChangedListener = HistoryAdapter.LoadingStateChangedListener {
        when(it) {
            LoadingState.LOADING_INITIAL -> loaderViewModel.start()
            LoadingState.LOADING_MORE -> binding.historyListAdditionalProgressBar.visibility = View.VISIBLE
            LoadingState.LOADED -> hideLoaders()
            LoadingState.FINISHED -> hideLoaders()
            LoadingState.ERROR -> adapter.retry() // TODO: show message
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val options = viewModel.buildPagingOptions(
            viewModel.initialQuery(),
            viewLifecycleOwner
        )
        adapter = HistoryAdapter(options, onItemClickListener, onLoadingStateChangedListener)
        binding = HistoryFragmentBinding.inflate(inflater)
        binding.historyList.adapter = adapter
        binding.lifecycleOwner = this

        viewModel.listQuery.observe(viewLifecycleOwner, Observer {
            val newOptions = viewModel.buildPagingOptions(it)

            adapter.updateOptions(newOptions)
        })

        return binding.root
    }

    private fun hideLoaders() {
        loaderViewModel.stop()
        binding.historyListAdditionalProgressBar.visibility = View.GONE
    }
}