package com.psycach_ktl.fragments.result

import com.psycach_ktl.databinding.JersildResultFragmentBinding
import com.psycach_ktl.fragments.FormResultFragment
import com.psycach_ktl.viewmodels.result.JersildResultViewModel

class JersildResultFragment : FormResultFragment<JersildResultFragmentBinding, JersildResultViewModel>(
    JersildResultFragmentBinding::class.java,
    JersildResultViewModel::class.java
)
