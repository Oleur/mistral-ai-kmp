package org.mistral.ai.kmp

import kotlinx.coroutines.flow.Flow
import org.mistral.ai.kmp.api.MistralService
import org.mistral.ai.kmp.domain.ChatParams
import org.mistral.ai.kmp.domain.Embeddings
import org.mistral.ai.kmp.domain.Message
import org.mistral.ai.kmp.domain.Model
import org.mistral.ai.kmp.domain.Role

class MistralClient(apiKey: String) {

    private val service = MistralService(apiKey)

    suspend fun getModels(): Result<List<Model>> {
        return service.getModels()
    }

    suspend fun chat(
        model: String,
        messages: List<Message>,
        params: ChatParams? = null,
    ): Result<List<Message>> {
        messages.validate()
        return service.chat(model, messages, params)
    }

    suspend fun chatStream(
        model: String,
        messages: List<Message>,
        params: ChatParams? = null,
    ): Flow<Result<List<Message>>> {
        messages.validate()
        return service.chatStream(model, messages, params)
    }

    suspend fun createEmbeddings(
        model: String,
        input: List<String>,
    ): Result<Embeddings> {
        return service.createEmbeddings(model, input)
    }

    private fun List<Message>.validate() {
        require(isNotEmpty()) { "Messages cannot be empty!" }
        require(first().role in listOf(Role.USER, Role.SYSTEM)) {
            "The first prompt role should be user or system."
        }
    }
}
