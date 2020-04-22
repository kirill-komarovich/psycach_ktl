package com.psycach_ktl.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.psycach_ktl.databinding.ResultFragmentBinding
import com.psycach_ktl.entities.FormResult
import com.psycach_ktl.enums.AuthenticationState
import com.psycach_ktl.enums.MethodologyTypes
import com.psycach_ktl.fragments.result.*
import com.psycach_ktl.viewmodels.AuthViewModel
import com.psycach_ktl.viewmodels.ResultViewModel

class ResultFragment : Fragment() {
    private lateinit var binding: ResultFragmentBinding
    private lateinit var viewModel: ResultViewModel
    private lateinit var viewModelFactory: ResultViewModel.Factory
    private val authViewModel: AuthViewModel by lazy {
        ViewModelProvider(this).get(AuthViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = ResultFragmentArgs.fromBundle(requireArguments())
        val formResult = when {
            args.formParcel != null -> FormResult.from(args.formParcel)
            args.formResultParcel != null -> FormResult.from(args.formResultParcel)
            else -> null
        }!!
        viewModelFactory = ResultViewModel.Factory(formResult)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ResultViewModel::class.java)

        if (authViewModel.authenticationState.value == AuthenticationState.AUTHENTICATED) {
            viewModel.processResult(authViewModel.currentUser.value!!.uid)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ResultFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.isReady.observe(viewLifecycleOwner, Observer {
            if (it) {
                applyCurrentFragment()

            }
        })

        return binding.root
    }

    private fun applyCurrentFragment() {
        val fragmentTransaction = parentFragmentManager.beginTransaction()

        val formResult = viewModel.result.value!!
        fragmentTransaction.add(binding.resultFragmentContainer.id, fragmentFor(formResult))

        fragmentTransaction.commit()
    }

    private fun fragmentFor(formResult: FormResult): Fragment {
        return when(formResult.methodologyType) {
            MethodologyTypes.SAN -> SanResultFragment(formResult)
            MethodologyTypes.MENTAL_STATES -> MentalStatesResultFragment(formResult)
            MethodologyTypes.JERSILD -> JersildResultFragment(formResult)
            MethodologyTypes.ALARM_SCALE -> AlarmScaleResultFragment(formResult)
            else -> throw IllegalArgumentException("Unknown ResultFragment for ${formResult.methodologyType}")
        }
    }
}
