package com.psycach_ktl.viewmodels

import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.psycach_ktl.enums.UserRoles
import com.psycach_ktl.repositories.UserRepository
import kotlinx.coroutines.launch

class UpgradeAccountViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val userRepository = UserRepository()

    private val _isUpgraded = MutableLiveData(false)
    val isUpgraded: LiveData<Boolean>
        get() = _isUpgraded

    fun updateRole() {
        viewModelScope.launch {
            userRepository.updateUserRole(auth.currentUser!!.uid, UserRoles.PSYCOLOGIST)
            _isUpgraded.value = true
        }
    }
}