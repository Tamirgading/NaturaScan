package com.farez.naturascan.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(private val dataStore: DataStore<Preferences>) {
    private val token = stringPreferencesKey("TOKEN")
    private val email = stringPreferencesKey("EMAIL")
    private val auth = booleanPreferencesKey("AUTH")

    fun getToken(): Flow<String> {
        return dataStore.data.map {
            it[token] ?: "null"
        }
    }

    fun getEmail(): Flow<String> {
        return dataStore.data.map {
            it[email] ?: "null@null.com"
        }
    }

    suspend fun saveLoginInfo(token: String, email: String) {
        dataStore.edit {
            it[this.token] = token
            it[this.email] = email
        }
    }

    suspend fun delLoginInfo() {
        dataStore.edit {
            it[token] = "null"
            it[email] = "deleted@null.com"
        }
    }

    suspend fun setAuth(auth: Boolean) {
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