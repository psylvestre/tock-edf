package edf

import ai.tock.bot.connector.ConnectorCallbackBase
import ai.tock.bot.engine.action.Action
import ai.tock.bot.engine.action.SendSentence
import ai.tock.bot.engine.event.Event
import ai.tock.shared.jackson.mapper
import io.vertx.ext.web.RoutingContext
import mu.KotlinLogging
import java.util.Locale
import java.util.concurrent.CopyOnWriteArrayList

internal class SampleRestConnectorCallback(
    applicationId: String,
    val locale: Locale,
    private val context: RoutingContext,
    private val actions: MutableList<Action> = CopyOnWriteArrayList()
) : ConnectorCallbackBase(applicationId, sampleRestConnectorType) {

    private val logger = KotlinLogging.logger {}


    fun addAction(event: Event) {
        logger.info("Etape 11")
        if (event is Action) {
            logger.info("Etape 12 $event")
            actions.add(event)
        } else {
            logger.info("Etape 13 $event")
            logger.trace { "unsupported event: $event" }
        }
    }

    fun sendResponse() {
        logger.info("Etape 14")
        val messages = actions
            .filterIsInstance<SendSentence>()
            .mapNotNull {
                if (it.stringText != null) {
                    logger.info("Etape 14.1 $it")
                    SampleMessage(it.stringText!!)
                } else it.message(sampleRestConnectorType)?.let {
                    logger.info("Etape 14.2 map null")
                    it as? SampleMessage
                }

            }
        logger.info("Etape 15 $messages")
        context.response().end(mapper.writeValueAsString(SampleConnectorResponse(messages)))
    }
}