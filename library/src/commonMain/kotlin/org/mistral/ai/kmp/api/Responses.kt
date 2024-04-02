package org.mistral.ai.kmp.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// region Embeddings
@Serializable
internal data class EmbeddingsResponse(
    @SerialName("id") val id: String,
    @SerialName("object") val obj: String,
    @SerialName("model") val model: String,
    @SerialName("usage") val usage: CommonUsageResponse,
    @SerialName("data") val data: List<EmbeddingsDataResponse>,
)

@Serializable
internal data class EmbeddingsDataResponse(
    @SerialName("object") val obj: String,
    @SerialName("embedding") val embedding: List<Float>,
    @SerialName("index") val index: Int,
)
// endregion

// region Models
@Serializable
internal data class ModelsResponse(
    @SerialName("object") val obj: String,
    @SerialName("data") val data: List<ModelDataResponse>,
)

@Serializable
internal data class ModelDataResponse(
    @SerialName("id") val id: String,
    @SerialName("object") val obj: String,
    @SerialName("created") val createdAt: Long,
    @SerialName("owned_by") val ownBy: String,
    @SerialName("parent") val parent: String?,
    @SerialName("root") val root: String?,
)
// endregion

// region Completions
@Serializable
internal data class CompletionsResponse(
    @SerialName("id") val id: String,
    @SerialName("object") val obj: String? = null,
    @SerialName("created") val createdAt: Long? = null,
    @SerialName("model") val model: String,
    @SerialName("choices") val choices: List<CompletionsChoiceResponse>,
    @SerialName("usage") val usage: CommonUsageResponse? = null,
)

@Serializable
internal data class CompletionsChoiceResponse(
    @SerialName("index") val index: Int,
    @SerialName("message") val message: CompletionsMessageResponse? = null,
    @SerialName("delta") val delta: CompletionsMessageResponse? = null,
    @SerialName("finish_reason") val finishReason: String? = null,
)

@Serializable
internal data class CompletionsMessageResponse(
    @SerialName("role") val role: String? = null,
    @SerialName("content") val content: String,
    @SerialName("name") val name: String? = null,
)
// endregion

// region Common
@Serializable
internal data class CommonUsageResponse(
    @SerialName("prompt_tokens") val promptTokens: Int,
    @SerialName("total_tokens") val totalTokens: Int,
    @SerialName("completion_tokens") val completionTokens: Int? = null,
)
// endregion
