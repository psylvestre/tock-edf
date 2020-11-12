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

package edf.allomedia.connector

import ai.tock.bot.connector.Connector
import ai.tock.bot.connector.ConnectorCallback
import ai.tock.bot.connector.ConnectorType
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
import edf.allomedia.callback.EDFAlloMediaConnectorCallback
import edf.allomedia.request.EDFAlloMediaRequest
import io.vertx.core.http.HttpHeaders
import mu.KotlinLogging
import java.time.Duration
import java.util.*

private val basicAuthEncoded = property("edf_allo_media_basic_auth", "Basic b3JhbmdlOmF6ZGhhZDEya19qbw==")
val edfAlloMediaConnectorType = ConnectorType("edfallomedia", voiceAssistant)

class EDFAlloMediaConnector(val applicationId: String, val path: String) : Connector {

    override val connectorType: ConnectorType = edfAlloMediaConnectorType

    override fun register(controller: ConnectorController) {
        controller.registerServices(path) { router ->
            logger.info { "Etape 0001 $path" }
            router.post("$path/call").blocking { context ->
                logger.info { "Etape 0002" }
                val auth = context.request().getHeader(HttpHeaders.AUTHORIZATION)
                logger.info { "Etape 0003 $auth" }
                logger.info { "Etape 0003 ${basicAuthEncoded.toString()}" }
                val contentType = context.request().getHeader(HttpHeaders.CONTENT_TYPE)
                logger.info { "Etape 0004 $contentType" }
                when {
                    auth != basicAuthEncoded -> context.fail(401)
                    contentType != "application/json" -> context.fail(400)
                    else -> {
                        try {
                            val body = context.bodyAsString
                            logger.info { "Etape 0005 $body" }
                            val request: EDFAlloMediaRequest = mapper.readValue(body)
                            logger.info { "Etape 0006 ${request.intent}" }
                            val callback = EDFAlloMediaConnectorCallback(
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
                            controller.handle(event, EDFAlloMediaConnectorData(callback))
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
        callback as EDFAlloMediaConnectorCallback
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

fun BotBus.withEDFAlloMedia(message: EDFAlloMediaMessage): BotBus {
    return withEDFAlloMedia { message }
}

fun BotBus.withEDFAlloMedia(messageProvider: () -> EDFAlloMediaMessage): BotBus {
    return withMessage(edfAlloMediaConnectorType) { messageProvider.invoke() }
}
