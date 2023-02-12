package com.lira.cinetime.core

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lira.cinetime.R

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

fun genresIDsToNamesResources(id: Int): Int {

    return when (id) {
        28 -> R.string.action
        12 -> R.string.adventure
        16 -> R.string.animation
        35 -> R.string.comedy
        80 -> R.string.crime
        99 -> R.string.documentary
        18 -> R.string.drama
        10751 -> R.string.family
        14 -> R.string.fantasy
        36 -> R.string.history
        27 -> R.string.horror
        10402 -> R.string.music
        9648 -> R.string.mystery
        10749 -> R.string.romance
        878 -> R.string.science_fiction
        10770 -> R.string.tv_movie
        53 -> R.string.thriller
        10752 -> R.string.war
        37 -> R.string.western
        else -> R.string.blank
    }
}