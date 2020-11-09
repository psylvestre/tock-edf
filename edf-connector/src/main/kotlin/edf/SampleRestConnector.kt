package edf

import com.fasterxml.jackson.module.kotlin.readValue
import ai.tock.bot.connector.ConnectorBase
import ai.tock.bot.connector.ConnectorCallback
import ai.tock.bot.connector.ConnectorData
import ai.tock.bot.connector.ConnectorType
import ai.tock.bot.engine.BotRepository
import ai.tock.bot.engine.ConnectorController
import ai.tock.bot.engine.action.Action
import ai.tock.bot.engine.event.Event
import ai.tock.bot.engine.user.PlayerId
import ai.tock.bot.engine.user.UserPreferences
import ai.tock.shared.Executor
import ai.tock.shared.injector
import ai.tock.shared.jackson.mapper
import ai.tock.shared.provide
import io.vertx.ext.web.RoutingContext
import mu.KotlinLogging

internal const val EDF_CONNECTOR_ID = "edf"
val sampleRestConnectorType = ConnectorType(EDF_CONNECTOR_ID)

class SampleRestConnector internal constructor(
    val applicationId: String,
    val path: String
) : ConnectorBase(sampleRestConnectorType) {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    private val executor: Executor get() = injector.provide()

    override fun register(controller: ConnectorController) {
        controller.registerServices(path) { router ->
            logger.info("Request for path $path from EDF Connector")
            logger.info("deploy sample rest connector services for root path $path ")

            router.post(path).handler { context ->
                try {
                    logger.info("Etape 01")
                    executor.executeBlocking {
                        handleRequest(controller, context, context.bodyAsString)
                    }
                } catch (e: Throwable) {
                    logger.info("Etape 02")
                    context.fail(e)
                }
            }
        }
    }

    //internal for tests
    internal fun handleRequest(
        controller: ConnectorController,
        context: RoutingContext,
        body: String
    ) {
        val timerData = BotRepository.requestTimer.start("sample_webhook")
        try {
            logger.info("Etape 03")
            logger.info { "Sample request input : $body" }
            val request: SampleConnectorRequest = mapper.readValue(body)
            val callback = SampleRestConnectorCallback(applicationId, request.locale, context)
            controller.handle(request.toEvent(applicationId), ConnectorData(callback))
        } catch (t: Throwable) {
            logger.info("Etape 04 $t")
            BotRepository.requestTimer.throwable(t, timerData)
            context.fail(t)
        } finally {
            logger.info("Etape 05")
            BotRepository.requestTimer.end(timerData)
        }
    }

    override fun send(event: Event, callback: ConnectorCallback, delayInMs: Long) {
        val c = callback as? SampleRestConnectorCallback
        c?.addAction(event)
        logger.info("Etape 06")
        if (event is Action) {
            logger.info("Etape 07 $event")
            if (event.metadata.lastAnswer) {
                logger.info("Etape 08")
                c?.sendResponse()
            }
        } else {
            logger.info("Etape 09")
            logger.trace { "unsupported event: $event" }
        }
    }

    override fun loadProfile(callback: ConnectorCallback, userId: PlayerId): UserPreferences {
        logger.info("Etape 10")
        callback as SampleRestConnectorCallback
        return UserPreferences().apply {
            locale = callback.locale
        }
    }

}