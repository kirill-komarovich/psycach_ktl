package com.psycach_ktl.enums

import java.util.*

enum class ResultLevels {
    GOOD,
    AVERAGE,
    LOW;

    // TODO: find better way to i18n
    val feminineResourceName = "${toLowerCase()}_level_feminine"
    val neuterResourceName = "${toLowerCase()}_level_neuter"

    fun toLowerCase(): String {
        return name.toLowerCase(Locale.ROOT)
    }
}