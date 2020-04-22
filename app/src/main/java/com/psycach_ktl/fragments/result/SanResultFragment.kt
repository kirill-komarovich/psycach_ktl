package com.psycach_ktl.fragments.result

import com.psycach_ktl.databinding.SanResultFragmentBinding
import com.psycach_ktl.fragments.FormResultFragment
import com.psycach_ktl.viewmodels.result.SanResultViewModel

class SanResultFragment : FormResultFragment<SanResultFragmentBinding, SanResultViewModel>(
    SanResultFragmentBinding::class.java,
    SanResultViewModel::class.java
)
