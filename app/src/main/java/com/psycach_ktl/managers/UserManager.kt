package com.psycach_ktl.managers

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.psycach_ktl.entities.UserProfile

object UserManager {
    val currentUser: FirebaseUser?
        get() = FirebaseAuth.getInstance().currentUser
    var currentUserProfile: UserProfile? = null
}