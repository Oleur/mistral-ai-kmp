package org.mistral.ai.kmp.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.api.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import org.mistral.ai.kmp.domain.ChatParams
import org.mistral.ai.kmp.domain.Embeddings
import org.mistral.ai.kmp.domain.Message
import org.mistral.ai.kmp.domain.Model

internal class MistralService(private val auth: String) {

    private val responseMapper = ResponseMapper()
    private val requestMapper = RequestMapper()

    private val clientJson = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys =  true
    }

    private val client = HttpClient {
        install(
            createClientPlugin("HeaderPlugin") {
                onRequest { request, _ ->
                    request.headers.append(HttpHeaders.Accept, "application/json")
                    request.headers.append(HttpHeaders.ContentType, "application/json")
                    request.headers.append(HttpHeaders.Authorization, "Bearer $auth")
                }
            }
        )
        install(Logging) {
            logger = object: Logger {
                override fun log(message: String) {
                    println("[MISTRAL KTOR] $message")
                }
            }
            level = LogLevel.ALL
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }
        install(HttpTimeout) {
            requestTimeoutMillis = TIMEOUT
        }
        install(ContentNegotiation) {
            json(clientJson)
        }
    }

    suspend fun getModels(): Result<List<Model>> {
        return runCatching {
            val response: ModelsResponse = client.get("$BASE_URL/v1/models").body()
            val models = responseMapper.mapModels(response.data)
            Result.success(models)
        }.getOrElse(Result.Companion::failure)
    }

    suspend fun chat(
        model: String,
        messages: List<Message>,
        params: ChatParams? = null,
    ): Result<List<Message>> {
        return runCatching {
            val request = requestMapper.mapCompletionRequest(
                model = model,
                messages = messages,
                params = params
            )

            val response: CompletionsResponse = client.post("$BASE_URL/v1/chat/completions") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()

            Result.success(responseMapper.mapChoices(response.choices))
        }.getOrElse {
            Result.failure(it)
        }
    }

    suspend fun chatStream(
        model: String,
        messages: List<Message>,
        params: ChatParams? = null,
    ): Flow<Result<List<Message>>> = flow {
        runCatching {
            val request = requestMapper.mapCompletionRequest(
                model = model,
                messages = messages,
                stream = true,
                params = params
            )

            val channel = client.post("$BASE_URL/v1/chat/completions") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.bodyAsChannel()

            while (!channel.isClosedForRead) {
                val line = channel.readUTF8Line()?.takeUnless { it.isEmpty() } ?: continue
                try {
                    val response = clientJson.decodeFromString<CompletionsResponse>(line.removePrefix(STREAM_PREFIX))
                    emit(Result.success(responseMapper.mapStreamChoices(response.choices)))
                } catch (e: Exception) {
                    emit(Result.failure(e))
                }
            }
        }.getOrElse {
            emit(Result.failure(it))
        }
    }

    suspend fun createEmbeddings(model: String, input: List<String>): Result<Embeddings> {
        return runCatching {
            val request = EmbeddingsRequest(model, input)

            val response: EmbeddingsResponse = client.post("$BASE_URL/v1/embeddings") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()

            Result.success(responseMapper.mapEmbeddings(response))
        }.getOrElse {
            Result.failure(it)
        }
    }

    private companion object {
        const val BASE_URL = "https://api.mistral.ai"

        const val STREAM_PREFIX = "data: "

        const val TIMEOUT = 30_000L
    }
}
