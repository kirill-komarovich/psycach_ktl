package com.psycach_ktl.enums

import java.util.*

enum class MethodologyTypes {
    SAN,
    MENTAL_STATES,
    JERSILD,
    ALARM_SCALE;

    fun toLowerCase(): String {
        return name.toLowerCase(Locale.ROOT)
    }
}