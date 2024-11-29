package com.example.sms_sender.service

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.sms_sender.service.setting.SettingKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings");

class DataStoreService(private val context: Context)
{
    private val dataStore = context.dataStore;

    suspend fun saveString(key: String, value: String) {
        dataStore.edit {
            it[stringPreferencesKey(key)] = value
        }
    }

    suspend fun getString(key: String): String? {
        return dataStore.data.map {
            it[stringPreferencesKey(key)]
        }.first()
    }

    suspend fun getInt(key: String): Int? {
        return dataStore.data.map {
            it[intPreferencesKey(key)]
        }.first()
    }

    suspend fun saveInt(key: String, value: Int) {
        dataStore.edit {
            it[intPreferencesKey(key)] = value
        }
    }

    suspend fun getSettingData(): HashMap<String, String>{
        return HashMap<String, String>().apply {
            set(SettingKey.COUNTRY_KEY, getString(SettingKey.COUNTRY_KEY) ?: "")
            set(SettingKey.API_URL_KEY, getString(SettingKey.API_URL_KEY) ?: "")
        }
    }
}