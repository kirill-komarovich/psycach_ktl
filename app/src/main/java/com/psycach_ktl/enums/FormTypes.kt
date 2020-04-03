package com.psycach_ktl.enums

enum class FormTypes {
    SLIDER,
    RADIO_BUTTON;

    companion object {
        fun forMethodology(type: MethodologyTypes): FormTypes {
            return when(type) {
                MethodologyTypes.SAN -> SLIDER
                MethodologyTypes.JERSILD,
                    MethodologyTypes.ALARM_SCALE,
                    MethodologyTypes.MENTAL_STATES -> RADIO_BUTTON
            }
        }
    }
}
