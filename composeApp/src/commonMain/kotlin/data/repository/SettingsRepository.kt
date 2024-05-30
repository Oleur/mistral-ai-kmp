package data.repository

import data.datasource.SettingsDataSource

class SettingsRepository(
    private val settingsDataSource: SettingsDataSource,
) {

    fun setApiKey(apiKey: String) {
        settingsDataSource.setApiKey(apiKey)
    }

    fun getApiKey(): String = settingsDataSource.getApiKey()
}
