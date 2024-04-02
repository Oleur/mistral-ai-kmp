package ui.state

import org.mistral.ai.kmp.domain.Model

sealed interface ViewState

// Model view state
data class ModelViewState(
    val models: List<Model>,
    val selectedModelId: String,
) : ViewState

// History view state
data class HistoryViewState(
    val queries: List<String>,
    val currentQuery: Int = NO_INDEX,
) : ViewState

// Chat view states
data class ChatViewState(
    val conversation: List<ContentViewState>,
) : ViewState

data class ContentViewState(
    val content: String,
    val isMistral: Boolean? = false,
)

const val NO_INDEX = -1