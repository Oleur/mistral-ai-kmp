package ui

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import domain.MistralUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.mistral.ai.kmp.domain.Model
import ui.state.ChatViewState
import ui.state.ContentViewState
import ui.state.HistoryViewState
import ui.state.ModelViewState

class MistralViewModel(
    private val mistralUseCase: MistralUseCase,
) : KMMViewModel() {

    private val _models: MutableStateFlow<ModelViewState> = MutableStateFlow(ModelViewState(emptyList(), ""))
    val models: StateFlow<ModelViewState> = _models

    private val _history: MutableStateFlow<HistoryViewState> = MutableStateFlow(HistoryViewState(emptyList()))
    val history: StateFlow<HistoryViewState> = _history

    private val _chat: MutableStateFlow<ChatViewState> = MutableStateFlow(ChatViewState(emptyList()))
    val chat: StateFlow<ChatViewState> = _chat

    fun fetchModels() {
        viewModelScope.coroutineScope.launch(Dispatchers.Default) {
            val models = mistralUseCase.getModels()
            val selectedModelId = mistralUseCase.getSelectedModelId()
            _models.tryEmit(ModelViewState(models, selectedModelId))
        }
    }

    fun setSelectedModel(model: Model) {
        mistralUseCase.setSelectedModel(model)
        viewModelScope.coroutineScope.launch(Dispatchers.Default) {
            val models = _models.value.models
            _models.tryEmit(ModelViewState(models, model.id))
        }
    }

    fun getHistory() {
        viewModelScope.coroutineScope.launch(Dispatchers.Default) {
            // TODO
        }
    }

    fun chat(message: String) {
        viewModelScope.coroutineScope.launch(Dispatchers.Default) {
            val chat = _chat.value.conversation.toMutableList()
            if (chat.isEmpty()) {
                val newHistory = _history.value.queries + listOf(message)
                _history.tryEmit(
                    _history.value.copy(
                        queries = newHistory,
                        currentQuery = newHistory.indexOf(message),
                    )
                )
            }
            chat.add(ContentViewState(message))
            val messages = mistralUseCase.chat(message)
            chat.add(
                ContentViewState(
                    content = messages.joinToString(" ") { it.content },
                    isMistral = true
                )
            )
            _chat.tryEmit(ChatViewState(chat))
        }
    }

    fun chatStream(message: String) {
        viewModelScope.coroutineScope.launch(Dispatchers.Default) {
            val chat = _chat.value.conversation.toMutableList()
            if (chat.isEmpty()) {
                val newHistory = _history.value.queries + listOf(message)
                _history.tryEmit(
                    _history.value.copy(
                        queries = newHistory,
                        currentQuery = newHistory.indexOf(message),
                    )
                )
            }

            chat.add(ContentViewState(message))
            chat.add(
                ContentViewState(
                    content = "",
                    isMistral = true,
                )
            )

            mistralUseCase.chatStream(message).collect { messages ->
                val newContent = messages.joinToString(" ") { it.content }
                chat.lastOrNull()?.let { last ->
                    chat[chat.lastIndex] = last.copy(content = last.content + newContent)
                    _chat.tryEmit(ChatViewState(chat))
                }
            }
        }
    }

    fun newChat() {
        viewModelScope.coroutineScope.launch {
            _chat.tryEmit(ChatViewState(emptyList()))
        }
    }
}
