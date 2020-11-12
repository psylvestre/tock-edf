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
import java.util.concurrent.CopyOnWriteArrayList

class EDFAlloMediaConnectorCallback(
        applicationId: String,
        val session: EDFAlloMediaSession,
        val context: RoutingContext,
        val actions: MutableList<Action> = CopyOnWriteArrayList()
) : ConnectorCallbackBase(applicationId, edfAlloMediaConnectorType) {

    fun sendAnswer() {
        val res = context.response()
        res.putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
        res.statusCode = 201
        res.end(
            mapper.writeValueAsString(
                    EDFAlloMediaResponse(
                            session.sessionId,
                            EDFOutputText(
                                    actions
                                            .asSequence()
                                            .filterIsInstance<SendSentence>()
                                            .map { it.text }
                                            .filterNotNull()
                                            .map { it.toString() }
                                            .joinToString("\n")
                            ),
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