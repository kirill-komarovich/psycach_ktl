package com.psycach_ktl.fragments.result

import com.psycach_ktl.databinding.MentalStatesResultFragmentBinding
import com.psycach_ktl.fragments.FormResultFragment
import com.psycach_ktl.viewmodels.result.MentalStatesResultViewModel

class MentalStatesResultFragment : FormResultFragment<MentalStatesResultFragmentBinding, MentalStatesResultViewModel>(
    MentalStatesResultFragmentBinding::class.java,
    MentalStatesResultViewModel::class.java
)
