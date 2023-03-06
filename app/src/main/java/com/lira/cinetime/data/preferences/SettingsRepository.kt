package com.lira.cinetime.data.preferences

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val uiModeFlow: Flow<UiMode>
    suspend fun setUiMode(uiMode: UiMode)

}