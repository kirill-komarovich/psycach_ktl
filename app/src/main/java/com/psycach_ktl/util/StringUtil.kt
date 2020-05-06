package com.psycach_ktl.util

val CAMEL_CASE_REGEX = "(?<=[a-zA-Z])[A-Z]".toRegex()

fun String.camelToSnakeCase(): String {
    return CAMEL_CASE_REGEX.replace(this) { "_${it.value}" }.toLowerCase()
}