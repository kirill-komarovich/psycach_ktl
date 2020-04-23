package com.psycach_ktl.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LoaderViewModel(loading: Boolean) : ViewModel() {
    private val _isLoading = MutableLiveData(loading)
    val isLoading: LiveData<Boolean>
        get() = _isLoading


    fun start() {
        _isLoading.value = true
    }

    fun stop() {
        _isLoading.value = false
    }

    class Factory(private val loading: Boolean) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoaderViewModel::class.java)) {
                return LoaderViewModel(loading) as T
            }

            throw IllegalAccessException("Unknown ViewModel class")
        }
    }
}