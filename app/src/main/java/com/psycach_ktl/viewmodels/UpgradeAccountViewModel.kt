package com.psycach_ktl.viewmodels

import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.psycach_ktl.enums.UserRoles
import com.psycach_ktl.repositories.UserRepository
import kotlinx.coroutines.launch

class UpgradeAccountViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val userRepository = UserRepository()

    fun updateRole(
        role: UserRoles,
        onUpgradedListener: (isUpgraded: Boolean) -> Unit
    ) {
        viewModelScope.launch {
            userRepository.updateUserRole(auth.currentUser!!.uid, role)
            onUpgradedListener(true)
        }
    }
}