package com.psycach_ktl.entities

import com.google.firebase.firestore.DocumentId
import com.psycach_ktl.enums.UserRoles

data class UserProfile(
    @DocumentId
    var id: String = "",
    var email: String = "",
    var displayName: String = "",
    var role: UserRoles? = null
)
