package ru.example.driverestimation.utils

import android.content.Context
import android.widget.Toast

fun showMsg(context: Context, text: String) {
    Toast.makeText(
        context,
        text,
        Toast.LENGTH_SHORT
    ).show()
}