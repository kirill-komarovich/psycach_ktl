package com.psycach_ktl.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.firebase.ui.firestore.paging.LoadingState
import com.psycach_ktl.adapters.HistoryAdapter
import com.psycach_ktl.databinding.HistoryFragmentBinding
import com.psycach_ktl.repositories.FormResultRepository
import com.psycach_ktl.viewmodels.AuthViewModel
import com.psycach_ktl.viewmodels.HistoryViewModel

class HistoryFragment : Fragment() {
    private lateinit var binding: HistoryFragmentBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var formResultRepository: FormResultRepository
    private lateinit var viewModelFactory: HistoryViewModel.Factory
    private lateinit var viewModel: HistoryViewModel
    private lateinit var adapter: HistoryAdapter
    private val onItemClickListener = HistoryAdapter.Listener {
        Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
        this.findNavController().navigate(
            HistoryFragmentDirections.actionHistoryToResult(formResultParcel = it.toParcel())
        )
    }
    private val onLoadingStateChangedListener = HistoryAdapter.LoadingStateChangedListener {
        when(it) {
            LoadingState.LOADING_INITIAL -> binding.historyListProgressBar.visibility = View.VISIBLE
            LoadingState.LOADING_MORE -> binding.historyListAdditionalProgressBar.visibility = View.VISIBLE
            LoadingState.LOADED -> hideLoaders()
            LoadingState.FINISHED -> hideLoaders()
            LoadingState.ERROR -> adapter.retry() // TODO: show message
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        formResultRepository = FormResultRepository()
        viewModelFactory = HistoryViewModel.Factory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(HistoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val userId = authViewModel.currentUser.value!!.uid
        val options = viewModel.buildPagingOptions(
            viewModel.initialQuery(userId),
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
        binding.apply {
            historyListProgressBar.visibility = View.GONE
            historyListAdditionalProgressBar.visibility = View.GONE
        }
    }
}