package com.psycach_ktl.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.psycach_ktl.databinding.UpgradeAccountFragmentBinding
import com.psycach_ktl.viewmodels.UpgradeAccountViewModel

class UpgradeAccountFragment() : Fragment() {
    private lateinit var binding: UpgradeAccountFragmentBinding
    private val upgradeAccountViewModel: UpgradeAccountViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UpgradeAccountFragmentBinding.inflate(inflater)

        return binding.root
    }
}