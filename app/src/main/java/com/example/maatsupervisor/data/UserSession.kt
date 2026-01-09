package com.example.maatsupervisor.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore("user_session")

class UserSession(private val context: Context) {

    companion object {
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val GID = stringPreferencesKey("gid")
        val NAME = stringPreferencesKey("name")
        val DESIGNATION = stringPreferencesKey("designation")
    }

    /* ---------------- SAVE LOGIN ---------------- */

    suspend fun saveLogin(
        gid: String,
        name: String,
        designation: String
    ) {
        context.dataStore.edit {
            it[IS_LOGGED_IN] = true
            it[GID] = gid
            it[NAME] = name
            it[DESIGNATION] = designation
        }
    }

    /* ---------------- GETTERS ---------------- */

    suspend fun isLoggedIn(): Boolean =
        context.dataStore.data.first()[IS_LOGGED_IN] ?: false

    suspend fun getGid(): String =
        context.dataStore.data.first()[GID] ?: ""

    suspend fun getUserName(): String =
        context.dataStore.data.first()[NAME] ?: ""

    suspend fun getDesignation(): String =
        context.dataStore.data.first()[DESIGNATION] ?: ""

    /* ---------------- LOGOUT ---------------- */

    suspend fun logout() {
        context.dataStore.edit { it.clear() }
    }
}
