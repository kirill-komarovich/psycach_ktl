package com.psycach_ktl.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MethodologyInstructionsViewModel(methodologyType: String) : ViewModel() {

    var methodologyType = MutableLiveData<String>()
    val instructions: String
        get() = "${methodologyType.value}_instructions"

    init {
        this.methodologyType.value = methodologyType
    }

    class Factory(private var methodologyType: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MethodologyInstructionsViewModel::class.java)) {
                return MethodologyInstructionsViewModel(methodologyType) as T
            }

            throw IllegalAccessException("Unknown ViewModel class")
        }
    }
}