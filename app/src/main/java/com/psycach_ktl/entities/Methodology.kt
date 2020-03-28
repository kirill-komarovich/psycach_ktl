package com.psycach_ktl.entities

data class Methodology(val type: String) {
    companion object Factory {
        var supportedMethodologies = listOf(
            Methodology(type = "san"),
            Methodology(type = "mental_states"),
            Methodology(type = "jersild"),
            Methodology(type = "alarm_scale")
        )
    }
}