package com.psycach_ktl.fragments.result

import com.psycach_ktl.databinding.AlarmScaleResultFragmentBinding
import com.psycach_ktl.fragments.FormResultFragment
import com.psycach_ktl.viewmodels.result.AlarmScaleResultViewModel

class AlarmScaleResultFragment : FormResultFragment<AlarmScaleResultFragmentBinding, AlarmScaleResultViewModel>(
    AlarmScaleResultFragmentBinding::class.java,
    AlarmScaleResultViewModel::class.java
)
