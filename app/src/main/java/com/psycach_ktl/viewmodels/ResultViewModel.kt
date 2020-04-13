package com.psycach_ktl.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.psycach_ktl.entities.FormResult
import com.psycach_ktl.entities.results.MentalStatesResult
import com.psycach_ktl.entities.results.SanResult
import com.psycach_ktl.viewmodels.result.MentalStatesResultViewModel
import com.psycach_ktl.viewmodels.result.SanResultViewModel

open class ResultViewModel : ViewModel() {
    class Factory(private val formResult: FormResult) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (ResultViewModel::class.java.isAssignableFrom(modelClass)) {
                return when (modelClass) {
                    SanResultViewModel::class.java -> SanResultViewModel(formResult as SanResult)
                    MentalStatesResultViewModel::class.java -> MentalStatesResultViewModel(formResult as MentalStatesResult)
                    else -> ResultViewModel()
                } as T
            }

            throw IllegalAccessException("Unknown ViewModel class")
        }
    }
}
