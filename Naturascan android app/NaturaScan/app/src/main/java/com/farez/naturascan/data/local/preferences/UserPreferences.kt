package com.farez.naturascan.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences (private val dataStore: DataStore <Preferences>) {
    private val token = stringPreferencesKey("TOKEN")
    private val auth = booleanPreferencesKey("AUTH")

    fun getToken() : Flow<String> {
        return dataStore.data.map {
            it[token] ?: "null"
        }
    }

    suspend fun saveToken(token: String) {
        dataStore.edit {
            it [this.token] = token
        }
    }

    suspend fun delToken() {
        dataStore.edit {
            it[token] = "null"
        }
    }

    suspend fun setAuth(auth : Boolean) {
        dataStore.edit {
            it[this.auth] = auth
        }
    }

    fun getAuth(): Flow<Boolean> {
        return dataStore.data.map {
            it[auth] ?: false
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null
        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}