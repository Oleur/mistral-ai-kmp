package data.datasource

import org.mistral.ai.kmp.domain.MODEL_OPEN_MISTRAL_7B

class ModelCacheDataSource {

    private var selectedModelId = MODEL_OPEN_MISTRAL_7B

    fun getSelectedModelId() = selectedModelId

    fun setSelectedModelId(id: String) {
        selectedModelId = id
    }
}