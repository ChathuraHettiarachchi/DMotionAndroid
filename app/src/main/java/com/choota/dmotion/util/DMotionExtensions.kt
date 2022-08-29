package com.choota.dmotion.util

/**
 * resolve will replace null with replace value.
 * default will be empty string
 */
fun String?.resolve(replace: String = ""): String = this ?: replace

/**
 * resolve will replace null with replace value.
 * default will be empty string
 */
fun Int?.resolve(replace: Int = -1): Int = this ?: replace