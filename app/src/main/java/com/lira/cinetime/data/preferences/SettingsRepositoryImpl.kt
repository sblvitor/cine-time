package com.lira.cinetime.data.preferences

import android.content.Context
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException

private val Context.dataStore by preferencesDataStore(name = "settings_pref")

class SettingsRepositoryImpl(private val context: Context): SettingsRepository {

    override val uiModeFlow: Flow<UiMode> = context.dataStore.data
        .catch {
            if(it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->

            when(preferences[IS_DARK_MODE] ?: false) {
                true -> UiMode.DARK
                false -> UiMode.LIGHT
            }
        }

    override suspend fun setUiMode(uiMode: UiMode) {
        context.dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = when(uiMode) {
                UiMode.LIGHT -> false
                UiMode.DARK -> true
            }
        }
    }

    companion object {
        val IS_DARK_MODE = booleanPreferencesKey("dark_mode")
    }

}