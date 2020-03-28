package com.psycach_ktl.services

import android.content.Context
import android.content.res.Resources

class I18n {
    companion object {
        fun translate(context: Context, key: String): String {
            val res: Resources = context.resources

            return res.getString(res.getIdentifier(key, "string",  context.packageName))
        }
    }
}