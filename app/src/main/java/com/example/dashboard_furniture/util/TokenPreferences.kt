package com.example.dashboard_furniture.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TokenPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val ACCES_TOKEN = stringPreferencesKey("acces_token")
    private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    private val PRICE = doublePreferencesKey("price")

    val accesToken: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[ACCES_TOKEN] ?: ""
        }


    val refreshToken: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[REFRESH_TOKEN] ?: ""
        }

    val pricePrediction: Flow<Double?>
        get() = dataStore.data.map { preferences ->
            preferences[PRICE] ?: null
        }

    suspend fun saveTokenValidation(accesToken: String, refreshToken: String) {
        dataStore.edit { preferences ->
            preferences[ACCES_TOKEN] = accesToken
            preferences[REFRESH_TOKEN] = refreshToken
        }
    }

    suspend fun savePricePrediction(price: Double) {
        dataStore.edit { preferences ->
            preferences[PRICE] = price
        }
    }

    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
    suspend fun clearPrice() {
        dataStore.edit { preferences ->
            preferences.remove(PRICE)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: TokenPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): TokenPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = TokenPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}