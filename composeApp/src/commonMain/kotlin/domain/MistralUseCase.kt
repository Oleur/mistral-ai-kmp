package domain

import org.mistral.ai.kmp.domain.Model
import data.repository.MistralRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mistral.ai.kmp.domain.Message

class MistralUseCase(private val repository: MistralRepository) {

    suspend fun getModels(): List<Model> {
        val models = repository.getModels().getOrDefault(emptyList()).toMutableList()
        val selectedModelId = repository.getSelectedModelId()
        models.firstOrNull { it.id == selectedModelId }?.also {
            models.remove(it)
            models.add(0, it)
        }
        return models
    }

    // TODO Make it a true conversation with the assistant
    suspend fun chat(message: String): List<Message> {
        val model = getSelectedModelId()
        return repository.chat(model, message).getOrDefault(emptyList())
    }

    // TODO Make it a true conversation with the assistant
    suspend fun chatStream(message: String): Flow<List<Message>> {
        val model = getSelectedModelId()
        return repository.chatStream(model, message).map { it.getOrDefault(emptyList()) }
    }

    fun setSelectedModel(model: Model) {
        repository.setSelectedModel(model)
    }

    fun getSelectedModelId() = repository.getSelectedModelId()
}