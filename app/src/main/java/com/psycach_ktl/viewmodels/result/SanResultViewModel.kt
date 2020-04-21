package com.psycach_ktl.viewmodels.result

import com.psycach_ktl.entities.FormResult
import com.psycach_ktl.enums.ResultLevels
import com.psycach_ktl.viewmodels.ResultViewModel

class SanResultViewModel(result: FormResult) : ResultViewModel(result) {
    val health: Float = calculateGroup(HEALTH_IDS, ANSWER_OFFSET)
    val activity: Float = calculateGroup(ACTIVITY_IDS, ANSWER_OFFSET)
    val mood: Float = calculateGroup(MOOD_IDS, ANSWER_OFFSET)

    val healthLevel: ResultLevels
        get() = groupLevel(health)

    val activityLevel: ResultLevels
        get() = groupLevel(activity)

    val moodLevel: ResultLevels
        get() = groupLevel(mood)

    override fun calculateGroup(groupIds: List<String>, offset: Int): Float {
        return super.calculateGroup(groupIds, offset) / groupIds.size
    }

    override fun answerToValue(value: Int, id: String): Int {
        return when {
            INVERTED_IDS.contains(id) -> -value
            else -> value
        }
    }

    private fun groupLevel(value: Float): ResultLevels {
        return when {
            value >= 7 -> ResultLevels.GOOD
            value >= 4 -> ResultLevels.AVERAGE
            else -> ResultLevels.LOW
        }
    }

    companion object {
        private const val ANSWER_OFFSET = 4
        private val HEALTH_IDS = listOf("0", "1", "6", "7", "12", "13", "18", "19", "24", "25")
        private val ACTIVITY_IDS = listOf("2", "3", "8", "9", "14", "15", "20", "22", "26", "27")
        private val MOOD_IDS = listOf("4", "5", "10", "11", "16", "17", "22", "23", "28", "29")
        private val INVERTED_IDS = listOf("0", "1", "4", "5", "6", "7", "10", "11", "13", "16", "17", "18", "19", "23", "24", "25", "28", "29")
    }
}
