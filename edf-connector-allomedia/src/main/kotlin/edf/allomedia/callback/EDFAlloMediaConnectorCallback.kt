package edf.allomedia.callback

import ai.tock.bot.connector.ConnectorCallbackBase
import ai.tock.bot.engine.action.Action
import ai.tock.bot.engine.action.SendSentence
import ai.tock.shared.jackson.mapper
import edf.allomedia.connector.EDFAlloMediaMessage
import edf.allomedia.response.EDFAlloMediaResponse
import edf.allomedia.response.EDFOutputText
import edf.allomedia.connector.edfAlloMediaConnectorType
import edf.allomedia.request.EDFAlloMediaSession
import io.vertx.core.http.HttpHeaders
import io.vertx.ext.web.RoutingContext
import mu.KotlinLogging
import java.util.concurrent.CopyOnWriteArrayList

class EDFAlloMediaConnectorCallback(
        applicationId: String,
        val session: EDFAlloMediaSession,
        val context: RoutingContext,
        val actions: MutableList<Action> = CopyOnWriteArrayList()
) : ConnectorCallbackBase(applicationId, edfAlloMediaConnectorType) {

    fun sendAnswer() {
        val logger = KotlinLogging.logger {}
        actions.forEach {
            logger.info("### Info ${it.javaClass}")
            logger.info("### Info ${it.playerId}")
        }
        val res = context.response()
        res.putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
        res.statusCode = 201
        val edfOutputText: EDFOutputText = EDFOutputText(
                actions
                        .asSequence()
                        .filterIsInstance<SendSentence>()
                        .map { it.text }
                        .filterNotNull()
                        .map { it.toString() }
                        .joinToString("\n")
        )
        // val edfStoryResponse: EDFStoryResponse = mapper.readValue(edfOutputText.textToSpeech)
        // logger.info("### Info StartIntent ${edfStoryResponse.story01?.starterIntent}")
        res.end(
            mapper.writeValueAsString(
                    EDFAlloMediaResponse(
                            session.sessionId,
                            edfOutputText,
                            actions
                                    .asSequence()
                                    .filterIsInstance<SendSentence>()
                                    .mapNotNull { it.message(edfAlloMediaConnectorType) }
                                    .filterIsInstance<EDFAlloMediaMessage>()
                                    .firstOrNull()
                                    ?.goodbye
                    )
            )
        )
    }
}