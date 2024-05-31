package org.mistral.ai.kmp.domain

data class Chat(
    val model: String,
    val messages: List<Message>,
)

data class ModelParams(
    val safePrompt: Boolean? = true,
    val temperature: Float? = null,
    val topP: Float? = null,
    val maxTokens: Long? = null,
    val minTokens: Long? = null,
    val randomSeed: Int? = null,
    val stop: List<String> = emptyList(),
    val suffix: String? = null,
)

data class Message(
    val role: Role = Role.USER,
    val content: String,
)

data class Usage(
    val promptTokens: Int,
    val totalTokens: Int,
    val completionTokens: Int? = null,
)

enum class Role(val role: String) {
    SYSTEM("system"),
    USER("user"),
    ASSISTANT("user"),
    TOOL("tool"),
    UNKNOWN("unknown")
}

enum class FinishReason(val reason: String) {
    STOP("stop"),
    LENGTH("length"),
    MODEL_LENGTH("model_length"),
    ERROR("error"),
    TOOL_CALLS("tool_calls"),
    UNKNOWN("unknown")
}
