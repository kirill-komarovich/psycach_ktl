package com.psycach_ktl.entities

import com.psycach_ktl.enums.MethodologyTypes

data class Methodology(val type: MethodologyTypes) {
    companion object Factory {
        var supportedMethodologies = MethodologyTypes.values().map { type ->
            Methodology(type = type)
        }
    }
}