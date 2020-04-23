package com.psycach_ktl.builders

import android.graphics.Color
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.psycach_ktl.R

class SnackbarBuilder(private val view: ViewGroup) {
    fun error(error: Int, length: Int = Snackbar.LENGTH_LONG, color: Int = Color.RED ) {
        val snack = Snackbar.make(view, error, length)
        snack.view.setBackgroundColor(color)

        snack.show()
    }
}