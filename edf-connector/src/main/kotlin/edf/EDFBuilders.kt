package edf

import ai.tock.bot.connector.ConnectorMessage
import ai.tock.bot.definition.IntentAware
import ai.tock.bot.definition.Parameters
import ai.tock.bot.definition.StoryHandlerDefinition
import ai.tock.bot.definition.StoryStep
import ai.tock.bot.engine.BotBus
import ai.tock.bot.engine.I18nTranslator
import ai.tock.bot.engine.action.SendChoice

/**
 * Adds a EDF [ConnectorMessage] if the current connector is Sample.
 * You need to call [BotBus.send] or [BotBus.end] later to send this message.
 */
fun BotBus.withEDF(messageProvider: () -> EDFMessage): BotBus {
    return withMessage(edfRestConnectorType, messageProvider)
}

/**
 * Creates a text with buttons.
 */
fun I18nTranslator.edfMessage(title: CharSequence, vararg buttons: EDFButton): EDFMessage =
        EDFMessage(
                translate(title).toString(), buttons.toList()
        )

/**
 * Creates a EDF button.
 */
fun BotBus.edfButton(
    title: CharSequence,
    targetIntent: IntentAware? = null,
    step: StoryStep<out StoryHandlerDefinition>? = null,
    parameters: Parameters = Parameters()
): EDFButton =
        EDFButton(
                translate(title).toString(),
                targetIntent?.let { i -> SendChoice.encodeChoiceId(this, i, step, parameters.toMap()) }
        )