package com.psycach_ktl.viewmodels.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.psycach_ktl.entities.results.SanResult
import com.psycach_ktl.enums.ResultLevels
import com.psycach_ktl.viewmodels.ResultViewModel

class SanResultViewModel(formResult: SanResult) : ResultViewModel() {
    private var _formResult = MutableLiveData<SanResult>()
    val formResult: LiveData<SanResult>
        get() = _formResult

    val healthLevel: ResultLevels
        get() {
            val result = formResult.value!!
            return result.groupLevel(result.health)
        }
    val activityLevel: ResultLevels
        get() {
            val result = formResult.value!!
            return result.groupLevel(result.activity)
        }
    val moodLevel: ResultLevels
        get() {
            val result = formResult.value!!
            return result.groupLevel(result.mood)
        }

    init {
        _formResult.value = formResult
    }

}