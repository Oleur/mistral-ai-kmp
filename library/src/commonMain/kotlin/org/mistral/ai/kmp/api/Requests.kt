package org.mistral.ai.kmp.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CompletionRequest(
    @SerialName("model") val model: String,
    @SerialName("messages") val messages: List<MessageRequest>,
    @SerialName("temperature") val temperature: Float? = null,
    @SerialName("top_p") val topP: Float? = null,
    @SerialName("max_tokens") val maxTokens: Long? = null,
    @SerialName("stream") val stream: Boolean? = false,
    @SerialName("safe_prompt") val safePrompt: Boolean = false,
    @SerialName("random_seed") val randomSeed: Int? = null,
)

@Serializable
internal data class MessageRequest(
    @SerialName("role") val role: String,
    @SerialName("content") val content: String,
)

@Serializable
internal data class EmbeddingsRequest(
    @SerialName("model") val model: String,
    @SerialName("input") val input: List<String>,
    @SerialName("encoding_format") val encodingFormat: String = ENCODING_FORMAT_FLOAT,
)

internal const val ENCODING_FORMAT_FLOAT = "float"
