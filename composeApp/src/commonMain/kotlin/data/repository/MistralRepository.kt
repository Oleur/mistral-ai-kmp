package data.repository

import data.datasource.ModelCacheDataSource
import kotlinx.coroutines.flow.Flow
import org.mistral.ai.kmp.MistralClient
import org.mistral.ai.kmp.domain.ModelParams
import org.mistral.ai.kmp.domain.Message
import org.mistral.ai.kmp.domain.Model

class MistralRepository(
    private val modelCacheDataSource: ModelCacheDataSource,
    apiKey: String,
) {

    private var mistralClient = MistralClient(apiKey)

    suspend fun getModels() = mistralClient.getModels()

    suspend fun chat(model: String, message: String): Result<List<Message>> {
        return mistralClient.chat(
            model = model,
            messages = listOf(Message(content = message)),
            params = ModelParams(safePrompt = false),
        )
    }

    suspend fun chatStream(model: String, message: String): Flow<Result<List<Message>>> {
        return mistralClient.chatStream(
            model = model,
            messages = listOf(Message(content = message)),
            params = ModelParams(safePrompt = false),
        )
    }

    fun getSelectedModelId(): String {
        return modelCacheDataSource.getSelectedModelId()
    }

    fun setSelectedModel(model: Model) {
        modelCacheDataSource.setSelectedModelId(model.id)
    }

    fun setApiKey(apiKey: String) {
        mistralClient = MistralClient(apiKey)
    }
}
