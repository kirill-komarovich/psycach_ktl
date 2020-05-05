package com.psycach_ktl.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.firebase.ui.firestore.paging.LoadingState
import com.psycach_ktl.adapters.HistoryAdapter
import com.psycach_ktl.databinding.HistoryFragmentBinding
import com.psycach_ktl.managers.UserManager
import com.psycach_ktl.viewmodels.HistoryViewModel
import com.psycach_ktl.viewmodels.LoaderViewModel

class HistoryFragment : Fragment() {
    private lateinit var binding: HistoryFragmentBinding
    private val loaderViewModel: LoaderViewModel by activityViewModels()
    private val viewModel: HistoryViewModel by viewModels()
    private lateinit var adapter: HistoryAdapter
    private lateinit var userId: String
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

        val args = HistoryFragmentArgs.fromBundle(requireArguments())

        userId = args.userId ?: UserManager.currentUser!!.uid
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(HISTORY_LIST_BUNDLE_KEY, binding.historyList.layoutManager!!.onSaveInstanceState())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("HistoryFragment", savedInstanceState.toString())
        val options = viewModel.buildPagingOptions(
            viewModel.initialQuery(userId),
            viewLifecycleOwner
        )
        adapter = HistoryAdapter(options, onItemClickListener, onLoadingStateChangedListener)
        binding = HistoryFragmentBinding.inflate(inflater)
        binding.historyList.layoutManager?.onRestoreInstanceState(savedInstanceState?.getParcelable(HISTORY_LIST_BUNDLE_KEY))
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

    companion object {
        private const val HISTORY_LIST_BUNDLE_KEY = "historyListState"
    }
}