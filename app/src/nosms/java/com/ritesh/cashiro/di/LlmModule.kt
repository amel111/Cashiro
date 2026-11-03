package com.ritesh.cashiro.di

import com.ritesh.cashiro.domain.service.LlmService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

/**
 * No-AI flavor binding: satisfies Hilt for [LlmService] without any LLM engine.
 *
 * The Chat UI is hidden in this flavor (BuildConfig.AI_ENABLED = false), so this
 * stub should never be reached; every call fails gracefully instead of linking
 * the LiteRT-LM dependency.
 */
@Module
@InstallIn(SingletonComponent::class)
object LlmModule {

    @Provides
    @Singleton
    fun provideLlmService(): LlmService = NoAiLlmService
}

private object NoAiLlmService : LlmService {
    override suspend fun initialize(modelPath: String): Result<Unit> =
        Result.failure(UnsupportedOperationException("AI is not available in this build"))

    override suspend fun generateResponse(prompt: String): Result<String> =
        Result.failure(UnsupportedOperationException("AI is not available in this build"))

    override fun generateResponseStream(prompt: String): Flow<String> = emptyFlow()

    override suspend fun reset() = Unit

    override suspend fun resetConversation() = Unit

    override fun isInitialized(): Boolean = false
}
