package com.psycach_ktl.enums

import java.util.*

enum class MethodologyTypes {
    SAN {
        override fun getQuestionsCount() = 30
    },
    MENTAL_STATES {
        override fun getQuestionsCount() = 40
    },
    JERSILD {
        override fun getQuestionsCount() = 36
    },
    ALARM_SCALE {
        override fun getQuestionsCount() = 1
    };

    abstract fun getQuestionsCount(): Int

    fun toLowerCase(): String {
        return name.toLowerCase(Locale.ROOT)
    }
}