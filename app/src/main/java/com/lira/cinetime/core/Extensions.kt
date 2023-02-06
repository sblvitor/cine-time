package com.lira.cinetime.core

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.lira.cinetime.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Fragment.createDialog(block: MaterialAlertDialogBuilder.() -> Unit = {}): androidx.appcompat.app.AlertDialog {
    val builder = activity?.let { MaterialAlertDialogBuilder(it) }
    builder!!.setPositiveButton(android.R.string.ok, null)
    block(builder)
    return builder.create()
}

fun Fragment.createProgressDialog(): AlertDialog {
    return createDialog {
        val padding = this@createProgressDialog.resources.getDimensionPixelOffset(R.dimen.margin_default)
        val progressBar = ProgressBar(activity)
        progressBar.setPadding(padding, padding, padding, padding)
        setView(progressBar)

        setPositiveButton(null, null)
        setCancelable(false)
    }
}

fun View.hideSoftKeyboard() {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}