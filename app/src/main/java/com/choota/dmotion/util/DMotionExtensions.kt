package com.choota.dmotion.util

import android.app.Activity
import android.content.ClipData.newIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.View
import android.widget.Toast
import java.util.*


/**
 * resolve will replace null with replace value.
 * default will be empty string
 */
fun String?.resolve(replace: String = ""): String = this ?: replace

fun String?.resolveHtml(): Spanned {
    val result: Spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this.resolve(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this.resolve())
    }
    return result
}

fun String?.country(): String{
    return if(this.isNullOrEmpty())
        "N/A"
    else
        Locale("", this).displayCountry
}

fun String?.language(): String{
    return if(this.isNullOrEmpty())
        "N/A"
    else
        Locale(this, "").displayLanguage
}

/**
 * resolve will replace null with replace value.
 * default will be empty string
 */
fun Int?.resolve(replace: Int = -1): Int = this ?: replace

/**
 * convert to minutes and seconds
 */
fun Int?.toMMSS(): String {
    return if(this == null) {
        "N/A"
    }else {
        val s = this%60
        val m = this/60

        "$m:$s"
    }
}

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

fun Activity.toast(message: String) = Toast.makeText(this.applicationContext, message.resolve(), Toast.LENGTH_LONG).show()

