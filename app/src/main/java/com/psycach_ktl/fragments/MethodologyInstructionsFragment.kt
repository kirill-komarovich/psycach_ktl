package com.psycach_ktl.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.psycach_ktl.R
import com.psycach_ktl.databinding.MethodologyInstructionsFragmentBinding
import com.psycach_ktl.viewmodels.MethodologyInstructionsViewModel

class MethodologyInstructionsFragment : Fragment() {
    private lateinit var binding: MethodologyInstructionsFragmentBinding
    private lateinit var viewModel: MethodologyInstructionsViewModel
    private lateinit var viewModelFactory: MethodologyInstructionsViewModel.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = MethodologyInstructionsFragmentArgs.fromBundle(arguments!!)

        viewModelFactory = MethodologyInstructionsViewModel.Factory(args.methodologyType)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MethodologyInstructionsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MethodologyInstructionsFragmentBinding.inflate(inflater)

        binding.instuctionsViewModel = viewModel
        binding.lifecycleOwner = this
        binding.startButton.setOnClickListener {
            this.findNavController().navigate(
                MethodologyInstructionsFragmentDirections.actionMethodologyInstructionsFragmentToFormFragment(viewModel.methodologyType.value!!)
            )
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.form_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about_methodology_fragment -> {
                val aboutBundle = Bundle()

                aboutBundle.putSerializable("methodologyType", viewModel.methodologyType.value!!)

                view!!.findNavController().navigate(R.id.about_methodology_fragment, aboutBundle)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
