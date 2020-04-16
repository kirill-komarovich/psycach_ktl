package com.psycach_ktl.viewmodels.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.psycach_ktl.entities.results.MentalStatesResult
import com.psycach_ktl.enums.ResultLevels
import com.psycach_ktl.viewmodels.ResultViewModel

class MentalStatesResultViewModel(formResult: MentalStatesResult) : ResultViewModel() {
    private var _formResult = MutableLiveData<MentalStatesResult>()
    val formResult: LiveData<MentalStatesResult>
        get() = _formResult

    val anxietyLevel: ResultLevels
        get() = result.groupLevel(result.anxiety)

    val frustrationLevel: ResultLevels
        get() = result.groupLevel(result.frustration)

    val aggressivenessLevel: ResultLevels
        get() = result.groupLevel(result.aggressiveness)

    val rigidityLevel: ResultLevels
        get() = result.groupLevel(result.rigidity)

    private val result: MentalStatesResult
        get() = _formResult.value!!

    init {
        _formResult.value = formResult
    }
}