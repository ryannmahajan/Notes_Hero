package com.ryannm.noteshero.domain

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log

private const val TAG = "SharedPref"

class SharedPref private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun saveIsLoggedIn(isLoggedIn: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("IS_LOGGED_IN", isLoggedIn)
        Log.d(TAG, "Saved log in: $isLoggedIn")
        editor.apply()
    }

    val isLoggedIn: Boolean
        get() {
            return sharedPreferences.getBoolean("IS_LOGGED_IN", false).also {
                Log.d(TAG, "getting logged in: $it")
            }
        }

    companion object {
        private lateinit var instance: SharedPref

        fun init(context: Context) {
            instance = SharedPref(context.applicationContext)
        }

        @Synchronized
        fun getInstance(): SharedPref {
            return instance
        }
    }
}
