package data.datasource

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import org.mistral.ai.kmp.BuildKonfig

class SettingsDataSource(private val settings: Settings = Settings()) {

    fun setApiKey(apiKey: String) {
        settings[KEY_API] = apiKey
    }

    fun getApiKey(): String {
        return settings.getString(KEY_API, BuildKonfig.MISTRAL_API_KEY)
    }

    private companion object {
        const val KEY_API = "key_api"
    }
}
