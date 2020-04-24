package com.psycach_ktl.builders

import android.graphics.Color
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar

class SnackbarBuilder(private val view: ViewGroup) {
    fun error(error: Int, length: Int = Snackbar.LENGTH_LONG, color: Int = Color.RED ) {
        val snack = build(error, length)
        snack.view.setBackgroundColor(color)

        snack.show()
    }

    fun success(message: Int,
                length: Int = Snackbar.LENGTH_SHORT,
                color: Int = Color.GREEN,
                textColor: Int = Color.BLACK) {
        val snack = build(message, length)
        snack.view.setBackgroundColor(color)
        snack.setTextColor(textColor)

        snack.show()
    }

    private fun build(message: Int, length: Int) = Snackbar.make(view, message, length)
}