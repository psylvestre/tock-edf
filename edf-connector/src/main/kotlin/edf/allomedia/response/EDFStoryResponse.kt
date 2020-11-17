package edf.allomedia.response

import ai.tock.bot.connector.ConnectorData
import ai.tock.bot.definition.BotDefinition
import ai.tock.bot.definition.IntentAware
import ai.tock.bot.definition.StoryHandlerDefinition
import ai.tock.bot.definition.StoryStep
import ai.tock.bot.engine.action.Action
import ai.tock.bot.engine.dialog.Dialog
import ai.tock.bot.engine.dialog.EntityStateValue
import ai.tock.bot.engine.dialog.Story
import ai.tock.bot.engine.user.PlayerId
import ai.tock.bot.engine.user.UserPreferences
import ai.tock.bot.engine.user.UserTimeline
import java.util.*

data class EDFStoryResponse(
    val action: Action?,
    val botDefinition: BotDefinition?,
    val connectorData: ConnectorData?,
    val dialog: Dialog?,
    val entities: Map<String, EntityStateValue>?,
    val userTimeLine: UserTimeline?,
    val userText: String?,
    val story: Story?,
    val step: StoryStep<out StoryHandlerDefinition>?,
    val userPreferences: UserPreferences?,
    val intent: IntentAware?,
    val applicationId: String?,
    val botId: PlayerId?,
    val contextId: String?,
    val userId: PlayerId?,
    val stepName: String?,
    val userLocale: Locale?,
    val idValue: Any?,
    val json: String?,
    val text: CharSequence?,
    val story01: Story?
)