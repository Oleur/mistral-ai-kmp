package org.mistral.ai.kmp

import kotlinx.coroutines.flow.Flow
import org.mistral.ai.kmp.api.MistralService
import org.mistral.ai.kmp.domain.ModelParams
import org.mistral.ai.kmp.domain.Embeddings
import org.mistral.ai.kmp.domain.Message
import org.mistral.ai.kmp.domain.Model
import org.mistral.ai.kmp.domain.validate
import org.mistral.ai.kmp.domain.validateCodeModels

/**
 * MistralClient is a client class that provides methods to interact with the Mistral AI platform.
 *
 * @param apiKey The API key to authenticate the client.
 */
class MistralClient(apiKey: String) {

    private val service = MistralService(apiKey)

    /**
     * Fetches a list of Mistral AI models from the server.
     *
     * @return A [Result] object containing a list of [Model] objects, or an exception if the operation fails.
     */
    suspend fun getModels(): Result<List<Model>> {
        return service.getModels()
    }

    /**
     * Sends chat messages to the server and retrieves the response.
     *
     * @param model The name of the model to use for the chat.
     * @param messages The list of messages to send in the chat conversation.
     * @param params The optional [ModelParams] to customize the model behavior.
     * @return A [Result] object containing a list of [Message] objects as a response, or an exception if the operation fails.
     */
    suspend fun chat(
        model: String,
        messages: List<Message>,
        params: ModelParams? = null,
    ): Result<List<Message>> {
        messages.validate()
        params?.validate()
        return service.chat(model, messages, params)
    }

    /**
     * Creates a stream of chat messages for the given model and send the tokens as they become available.
     *
     * @param model The name of the model to use for the chat.
     * @param messages The list of messages to send in the chat conversation.
     * @param params The optional [ModelParams] to customize the model behavior. Default is `null`.
     * @return A [Flow] of [Result] objects containing a list of [Message] objects as a response, or an exception if the operation fails.
     */
    suspend fun chatStream(
        model: String,
        messages: List<Message>,
        params: ModelParams? = null,
    ): Flow<Result<List<Message>>> {
        messages.validate()
        params?.validate()
        return service.chatStream(model, messages, params)
    }

    /**
     * Executes the code completion for the given model and prompt.
     *
     * @param model The name of the model to use for the code completion.
     * @param prompt The code prompt to provide for completion.
     * @param params The optional [ModelParams] to customize the model behavior. Default is `null`.
     * @return A [Result] object containing a list of [Message] objects as a response, or an exception if the operation fails.
     */
    suspend fun code(
        model: String,
        prompt: String,
        params: ModelParams? = null,
    ): Result<List<Message>> {
        model.validateCodeModels()
        params?.validate()
        return service.code(model, prompt, params)
    }

    /**
     * Executes the code completion for the given model and prompt and send the tokens as they become available.
     *
     * @param model The name of the model to use for the code completion.
     * @param prompt The code prompt to provide for completion.
     * @param params The optional [ModelParams] to customize the model behavior. Default is `null`.
     * @return A [Flow] of [Result] objects containing a list of [Message] objects as a response,
     * or an exception if the operation fails.
     */
    suspend fun codeStream(
        model: String,
        prompt: String,
        params: ModelParams? = null,
    ): Flow<Result<List<Message>>> {
        model.validateCodeModels()
        params?.validate()
        return service.codeStream(model, prompt, params)
    }

    /**
     * Creates embeddings for the given model and input data.
     *
     * @param model The name of the model to use for creating embeddings.
     * @param input The list of strings as input data for creating embeddings.
     * @return A [Result] object containing the embeddings result, or an exception if the operation fails.
     */
    suspend fun createEmbeddings(
        model: String,
        input: List<String>,
    ): Result<Embeddings> {
        return service.createEmbeddings(model, input)
    }
}
