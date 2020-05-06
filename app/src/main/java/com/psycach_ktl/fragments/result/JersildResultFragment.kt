package com.psycach_ktl.fragments.result

import com.psycach_ktl.R
import com.psycach_ktl.databinding.JersildResultFragmentBinding
import com.psycach_ktl.entities.FormResult
import com.psycach_ktl.fragments.FormResultFragment
import com.psycach_ktl.util.camelToSnakeCase
import com.psycach_ktl.viewmodels.result.JersildResultViewModel

class JersildResultFragment(formResult: FormResult) :
    FormResultFragment<JersildResultFragmentBinding, JersildResultViewModel>(formResult) {
    private val methodologyProperties = listOf("Loneliness", "Meaninglessness", "Freedom",
                                                          "SexualConflict", "HostileConflict", "Discrepancy",
                                                          "StrengthOfWill", "Hopelessness", "Homelessness")

    override fun buildShareText(): String {
        val formResult = viewModel.formResult.value!!
        val prefix = formResult.methodologyType!!.toLowerCase()
        val results = methodologyProperties.map {
            val value = viewModelClass.getMethod("get${it}").invoke(viewModel) as Float
            val labelId = resources.getIdentifier(
                "${prefix}_${it.camelToSnakeCase()}",
                "string",
                requireContext().packageName
            )
            String.format(resources.getString(labelId), value)
        }
        val details = resources.getString(R.string.jersild_result_details)
        return details.plus("\n\n").plus(results.joinToString("\n\n"))
    }
}
