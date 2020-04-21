package com.psycach_ktl.viewmodels

import androidx.lifecycle.*
import com.psycach_ktl.entities.FormResult
import com.psycach_ktl.repositories.FormResultRepository
import com.psycach_ktl.viewmodels.result.*
import kotlinx.coroutines.launch

abstract class ResultViewModel(result: FormResult) : ViewModel() {
    private val formResultRepository = FormResultRepository()
    private var _formResult = MutableLiveData<FormResult>()
    val formResult: LiveData<FormResult>
        get() = _formResult

    init {
        _formResult.value = result
    }

    protected open fun calculateGroup(groupIds: List<String>, offset: Int = 0): Float {
        val groupItems = formResult.value!!.items.filter { groupIds.contains(it.id) }
        val values = groupItems.map {
                formResultItem -> answerToValue(formResultItem.value, formResultItem.id) + offset
        }
        return values.sum().toFloat()
    }

    protected open fun answerToValue(value: Int, id: String): Int  = value

    fun saveResult(userId: String) {
        viewModelScope.launch {
            val result = formResult.value!!
            result.userId = userId

            formResultRepository.save(result)
        }
    }
    class Factory(private val result: FormResult) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (ResultViewModel::class.java.isAssignableFrom(modelClass)) {
                return when (modelClass) {
                    SanResultViewModel::class.java -> SanResultViewModel(result)
                    MentalStatesResultViewModel::class.java -> MentalStatesResultViewModel(result)
                    JersildResultViewModel::class.java -> JersildResultViewModel(result)
                    AlarmScaleResultViewModel::class.java -> AlarmScaleResultViewModel(result)
                    else -> throw IllegalAccessException("Unknown ResultViewModel class")
                } as T
            }

            throw IllegalAccessException("Unknown ViewModel class")
        }
    }
}
