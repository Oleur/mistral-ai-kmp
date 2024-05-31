package org.mistral.ai.kmp.domain

internal fun List<Message>.validate() {
    require(isNotEmpty()) { "Messages cannot be empty!" }
    require(first().role in listOf(Role.USER, Role.SYSTEM)) {
        "The first prompt role should be user or system."
    }
}

internal fun String.validateCodeModels() {
    require(this == MODEL_CODESTRAL_2405 || this == MODEL_CODESTRAL_LATEST) {
        "Code models should be codestral-2405 or codestral-latest"
    }
}

internal fun ModelParams.validate() {
    temperature?.let { temperature ->
        require(temperature in 0.0..1.0) {
            "Temperature should be between 0 and 1."
        }
    }
    topP?.let {
        require(topP in 0.0..1.0) {
            "TopP should be between 0 and 1."
        }
    }
    maxTokens?.let {
        require(maxTokens >= 0) {
            "MaxTokens should be greater than or equal to 0."
        }
    }
    minTokens?.let {
        require(minTokens >= 0) {
            "MinTokens should be greater than or equal to 0."
        }
    }
    randomSeed?.let {
        require(randomSeed >= 0) {
            "Seed should be greater than or equal to 0."
        }
    }
}