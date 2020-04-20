package com.psycach_ktl.viewmodels.result

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.psycach_ktl.entities.results.SanResult
import com.psycach_ktl.enums.ResultLevels
import com.psycach_ktl.repositories.FormResultRepository
import com.psycach_ktl.viewmodels.ResultViewModel
import kotlinx.coroutines.launch

class SanResultViewModel(formResult: SanResult) : ResultViewModel() {
    private var _formResult = MutableLiveData<SanResult>()
    val formResult: LiveData<SanResult>
        get() = _formResult

    val healthLevel: ResultLevels
        get() = result.groupLevel(result.health)

    val activityLevel: ResultLevels
        get() = result.groupLevel(result.activity)

    val moodLevel: ResultLevels
        get() = result.groupLevel(result.mood)

    override val result: SanResult
        get() = _formResult.value!!

    init {
        _formResult.value = formResult
    }

//    fun saveResult(userId: String) {
//        viewModelScope.launch {
//            result.userId = userId
//
//            formResultRepository.save(result)
//        }
//    }
}