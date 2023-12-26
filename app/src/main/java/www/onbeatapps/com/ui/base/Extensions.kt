package www.onbeatapps.com.ui.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Created by PKB on 09-06-2021.
 * PKB@gmail.com
 */

fun Context.toast(content: String, isLong: Boolean = false) =
    Toast.makeText(this, content, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()

fun Context.toast(content: Int, isLong: Boolean = false) =
    Toast.makeText(this, content, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()

fun Fragment.toast(content: String, isLong: Boolean = false) =
    requireActivity().toast(content, isLong)

fun Fragment.toast(content: Int, isLong: Boolean = false) = requireActivity().toast(content, isLong)

val <T> T.exhaustive: T
    get() = this

/**
 * Kotlin Extensions for simpler, easier and funw way
 * of launching of Activities
 */

inline fun <reified T : Any> Activity.launchActivity(
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivityForResult(intent, requestCode, options)
    } else {
        startActivityForResult(intent, requestCode)
    }
}

inline fun <reified T : Any> Context.launchActivity(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivity(intent, options)
    } else {
        startActivity(intent)
    }
}

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)

val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
val Int.toDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()