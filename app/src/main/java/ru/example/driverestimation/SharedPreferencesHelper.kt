package ru.example.driverestimation

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class SharedPreferencesHelper(context: Context) {
    private val mSharedPreferences: SharedPreferences
    private val mGson = Gson()
    val users: MutableList<User>
        get() {
            val users = mGson.fromJson<List<User>>(
                mSharedPreferences.getString(
                    USERS_KEY,
                    ""
                ), USERS_TYPE
            )
            return users as MutableList<User>? ?: ArrayList<User>()
        }

    fun addUser(user: User): Boolean {
        val users = users
        for (u in users) {
            if (u.id === user.id) {
                users.remove(u)
                break
            }
        }
        users.add(user)
        mSharedPreferences.edit().putString(
            USERS_KEY,
            mGson.toJson(users, USERS_TYPE)
        ).apply()
        return true
    }

    fun getUser(userId: Long): User? {
        val users: List<User> = users
        for (u in users) {
            if (u.id == userId) {
                return u
            }
        }
        return null
    }

    companion object {
        const val SHARED_PREF_NAME = "SHARED_PREF_NAME"
        const val USERS_KEY = "USERS_KEY"
        val USERS_TYPE =
            object : TypeToken<List<User?>?>() {}.type
    }

    init {
        mSharedPreferences = context.getSharedPreferences(
            SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
    }
}