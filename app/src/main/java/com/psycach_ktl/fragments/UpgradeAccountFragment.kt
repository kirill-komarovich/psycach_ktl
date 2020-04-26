package com.psycach_ktl.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.psycach_ktl.R
import com.psycach_ktl.builders.SnackbarBuilder
import com.psycach_ktl.databinding.UpgradeAccountFragmentBinding
import com.psycach_ktl.viewmodels.UpgradeAccountViewModel

class UpgradeAccountFragment : Fragment() {
    private lateinit var binding: UpgradeAccountFragmentBinding
    private val viewModel: UpgradeAccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UpgradeAccountFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.upgradeAccountButton.setOnClickListener { viewModel.updateRole() }

        viewModel.isUpgraded.observe(viewLifecycleOwner, Observer {
            if (it) {
                SnackbarBuilder(container!!).success(R.string.upgrade_account_success)
                this.findNavController().navigateUp()
            }
        })
        return binding.root
    }
}
