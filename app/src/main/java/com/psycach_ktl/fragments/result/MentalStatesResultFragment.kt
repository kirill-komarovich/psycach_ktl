package com.psycach_ktl.fragments.result

import com.psycach_ktl.databinding.MentalStatesResultFragmentBinding
import com.psycach_ktl.entities.FormResult
import com.psycach_ktl.enums.ResultLevels
import com.psycach_ktl.fragments.FormResultFragment
import com.psycach_ktl.viewmodels.result.MentalStatesResultViewModel

class MentalStatesResultFragment(formResult: FormResult) :
    FormResultFragment<MentalStatesResultFragmentBinding, MentalStatesResultViewModel>(formResult) {

    private val methodologyProperties = listOf("Anxiety", "Frustration", "Aggressiveness", "Rigidity")

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
            val detailsId = resources.getIdentifier(
                "${prefix}_${it.decapitalize()}_details",
                "string",
                requireContext().packageName
            )
            String.format(resources.getString(labelId), value).plus("\n\n").plus(resources.getString(detailsId))
        }

        return results.joinToString("\n\n")
    }
}
