package com.psycach_ktl.parcels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FormItemParcel(val id: String, val value: Int) : Parcelable