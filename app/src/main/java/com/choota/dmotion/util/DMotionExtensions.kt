package com.choota.dmotion.util

import android.view.View

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

/**
 * make view visible
 */
fun View.visible(){
    this.visibility = View.VISIBLE
}

/**
 * make view gone
 */
fun View.gone(){
    this.visibility = View.GONE
}