package com.choota.dmotion.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
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

/**
 * Launch activity
 */
inline fun <reified T : Any> Activity.launchActivity (
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {})
{
    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
    {
        startActivityForResult(intent, requestCode, options)
    } else {
        startActivityForResult(intent, requestCode)
    }
}

/**
 * Launch activity
 */
inline fun <reified T : Any> Context.launchActivity (
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {})
{
    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
    {
        startActivity(intent, options)
    } else {
        startActivity(intent)
    }
}

/**
 * Launch activity
 */
inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)