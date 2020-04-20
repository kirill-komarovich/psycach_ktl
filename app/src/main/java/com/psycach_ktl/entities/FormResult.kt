package com.psycach_ktl.entities

import com.psycach_ktl.entities.results.*
import com.psycach_ktl.enums.MethodologyTypes
import com.psycach_ktl.parcels.FormParcel

open class FormResult(
    val methodologyType: MethodologyTypes,
    open val items: List<FormResultItem>,
    var userId: String? = null
) {
    protected open fun calculateGroup(groupIds: List<Int>, offset: Int = 0): Float {
        val groupItems = items.filter { groupIds.contains(it.id) }
        val values = groupItems.map {
            formResultItem -> answerToValue(formResultItem.value, formResultItem.id) + offset
        }
        return values.sum().toFloat()
    }

    protected open fun answerToValue(value: Int, id: Int): Int  = value

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "methodologyType" to methodologyType,
            "userId" to userId,
            "answers" to items.map { it.id.toString() to it.value }.toMap()
        )
    }

    companion object {
        fun from(formParcel: FormParcel) : FormResult {
            val items = formParcel.items.map { FormResultItem(it.id, it.value) }

            return when(formParcel.methodologyType) {
                MethodologyTypes.SAN -> SanResult(items)
                MethodologyTypes.MENTAL_STATES -> MentalStatesResult(items)
                MethodologyTypes.JERSILD -> JersildResult(items)
                MethodologyTypes.ALARM_SCALE -> AlarmScaleResult(items)
                else -> throw IllegalArgumentException("Unknown FormResult for ${formParcel.methodologyType} methodology")
            }
        }

    }
}
