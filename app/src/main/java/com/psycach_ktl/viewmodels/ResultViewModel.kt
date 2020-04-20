package com.psycach_ktl.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.psycach_ktl.entities.FormResult
import com.psycach_ktl.entities.results.*
import com.psycach_ktl.repositories.FormResultRepository
import com.psycach_ktl.viewmodels.result.*
import kotlinx.coroutines.launch

abstract class ResultViewModel : ViewModel() {
    private val formResultRepository = FormResultRepository()
    protected abstract val result: FormResult

    fun saveResult(userId: String) {
        viewModelScope.launch {
            result.userId = userId

            formResultRepository.save(result)
        }
    }
    class Factory(private val formResult: FormResult) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (ResultViewModel::class.java.isAssignableFrom(modelClass)) {
                return when (modelClass) {
                    SanResultViewModel::class.java -> SanResultViewModel(formResult as SanResult)
                    MentalStatesResultViewModel::class.java -> MentalStatesResultViewModel(formResult as MentalStatesResult)
                    JersildResultViewModel::class.java -> JersildResultViewModel(formResult as JersildResult)
                    AlarmScaleResultViewModel::class.java -> AlarmScaleResultViewModel(formResult as AlarmScaleResult)
                    else -> throw IllegalAccessException("Unknown ResultViewModel class")
                } as T
            }

            throw IllegalAccessException("Unknown ViewModel class")
        }
    }
}
