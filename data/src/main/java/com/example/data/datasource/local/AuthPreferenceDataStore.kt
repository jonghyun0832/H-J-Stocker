package com.example.data.datasource.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

private const val AUTH_DATASTORE_NAME = "auth_prefs"

val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = AUTH_DATASTORE_NAME)