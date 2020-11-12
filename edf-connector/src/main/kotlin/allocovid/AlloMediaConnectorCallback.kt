package allocovid

import ai.tock.bot.connector.ConnectorCallbackBase
import ai.tock.bot.engine.action.Action
import ai.tock.bot.engine.action.SendSentence
import ai.tock.shared.jackson.mapper
import io.vertx.core.http.HttpHeaders
import io.vertx.ext.web.RoutingContext
import java.util.concurrent.CopyOnWriteArrayList

class AlloMediaConnectorCallback(
        applicationId: String,
        val session: AlloMediaSession,
        val context: RoutingContext,
        val actions: MutableList<Action> = CopyOnWriteArrayList()
) : ConnectorCallbackBase(applicationId, alloMediaConnectorType) {

    fun sendAnswer() {
        val res = context.response()
        res.putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
        res.statusCode = 201
        res.end(
            mapper.writeValueAsString(
                    AlloMediaResponse(
                            session.sessionId,
                            OutputText(
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
                                    .mapNotNull { it.message(alloMediaConnectorType) }
                                    .filterIsInstance<AlloMediaMessage>()
                                    .firstOrNull()
                                    ?.goodbye
                    )
            )
        )
    }
}