package com.psycach_ktl.entities.results

import com.psycach_ktl.entities.FormResult
import com.psycach_ktl.entities.FormResultItem
import com.psycach_ktl.enums.MethodologyTypes
import com.psycach_ktl.enums.ResultLevels

data class MentalStatesResult(override val items: List<FormResultItem>) : FormResult(MethodologyTypes.MENTAL_STATES, items) {
    val anxiety: Float = calculateGroup(ANXIETY_IDS)
    val frustration: Float = calculateGroup(FRUSTRATION_IDS)
    val aggressiveness: Float = calculateGroup(AGGRESSIVENESS_IDS)
    val rigidity: Float = calculateGroup(RIGIDITY_IDS)

    fun groupLevel(value: Float): ResultLevels {
        return when {
            value >= 15 -> ResultLevels.HIGH
            value >= 8 -> ResultLevels.AVERAGE
            else -> ResultLevels.LOW
        }
    }

    companion object {
        private val ANXIETY_IDS = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
        private val FRUSTRATION_IDS = listOf(10, 11, 12, 13, 14, 15, 16, 17, 18, 19)
        private val AGGRESSIVENESS_IDS = listOf(20, 21, 22, 23, 24, 25, 26, 27, 28, 29)
        private val RIGIDITY_IDS = listOf(30, 31, 32, 33, 34, 35, 36, 37, 38, 39)
    }
}