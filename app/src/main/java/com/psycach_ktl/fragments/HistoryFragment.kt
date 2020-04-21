package com.psycach_ktl.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.psycach_ktl.adapters.HistoryAdapter
import com.psycach_ktl.databinding.HistoryFragmentBinding
import com.psycach_ktl.entities.FormResult
import com.psycach_ktl.repositories.FormResultRepository
import com.psycach_ktl.viewmodels.AuthViewModel
import com.psycach_ktl.viewmodels.HistoryViewModel

class HistoryFragment : Fragment() {
    private lateinit var binding: HistoryFragmentBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var formResultRepository: FormResultRepository
    private lateinit var viewModelFactory: HistoryViewModel.Factory
    private lateinit var viewModel: HistoryViewModel
    private var adapter = HistoryAdapter(
        HistoryAdapter.Listener {
            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
        },
        HistoryAdapter.OnBottomReachedListener { position ->
            Toast.makeText(context, "Reached! $position", Toast.LENGTH_SHORT).show()
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        formResultRepository = FormResultRepository(authViewModel.currentUser.value?.uid)
        viewModelFactory = HistoryViewModel.Factory(formResultRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HistoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HistoryFragmentBinding.inflate(inflater)
        binding.historyList.adapter = adapter
        binding.lifecycleOwner = this

        viewModel.resultItems.observe(viewLifecycleOwner, Observer {
            it?.let { resultItems ->
                adapter.submitList(resultItems)
            }
        })

        return binding.root
    }
}