package ru.example.driverestimation.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.example.driverestimation.model.User
import java.util.*

class UserController(context: Context) {

    companion object {
        const val SHARED_PREF_NAME = "SHARED_PREF_NAME"
        const val USERS_KEY = "USERS_KEY"
        val USERS_TYPE =
            object : TypeToken<List<User?>?>() {}.type
    }

    private val mSharedPreferences: SharedPreferences

    init {
        mSharedPreferences = context.getSharedPreferences(
            SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

    private val mGson = Gson()

    val users: MutableList<User>
        get() {
            val users = mGson.fromJson<List<User>>(
                mSharedPreferences.getString(
                    USERS_KEY,
                    ""
                ),
                USERS_TYPE
            )
            return users as MutableList<User>? ?: ArrayList()
        }

    fun addUser(user: User): Boolean {
        val users = users
        for (u in users) {
            if (u.email.equals(user.email, true)) {
                users.remove(u)
                break
            }
        }
        users.add(user)
        mSharedPreferences.edit().putString(
            USERS_KEY,
            mGson.toJson(
                users,
                USERS_TYPE
            )
        ).apply()
        return true
    }

    fun getUser(userEmail: String): User? {
        val users: List<User> = users
        for (u in users) {
            if (u.email.equals(userEmail, true)) {
                return u
            }
        }
        return null
    }

    fun login(userEmail: String): User? {
        val users: List<User> = users
        for (u in users) {
            if (u.email.equals(userEmail, true)) {
                return u
            }
        }
        return null
    }
}