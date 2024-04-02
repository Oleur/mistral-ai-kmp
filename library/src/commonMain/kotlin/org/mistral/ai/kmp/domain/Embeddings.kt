package org.mistral.ai.kmp.domain

data class Embeddings(
    val id: String,
    val obj: String,
    val model: String,
    val embeddings: List<Embedding>,
    val usage: Usage? = null,
)

data class Embedding(
    val obj: String,
    val embeddings: List<Float>,
    val index: Int,
)
