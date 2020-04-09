package com.psycach_ktl.parcels

import android.os.Parcelable
import com.psycach_ktl.enums.MethodologyTypes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FormParcel(
    val methodologyType: MethodologyTypes,
    val items: List<FormItemParcel>
) : Parcelable
