package com.example.maatsupervisor.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.themeStore by preferencesDataStore("theme_store")

class ThemeSession(private val context: Context) {

    companion object {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
    }

    suspend fun isDark(): Boolean {
        val prefs = context.themeStore.data.first()
        return prefs[DARK_MODE] ?: false
    }

    suspend fun setDark(value: Boolean) {
        context.themeStore.edit {
            it[DARK_MODE] = value
        }
    }
}
