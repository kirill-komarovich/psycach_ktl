package com.psycach_ktl.viewmodels

import androidx.lifecycle.*
import com.psycach_ktl.enums.MethodologyTypes

class MethodologyInstructionsViewModel(methodologyType: MethodologyTypes) : ViewModel() {

    private var _methodologyType = MutableLiveData<MethodologyTypes>()
    val methodologyType: LiveData<MethodologyTypes>
        get() = _methodologyType

    val instructions = Transformations.map(this.methodologyType) { type ->
        "${type.toLowerCase()}_instructions"
    }

    init {
        _methodologyType.value = methodologyType
    }

    class Factory(private var methodologyType: MethodologyTypes) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MethodologyInstructionsViewModel::class.java)) {
                return MethodologyInstructionsViewModel(methodologyType) as T
            }

            throw IllegalAccessException("Unknown ViewModel class")
        }
    }
}