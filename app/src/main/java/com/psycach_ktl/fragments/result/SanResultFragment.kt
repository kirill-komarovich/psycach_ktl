package com.psycach_ktl.fragments.result

import com.psycach_ktl.R
import com.psycach_ktl.databinding.SanResultFragmentBinding
import com.psycach_ktl.entities.FormResult
import com.psycach_ktl.enums.ResultLevels
import com.psycach_ktl.fragments.FormResultFragment
import com.psycach_ktl.viewmodels.result.SanResultViewModel

class SanResultFragment(formResult: FormResult) :
    FormResultFragment<SanResultFragmentBinding, SanResultViewModel>(formResult) {
    private val methodologyProperties = listOf("Health", "Activity", "Mood")

    override fun buildShareText(): String {
        val formResult = viewModel.formResult.value!!
        val prefix = formResult.methodologyType!!.toLowerCase()
        val results = methodologyProperties.map {
            val value = viewModelClass.getMethod("get${it}").invoke(viewModel) as Float
            val level = viewModelClass.getMethod("get${it}Level").invoke(viewModel) as ResultLevels
            val labelId = resources.getIdentifier(
                "${prefix}_${level.toLowerCase()}_${it.decapitalize()}",
                "string",
                requireContext().packageName
            )
            String.format(resources.getString(labelId), value)
        }

        return results.joinToString("\n").plus("\n\n").plus(resources.getString(R.string.san_details))
    }
}
