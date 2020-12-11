package edf.allomedia.callback

import ai.tock.bot.connector.ConnectorCallbackBase
import ai.tock.bot.engine.action.Action
import ai.tock.bot.engine.action.SendSentence
import ai.tock.shared.jackson.mapper
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
        var transfer = true;
        actions.forEach {
            logger.info("### Info ${it.javaClass}")
            logger.info("### Info ${it.playerId}")
            logger.info("### Info ${it.metadata}")
            logger.info("### Info ${it.state}")
            if (!it.metadata.lastAnswer) {
                transfer = false;
            }
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
        if (transfer) {
            res.end(
                mapper.writeValueAsString(
                    EDFAlloMediaResponse(
                        session.sessionId,
                        edfOutputText,
                        transfer,
                        "+33645799228"
                    )
                )
            )
        } else {
            res.end(
                mapper.writeValueAsString(
                    EDFAlloMediaResponse(
                        session.sessionId,
                        edfOutputText
                    )
                )
            )
        }
    }
}