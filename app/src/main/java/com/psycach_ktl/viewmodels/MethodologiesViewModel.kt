package com.psycach_ktl.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.psycach_ktl.entities.Methodology

class MethodologiesViewModel(methodologies: List<Methodology>) : ViewModel() {

    var methodologies = MutableLiveData<List<Methodology>>()

    init {
        this.methodologies.value = methodologies
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
