package com.psycach_ktl.managers

import com.psycach_ktl.enums.UserRoles

object AuthorizationManager {
    fun isAuthenticated() = UserManager.currentUser != null

    fun isPsychologist(): Boolean {
        return UserManager.currentUserProfile?.let {
            it.role == UserRoles.PSYCOLOGIST
        } ?: false
    }
}