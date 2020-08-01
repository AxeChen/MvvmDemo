package com.axe.libcommon.ext

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import java.lang.reflect.Method

/**
 * 这个类封装了软件盘的一些拓展方法，一般用于打开、关闭软键盘的操作
 */


/**
 * 关闭软键盘
 */
fun Activity.closeKeyBoard() {
    val view = window.peekDecorView()
    view?.run {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            windowToken,
            0
        )
    }
}

fun Activity.closeKeyBoard(view: View?) {
    view?.run {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            windowToken,
            0
        )
    }
}

/**
 * 打开软件盘
 */
fun Activity.openKeyBoard(editText: View?) {
    editText?.run {
        post {
            requestFocus()
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
                this,
                0
            )
        }
    }
}


/**
 * 判断软键盘是否显示
 *
 * @return 软键盘是否显示：true 显示;false不显示
 */
fun Activity.isKeyboardShown(): Boolean {
    val rootView = window.peekDecorView()
    val softKeyboardHeight = 100
    val r = Rect()
    rootView.getWindowVisibleDisplayFrame(r)
    val dm = rootView.resources.displayMetrics
    val heightDiff = rootView.bottom - r.bottom
    return heightDiff > softKeyboardHeight * dm.density
}


/**
 * /禁止EditText弹出软件盘，光标依然正常显示
 */
fun Context.disableShowSoftInput(editText: EditText) {
    val cls = EditText::class.java
    var method: Method
    try {
        method = cls.getMethod("setShowSoftInputOnFocus", Boolean::class.javaPrimitiveType)
        method.isAccessible = true
        method.invoke(editText, false)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}





