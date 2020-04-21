package com.psycach_ktl.viewmodels.result

import com.psycach_ktl.entities.FormResult
import com.psycach_ktl.viewmodels.ResultViewModel

class JersildResultViewModel(result: FormResult) : ResultViewModel(result) {

    val loneliness: Float = calculateGroup(LONELINESS_IDS)
    val meaninglessness: Float = calculateGroup(MEANINGLESSNESS_IDS)
    val freedom: Float = calculateGroup(FREEDOM_IDS)
    val sexualConflict: Float = calculateGroup(SEXUAL_CONFLICT_IDS)
    val hostileConflict: Float = calculateGroup(HOSTILE_CONFLICT_IDS)
    val discrepancy: Float = calculateGroup(DISCREPANCY_IDS)
    val strengthOfWill: Float = calculateGroup(STRENGTH_OF_WILL_IDS)
    val hopelessness: Float = calculateGroup(HOPELESSNESS_IDS)
    val homelessness: Float = calculateGroup(HOMELESSNESS_IDS)

    override fun answerToValue(value: Int, id: Int): Int {
        return when(value) {
            0 -> 0
            else -> value - 1
        }
    }

    companion object {
        private val LONELINESS_IDS = listOf(6, 14, 19, 33)
        private val MEANINGLESSNESS_IDS = listOf(3, 10, 22, 32)
        private val FREEDOM_IDS = listOf(4, 20, 23, 33)
        private val SEXUAL_CONFLICT_IDS = listOf(2, 11, 16, 28)
        private val HOSTILE_CONFLICT_IDS = listOf(7, 24, 29, 31)
        private val DISCREPANCY_IDS = listOf(1, 13, 21, 26)
        private val STRENGTH_OF_WILL_IDS = listOf(0, 8, 17, 27)
        private val HOPELESSNESS_IDS = listOf(5, 9, 25, 30)
        private val HOMELESSNESS_IDS = listOf(12, 15, 18, 35)
    }
}
