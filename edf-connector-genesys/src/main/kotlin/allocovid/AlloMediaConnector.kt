/*
 * Copyright (C) 2020 e-voyageurs technologies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package allocovid

import ai.tock.bot.connector.*
import ai.tock.bot.engine.BotBus
import ai.tock.bot.engine.ConnectorController
import ai.tock.bot.engine.action.Action
import ai.tock.bot.engine.action.SendSentence
import ai.tock.bot.engine.event.Event
import ai.tock.bot.engine.user.PlayerId
import ai.tock.bot.engine.user.PlayerType.bot
import ai.tock.bot.engine.user.UserPreferences
import ai.tock.nlp.api.client.model.NlpResult
import ai.tock.shared.jackson.mapper
import ai.tock.shared.property
import ai.tock.shared.vertx.blocking
import ai.tock.shared.warn
import ai.tock.translator.UserInterfaceType.voiceAssistant
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.common.cache.CacheBuilder
import io.vertx.core.http.HttpHeaders
import mu.KotlinLogging
import java.time.Duration
import java.util.*

private class AlloMediaConnector(val applicationId: String, val path: String) : Connector {

    override val connectorType: ConnectorType = alloMediaConnectorType

    override fun register(controller: ConnectorController) {
        controller.registerServices(path) { router ->
            router.post("$path/call").blocking { context ->
                val auth = context.request().getHeader(HttpHeaders.AUTHORIZATION)
                val contentType = context.request().getHeader(HttpHeaders.CONTENT_TYPE)
                when {
                    auth != basicAuthEncoded -> context.fail(401)
                    contentType != "application/json" -> context.fail(400)
                    else -> {
                        try {
                            val body = context.bodyAsString
                            val request: AlloMediaRequest = mapper.readValue(body)
                            val callback = AlloMediaConnectorCallback(
                                    applicationId,
                                    request.session,
                                    context
                            )
                            val locale = request.locale?.also {
                                cache.put(request.session.sessionId, it)
                            }

                            val event = SendSentence(
                                PlayerId(request.session.sessionId),
                                applicationId,
                                PlayerId("bot", bot),
                                request.text,
                                precomputedNlp = request.intent?.takeUnless { it.isBlank() }?.let {
                                    NlpResult(it, "vsc", locale
                                        ?: Locale.FRENCH, emptyList(), intentProbability = 1.0, entitiesProbability = 1.0, retainedQuery = request.text
                                        ?: "")
                                }
                            )
                            controller.handle(event, AlloMediaConnectorData(callback))
                        } catch (e: MissingKotlinParameterException) {
                            //malformed request
                            logger.warn(e)
                            context.fail(400)
                        }
                    }
                }
            }
        }
    }

    override fun loadProfile(callback: ConnectorCallback, userId: PlayerId): UserPreferences? {
        return cache.getIfPresent(userId.id)?.let {
            UserPreferences(locale = it)
        }
    }

    override fun send(event: Event, callback: ConnectorCallback, delayInMs: Long) {
        callback as AlloMediaConnectorCallback
        if (event is Action) {
            callback.actions.add(event)
            if (event.metadata.lastAnswer) {
                callback.sendAnswer()
            }
        } else {
            logger.trace { "unsupported event: $event" }
        }
    }

    companion object {
        private val logger = KotlinLogging.logger {}
        private val cache = CacheBuilder.newBuilder().expireAfterWrite(Duration.ofMinutes(1)).build<String, Locale>()
    }
}

val alloMediaConnectorType = ConnectorType("allomedia", voiceAssistant)

private object AlloMediaConnectorProvider : ConnectorProvider {

    override val connectorType: ConnectorType = alloMediaConnectorType

    override fun connector(connectorConfiguration: ConnectorConfiguration): Connector {
        return AlloMediaConnector(
                connectorConfiguration.connectorId,
                connectorConfiguration.path
        )
    }

}

class AlloMediaConnectorProviderService : ConnectorProvider by AlloMediaConnectorProvider

fun BotBus.withAlloMedia(message: AlloMediaMessage): BotBus {
    return withAlloMedia { message }
}

fun BotBus.withAlloMedia(messageProvider: () -> AlloMediaMessage): BotBus {
    return withMessage(alloMediaConnectorType) { messageProvider.invoke() }
}

private val basicAuthEncoded = property("allo_media_basic_auth", "")
