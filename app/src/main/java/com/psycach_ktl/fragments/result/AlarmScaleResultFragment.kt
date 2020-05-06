package com.psycach_ktl.fragments.result

import com.psycach_ktl.R
import com.psycach_ktl.databinding.AlarmScaleResultFragmentBinding
import com.psycach_ktl.entities.FormResult
import com.psycach_ktl.enums.ResultLevels
import com.psycach_ktl.fragments.FormResultFragment
import com.psycach_ktl.util.camelToSnakeCase
import com.psycach_ktl.viewmodels.result.AlarmScaleResultViewModel

class AlarmScaleResultFragment(formResult: FormResult) :
    FormResultFragment<AlarmScaleResultFragmentBinding, AlarmScaleResultViewModel>(formResult) {

    private val methodologyProperties = listOf("SituationalAnxiety", "PersonalAnxiety")

    override fun buildShareText(): String {
        val formResult = viewModel.formResult.value!!
        val prefix = formResult.methodologyType!!.toLowerCase()
        val results = methodologyProperties.map {
            val value = viewModelClass.getMethod("get${it}").invoke(viewModel) as Float
            val level = viewModelClass.getMethod("get${it}Level").invoke(viewModel) as ResultLevels
            val labelId = resources.getIdentifier(
                "${prefix}_${level.toLowerCase()}_${it.camelToSnakeCase()}",
                "string",
                requireContext().packageName
            )
            String.format(resources.getString(labelId), value)
        }

        return results.joinToString("\n").plus("\n\n").plus(resources.getString(R.string.alarm_scale_result_details))
    }
}
