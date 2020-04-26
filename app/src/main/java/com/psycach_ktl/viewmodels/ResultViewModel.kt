package com.psycach_ktl.viewmodels

import androidx.lifecycle.*
import com.psycach_ktl.entities.FormResult
import com.psycach_ktl.managers.UserManager
import com.psycach_ktl.repositories.FormResultRepository
import kotlinx.coroutines.launch

class ResultViewModel(formResult: FormResult) : ViewModel() {
    private val formResultRepository = FormResultRepository()

    private var _result = MutableLiveData(formResult)
    val result: LiveData<FormResult>
        get() = _result

    private var _isReady = MutableLiveData(formResult.items.isNotEmpty())
    val isReady: LiveData<Boolean>
        get() = _isReady

    fun processResult() {
        val user = UserManager.currentUser ?: return

        if (result.value!!.isNewRecord()) {
            saveResult(user.uid)
        } else {
            loadResultItems()
        }
    }

    private fun loadResultItems() {
        viewModelScope.launch {
            val items = formResultRepository.itemsFor(result.value!!.id)

            result.value!!.items = items

            _isReady.value = true
        }
    }

    private fun saveResult(userId: String) {
        viewModelScope.launch {
            val result = result.value!!
            result.userId = userId

            formResultRepository.save(result)
        }
    }

    class Factory(val formResult: FormResult) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {

                return ResultViewModel(formResult) as T
            }

            throw IllegalAccessException("Unknown ViewModel class")
        }
    }
}