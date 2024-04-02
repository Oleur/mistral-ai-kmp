package data.datasource

import org.mistral.ai.kmp.domain.Chat

class ChatCacheDataSource {

    private var currentChat: Chat? = null

    fun getCurrentChat() = currentChat

    fun setCurrentChat(chat: Chat) {
        currentChat = chat
    }
}