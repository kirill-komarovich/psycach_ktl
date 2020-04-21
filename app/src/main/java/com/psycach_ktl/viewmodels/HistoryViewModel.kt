package com.psycach_ktl.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.psycach_ktl.entities.FormResult
import com.psycach_ktl.repositories.FormResultRepository
import kotlinx.coroutines.launch

class HistoryViewModel(private val formResultRepository: FormResultRepository) : ViewModel() {
    private var page = 1
    private val _resultItems = MutableLiveData<List<FormResult>>()
    val resultItems: LiveData<List<FormResult>>
        get() = _resultItems

    init {
        viewModelScope.launch {
            Log.d("HistoryViewModel", "viewModelScope.launch")
            _resultItems.value = emptyList()
        }
    }

    class Factory(private val formResultRepository: FormResultRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
                return HistoryViewModel(formResultRepository) as T
            }

            throw IllegalAccessException("Unknown ViewModel class")
        }
    }

    companion object {
        private const val PER_PAGE = 15
    }
}
