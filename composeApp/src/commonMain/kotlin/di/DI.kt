package di

import data.datasource.ModelCacheDataSource
import data.datasource.SettingsDataSource
import data.repository.MistralRepository
import data.repository.SettingsRepository
import domain.MistralUseCase
import ui.MistralViewModel

// DataSource
val settingsDataSource = SettingsDataSource()
val modelCacheDataSource = ModelCacheDataSource()

// Repository
var mistralRepository = MistralRepository(modelCacheDataSource, settingsDataSource.getApiKey())
val settingsRepository = SettingsRepository(settingsDataSource)

// UseCase
val mistralUseCase = MistralUseCase(mistralRepository, settingsRepository)

// ViewModel
val mistralViewModel = MistralViewModel(mistralUseCase)
