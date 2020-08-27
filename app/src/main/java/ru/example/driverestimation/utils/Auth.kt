package ru.example.driverestimation.utils

import android.content.Context
import android.content.SharedPreferences

class Auth(context: Context) {

    companion object {
        val USER_EMAIL_KEY: String = "USER_EMAIL_KEY"
    }

    private val sharedPrefUserId: SharedPreferences =
        context.getSharedPreferences(USER_EMAIL_KEY, Context.MODE_PRIVATE)

    fun addUser(email: String) {
        sharedPrefUserId.edit().putString(USER_EMAIL_KEY, email).apply()
    }

    fun isAuthed() =
        sharedPrefUserId.getString(USER_EMAIL_KEY, "") != ""

    fun deleteUser() {
        sharedPrefUserId.edit().putString(USER_EMAIL_KEY, "").apply()
    }
}