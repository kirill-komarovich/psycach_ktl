package com.psycach_ktl.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.psycach_ktl.R
import com.psycach_ktl.enums.MethodologyTypes
import com.psycach_ktl.fragments.result.*

class ResultFragment : Fragment() {
    private lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = ResultFragmentArgs.fromBundle(arguments!!)
        val methodologyType = args.formParcel.methodologyType
        currentFragment = fragmentFrom(methodologyType)
        currentFragment.arguments = arguments
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        applyCurrentFragment()

        return inflater.inflate(R.layout.result_fragment, container, false)
    }


    private fun applyCurrentFragment() {
        val fragmentTransaction = parentFragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.result_fragment_container, currentFragment)

        fragmentTransaction.commit()
    }

    private fun fragmentFrom(methodologyType: MethodologyTypes): Fragment {
        return when(methodologyType) {
            MethodologyTypes.SAN -> SanResultFragment()
            MethodologyTypes.MENTAL_STATES -> MentalStatesResultFragment()
            MethodologyTypes.JERSILD -> JersildResultFragment()
            MethodologyTypes.ALARM_SCALE -> AlarmScaleResultFragment()
            else -> throw IllegalArgumentException("Unknown ResultFragment for $methodologyType")
        }
    }
}
