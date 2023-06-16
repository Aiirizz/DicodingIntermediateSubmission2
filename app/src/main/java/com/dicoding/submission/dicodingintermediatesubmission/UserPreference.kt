package com.dicoding.submission.dicodingintermediatesubmission

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference (context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    var authToken: String?
        get() = sharedPreferences.getString(TOKEN, null)
        set(value) = sharedPreferences.edit { putString(TOKEN, value) }

    fun setToken(token: String) {
        val edit = sharedPreferences.edit()
        edit.putString(TOKEN, token)
        edit.apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN, null)
    }

    fun clearToken() {
        val edit = sharedPreferences.edit().clear()
        edit.apply()
    }

    companion object {
        const val PREFS = "userPreferences"
        const val TOKEN = "token"

    }
}