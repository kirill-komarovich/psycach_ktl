package com.psycach_ktl.entities.results

import com.psycach_ktl.entities.FormResult
import com.psycach_ktl.entities.FormResultItem
import com.psycach_ktl.enums.MethodologyTypes
import com.psycach_ktl.enums.ResultLevels

data class SanResult(override val items: List<FormResultItem>) : FormResult(MethodologyTypes.SAN, items) {
    val health: Float = calculateGroup(HEALTH_IDS)
    val activity: Float = calculateGroup(ACTIVITY_IDS)
    val mood: Float = calculateGroup(MOOD_IDS)

    fun groupLevel(value: Float): ResultLevels {
        return when {
            value >= 7 -> ResultLevels.GOOD
            value >= 4 -> ResultLevels.AVERAGE
            else -> ResultLevels.LOW
        }
    }

    private fun calculateGroup(groupIds: List<Int>): Float {
        val groupItems = items.filter { groupIds.contains(it.id) }
        val values = groupItems.map { formResultItem -> formResultItem.value + 4 }
        return values.sum().toFloat() / groupIds.size
    }

    companion object {
        private val HEALTH_IDS = listOf(0, 1, 6, 7, 12, 13, 18, 19, 24, 25)
        private val ACTIVITY_IDS = listOf(2, 3, 8, 9, 14, 15, 20, 22, 26, 27)
        private val MOOD_IDS = listOf(4, 5, 10, 11, 16, 17, 22, 23, 28, 29)
    }
}