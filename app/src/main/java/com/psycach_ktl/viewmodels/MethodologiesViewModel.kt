package com.psycach_ktl.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.psycach_ktl.entities.Methodology

class MethodologiesViewModel : ViewModel() {

    var methodologies = MutableLiveData<List<Methodology>>()

    init {
        methodologies.value = Methodology.supportedMethodologies
    }
}
