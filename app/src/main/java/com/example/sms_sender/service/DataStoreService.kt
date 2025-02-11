package com.example.sms_sender.service

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings");

class DataStoreService(context: Context)
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

    suspend fun saveBoolean(key: String, value: Boolean){
        dataStore.edit {
            it[booleanPreferencesKey(key)] = value
        }
    }

    suspend fun getBoolean(key: String): Boolean? {
        return dataStore.data.map {
            it[booleanPreferencesKey(key)]
        }.first()
    }
}