package com.psycach_ktl.viewmodels.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.psycach_ktl.entities.results.AlarmScaleResult
import com.psycach_ktl.entities.results.JersildResult
import com.psycach_ktl.enums.ResultLevels
import com.psycach_ktl.viewmodels.ResultViewModel

class AlarmScaleResultViewModel(formResult: AlarmScaleResult) : ResultViewModel() {
    private var _formResult = MutableLiveData<AlarmScaleResult>()
    val formResult: LiveData<AlarmScaleResult>
        get() = _formResult

    val situationalAnxietyLevel: ResultLevels
        get() = result.groupLevel(result.situationalAnxiety)

    val personalAnxietyLevel: ResultLevels
        get() = result.groupLevel(result.personalAnxiety)

    override val result: AlarmScaleResult
        get() = _formResult.value!!

    init {
        _formResult.value = formResult
    }
}
