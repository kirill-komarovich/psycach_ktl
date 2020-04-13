package com.psycach_ktl.enums

import java.util.*

enum class ResultLevels {
    HIGH,
    GOOD,
    AVERAGE,
    LOW;

    fun toLowerCase(): String {
        return name.toLowerCase(Locale.ROOT)
    }
}