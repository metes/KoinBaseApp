package com.base.commons

import android.content.Context
import com.base.commons.Constants.accessToken
import com.base.commons.Constants.isLoggedInKey


class SharedPrefHelper(private val context: Context) {

    fun saveLoginState(value: Boolean) {
        context.getSharedPreferences(isLoggedInKey, Context.MODE_PRIVATE).edit().apply {
            putBoolean(isLoggedInKey, value)
            apply()
        }
    }

    fun loadLoginState(): Boolean {
        return context.getSharedPreferences(isLoggedInKey, Context.MODE_PRIVATE).getBoolean(
            isLoggedInKey, false)
    }


    fun saveToken(value: String) {
        context.getSharedPreferences(accessToken, Context.MODE_PRIVATE).edit().apply {
            putString(accessToken, value)
            apply()
        }
    }

    fun loadToken(): String? {
        return context.getSharedPreferences(accessToken, Context.MODE_PRIVATE).getString(accessToken, null)
    }


}
