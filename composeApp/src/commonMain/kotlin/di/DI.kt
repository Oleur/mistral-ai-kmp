package di

import ui.MistralViewModel
import domain.MistralUseCase
import org.mistral.ai.kmp.MistralClient
import org.mistral.ai.kmp.BuildKonfig
import data.repository.MistralRepository
import data.datasource.ModelCacheDataSource

// DataSource
val mistralClient = MistralClient(BuildKonfig.MISTRAL_API_KEY)
val modelCacheDataSource = ModelCacheDataSource()

// Repository
val mistralRepository = MistralRepository(mistralClient, modelCacheDataSource)

// UseCase
val mistralUseCase = MistralUseCase(mistralRepository)

// ViewModel
val mistralViewModel = MistralViewModel(mistralUseCase)
