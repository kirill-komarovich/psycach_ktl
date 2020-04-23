package com.psycach_ktl.builders

import android.content.Context
import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.psycach_ktl.R

class DialogBuilder(private val context: Context) {

    private val resources = context.resources

    fun buildUpgradeAccountDialog(onPositive: (dialog: DialogInterface, which: Int) -> Unit) {
        MaterialAlertDialogBuilder(context)
            .setTitle(resources.getString(R.string.upgrade_account_dialog_title))
            .setMessage(resources.getString(R.string.upgrade_account_dialog_message))
            .setNeutralButton(resources.getString(R.string.upgrade_account_dialog_cancel)) { _, _ -> }
            .setPositiveButton(resources.getString(R.string.upgrade_account_dialog_accept)) { dialog, which -> onPositive(dialog, which) }
            .show()
    }
}