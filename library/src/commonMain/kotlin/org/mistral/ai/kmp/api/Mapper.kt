package org.mistral.ai.kmp.api

import org.mistral.ai.kmp.domain.ChatParams
import org.mistral.ai.kmp.domain.Embedding
import org.mistral.ai.kmp.domain.Embeddings
import org.mistral.ai.kmp.domain.Message
import org.mistral.ai.kmp.domain.Model
import org.mistral.ai.kmp.domain.Role
import org.mistral.ai.kmp.domain.Usage

internal class ResponseMapper {

    fun mapModels(remoteData: List<ModelDataResponse>): List<Model> {
        return remoteData.map {
            Model(
                id = it.id,
                name = it.obj,
                createdAt = it.createdAt,
                ownedBy = it.ownBy,
            )
        }
    }

    fun mapChoices(remoteChoices: List<CompletionsChoiceResponse>): List<Message> {
        return remoteChoices.map {
            Message(
                role = mapRole(it.message?.role),
                content = it.message?.content.orEmpty(),
            )
        }
    }

    fun mapStreamChoices(remoteChoices: List<CompletionsChoiceResponse>): List<Message> {
        return remoteChoices.map {
            Message(
                role = mapRole(it.message?.role),
                content = it.delta?.content.orEmpty(),
            )
        }
    }

    fun mapEmbeddings(remoteEmbeddings: EmbeddingsResponse): Embeddings {
        return Embeddings(
            id = remoteEmbeddings.id,
            obj = remoteEmbeddings.obj,
            model = remoteEmbeddings.model,
            embeddings = remoteEmbeddings.data.map {
                Embedding(
                    obj = it.obj,
                    embeddings = it.embedding,
                    index = it.index,
                )
            },
            usage = Usage(
                promptTokens = remoteEmbeddings.usage.promptTokens,
                totalTokens = remoteEmbeddings.usage.totalTokens,
            )
        )
    }

    private fun mapRole(role: String?): Role {
        return when (role) {
            "user" -> Role.USER
            "system" -> Role.SYSTEM
            "assistant" -> Role.ASSISTANT
            "tool" -> Role.TOOL
            else -> Role.UNKNOWN
        }
    }
}

internal class RequestMapper {

    fun mapCompletionRequest(
        model: String,
        messages: List<Message>,
        stream: Boolean = false,
        params: ChatParams? = null
    ): CompletionRequest {
        return CompletionRequest(
            model = model,
            messages = messages.map {
                MessageRequest(
                    role = it.role.role,
                    content = it.content,
                )
            },
            stream = stream,
            temperature = params?.temperature,
            topP = params?.topP,
            maxTokens = params?.maxTokens,
            randomSeed = params?.randomSeed,
            safePrompt = params?.safePrompt ?: true
        )
    }
}
