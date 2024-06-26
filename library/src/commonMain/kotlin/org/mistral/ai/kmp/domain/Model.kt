package org.mistral.ai.kmp.domain

data class Model(
    val id: String,
    val name: String,
    val createdAt: Long, // TODO Replace by KMP DateTime
    val ownedBy: String,
)

// Default model (smallest and cheapest one)
const val MODEL_OPEN_MISTRAL_7B = "open-mistral-7b"
const val MODEL_CODESTRAL_2405 = "codestral-2405"
const val MODEL_CODESTRAL_LATEST = "codestral-latest"
