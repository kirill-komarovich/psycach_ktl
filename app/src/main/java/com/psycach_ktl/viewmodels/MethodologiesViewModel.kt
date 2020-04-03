package com.psycach_ktl.viewmodels

import androidx.lifecycle.*
import com.psycach_ktl.entities.Methodology

class MethodologiesViewModel(methodologies: List<Methodology>) : ViewModel() {

    private var _methodologies = MutableLiveData<List<Methodology>>()
    val methodologies: LiveData<List<Methodology>>
        get() = _methodologies

    init {
        this._methodologies.value = methodologies
    }

    class Factory(private var methodologies: List<Methodology>) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MethodologiesViewModel::class.java)) {
                return MethodologiesViewModel(methodologies) as T
            }

            throw IllegalAccessException("Unknown ViewModel class")
        }
    }
}
