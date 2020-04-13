package com.psycach_ktl.entities.results

import com.psycach_ktl.entities.FormResult
import com.psycach_ktl.entities.FormResultItem
import com.psycach_ktl.enums.MethodologyTypes
import com.psycach_ktl.enums.ResultLevels

data class SanResult(override val items: List<FormResultItem>) : FormResult(MethodologyTypes.SAN, items) {
    val health: Float = calculateGroup(HEALTH_IDS, ANSWER_OFFSET)
    val activity: Float = calculateGroup(ACTIVITY_IDS, ANSWER_OFFSET)
    val mood: Float = calculateGroup(MOOD_IDS, ANSWER_OFFSET)

    fun groupLevel(value: Float): ResultLevels {
        return when {
            value >= 7 -> ResultLevels.GOOD
            value >= 4 -> ResultLevels.AVERAGE
            else -> ResultLevels.LOW
        }
    }

    override fun calculateGroup(groupIds: List<Int>, offset: Int): Float {
        val updatedGroupIds = groupIds.map { id ->
            if(INVERTED_IDS.indexOf(id) == -1) id else id * -1
        } // TODO: fix
        return super.calculateGroup(updatedGroupIds, offset) / groupIds.size
    }

    companion object {
        private const val ANSWER_OFFSET = 4
        private val HEALTH_IDS = listOf(0, 1, 6, 7, 12, 13, 18, 19, 24, 25)
        private val ACTIVITY_IDS = listOf(2, 3, 8, 9, 14, 15, 20, 22, 26, 27)
        private val MOOD_IDS = listOf(4, 5, 10, 11, 16, 17, 22, 23, 28, 29)
        private val INVERTED_IDS = listOf(0, 1, 4, 5, 6, 7, 10, 11, 13, 16, 17, 18, 19, 23, 24, 25, 28, 29)
    }
}
