package edf

import ai.tock.bot.engine.BotBus
import ai.tock.bot.engine.action.Action
import ai.tock.shared.jackson.mapper
import mu.KotlinLogging

object Utilities {
    val logger = KotlinLogging.logger {}
    
    fun logData(botbus: BotBus) {
//        logger.info { "Info Action ${botbus.action}" }
//        logger.info { "Info BotDefinition ${botbus.botDefinition}" }
//        logger.info { "Info ConnectorData ${botbus.connectorData}" }
//        logger.info { "Info Dialog ${botbus.dialog}" }
        logger.info { "Info Dialog Id ${botbus.dialog.id}" }
        logger.info { "Info Dialog PlayerId ${botbus.dialog.playerIds}" }
        logger.info { "Info Dialog GroupId ${botbus.dialog.groupId}" }
        logger.info { "Info Dialog Stories Size ${botbus.dialog.stories.size}" }
        logger.info { "Info Dialog CurrentStory StaterIntent ${botbus.dialog.currentStory?.starterIntent}" }
//        logger.info { "Info Entities ${botbus.entities}" }
//        logger.info { "Info UserTimeLine ${botbus.userTimeline}" }
        logger.info { "Info UserTimeLine Dialogs Size ${botbus.userTimeline.dialogs.size}" }
        logger.info { "Info UserTimeLine Dialog[0] Id ${botbus.userTimeline.dialogs[0].id}" }
        logger.info { "Info UserTimeLine Dialog[0] Stories Size ${botbus.userTimeline.dialogs[0].stories.size}" }
        logger.info { "Info UserTimeLine Dialog[0] Stories[0] StartIntent ${botbus.userTimeline.dialogs[0].stories[0].starterIntent}" }
        logger.info { "Info UserTimeLine Dialog[0] Stories[0] Definition ${botbus.userTimeline.dialogs[0].stories[0].definition}" }
//        logger.info { "Info Json Dialogs Dialog[0] Json ${mapper.writeValueAsString(botbus.userTimeline.dialogs[0])}"}
//        logger.info { "Info Json Dialogs Dialog[0] Stories[0] Json ${mapper.writeValueAsString(botbus.userTimeline.dialogs[0].stories[0])}"}
        logger.info { "Info UserText ${botbus.userText}" }
//        logger.info { "Info Story ${botbus.story}" }
//        logger.info { "Info Step ${botbus.step}" }
//        logger.info { "Info UserPreferences ${botbus.userPreferences}" }
        logger.info { "Info Intent ${botbus.intent}" }
        logger.info { "Info ApplicationId ${botbus.applicationId}" }
        logger.info { "Info BotId ${botbus.botId}" }
        logger.info { "Info ContextId ${botbus.contextId}" }
        logger.info { "Info UserId ${botbus.userId}" }
//        logger.info { "Info StepName ${botbus.stepName}" }
//        logger.info { "Info UserLocale ${botbus.userLocale}" }
//        logger.info { "Info Destination ${botbus.destination}" }
//        logger.info { "Info Location ${botbus.location}" }
//        logger.info { "Info Origin ${botbus.origin}" }
//        logger.info { "Info IdValue ${botbus.idValue}" }
//        logger.info { "Info Json ${botbus.json}" }

    }
}