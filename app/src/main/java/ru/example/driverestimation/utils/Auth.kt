package ru.example.driverestimation.utils

import android.content.Context
import android.content.SharedPreferences

class Auth(context: Context) {

    companion object {
        val USER_ID_KEY: String = "USER_ID_KEY"
    }

    private val sharedPrefUserId: SharedPreferences =
        context.getSharedPreferences(USER_ID_KEY, Context.MODE_PRIVATE)

    fun addUser(id: Long) {
        sharedPrefUserId.edit().putLong(USER_ID_KEY, id).apply()
    }

    fun isAuthed() =
        sharedPrefUserId.getLong(USER_ID_KEY, 0L) != 0L

    fun deleteUser() {
        sharedPrefUserId.edit().putLong(USER_ID_KEY, 0L).apply()
    }
}