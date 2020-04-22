package com.psycach_ktl.viewmodels.result

import com.psycach_ktl.entities.FormResult
import com.psycach_ktl.enums.ResultLevels
import com.psycach_ktl.viewmodels.FormResultViewModel

class AlarmScaleResultViewModel(result: FormResult) : FormResultViewModel(result) {

    val situationalAnxiety: Float = calculateGroup(SITUATION_ANXIETY_IDS, RESULT_OFFSET) + SITUATIONAL_ANXIETY_OFFSET
    val personalAnxiety: Float = calculateGroup(PERSONAL_ANXIETY_IDS, RESULT_OFFSET) + PERSONAL_ANXIETY_OFFSET

    val situationalAnxietyLevel: ResultLevels
        get() = groupLevel(situationalAnxiety)

    val personalAnxietyLevel: ResultLevels
        get() = groupLevel(personalAnxiety)


    override fun answerToValue(value: Int, id: String): Int {
        return when {
            BACK_IDS.contains(id) -> -value
            else -> value
        }
    }

    private fun groupLevel(value: Float): ResultLevels {
        return when {
            value >= 45 -> ResultLevels.HIGH
            value >= 31 -> ResultLevels.AVERAGE
            else -> ResultLevels.LOW
        }
    }

    companion object {
        private const val SITUATIONAL_ANXIETY_OFFSET = 50.0F
        private const val PERSONAL_ANXIETY_OFFSET = 35.0F
        private const val RESULT_OFFSET = 1
        private val BACK_IDS = listOf("0", "1", "4", "7", "9", "10", "12", "15", "18", "19", "20", "25", "26", "29", "32", "35", "38")
        private val SITUATION_ANXIETY_IDS = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19")
        private val PERSONAL_ANXIETY_IDS = listOf("20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39")
    }
}
