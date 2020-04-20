package com.psycach_ktl.viewmodels.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.psycach_ktl.entities.results.JersildResult
import com.psycach_ktl.viewmodels.ResultViewModel

class JersildResultViewModel(formResult: JersildResult) : ResultViewModel() {
    private var _formResult = MutableLiveData<JersildResult>()
    val formResult: LiveData<JersildResult>
        get() = _formResult

    override val result: JersildResult
        get() = _formResult.value!!

    init {
        _formResult.value = formResult
    }
}
