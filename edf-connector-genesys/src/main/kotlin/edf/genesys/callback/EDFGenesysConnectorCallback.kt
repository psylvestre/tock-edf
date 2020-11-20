package edf.genesys.callback

import ai.tock.bot.connector.ConnectorCallbackBase
import ai.tock.bot.engine.action.Action
import ai.tock.bot.engine.action.SendSentence
import ai.tock.shared.jackson.mapper
import com.fasterxml.jackson.module.kotlin.readValue
import edf.genesys.connector.EDFGenesysMessage
import edf.genesys.response.EDFGenesysResponse
import edf.genesys.response.EDFOutputText
import edf.genesys.connector.edfGenesysConnectorType
import edf.genesys.request.EDFGenesysSession
import io.vertx.core.http.HttpHeaders
import io.vertx.ext.web.RoutingContext
import mu.KotlinLogging
import java.util.concurrent.CopyOnWriteArrayList

class EDFGenesysConnectorCallback(
        applicationId: String,
        val session: EDFGenesysSession,
        val context: RoutingContext,
        val actions: MutableList<Action> = CopyOnWriteArrayList()
) : ConnectorCallbackBase(applicationId, edfGenesysConnectorType) {

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
        val edfStoryResponse: List<Action> = mapper.readValue(edfOutputText.textToSpeech)
        logger.info("###>>> Info Action List ${edfStoryResponse.size}")
        res.end(
            mapper.writeValueAsString(
                    EDFGenesysResponse(
                            session.sessionId,
                            edfOutputText,
                            actions
                                    .asSequence()
                                    .filterIsInstance<SendSentence>()
                                    .mapNotNull { it.message(edfGenesysConnectorType) }
                                    .filterIsInstance<EDFGenesysMessage>()
                                    .firstOrNull()
                                    ?.goodbye
                    )
            )
        )
    }
}