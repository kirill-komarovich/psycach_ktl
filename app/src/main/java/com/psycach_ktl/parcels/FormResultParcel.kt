package com.psycach_ktl.parcels

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.psycach_ktl.enums.MethodologyTypes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FormResultParcel(
    val id: String,
    val methodologyType: MethodologyTypes? = null,
    var userId: String,
    var createdAt: Timestamp
) : Parcelable