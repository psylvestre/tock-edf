/*
 *  This file is part of the tock-bot-open-data distribution.
 *  (https://github.com/theopenconversationkit/tock-bot-open-data)
 *  Copyright (c) 2017 VSCT.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ai.tock.bot.edf

import ai.tock.bot.definition.IntentAware
import ai.tock.bot.definition.story
import ai.tock.bot.engine.dialog.Dialog
import ai.tock.bot.engine.user.UserTimelineDAO
import ai.tock.bot.open.data.story.destination
import ai.tock.bot.open.data.story.location
import ai.tock.bot.open.data.story.origin
import ai.tock.shared.jackson.mapper
import edf.allomedia.connector.EDFAlloMediaMessage
import edf.allomedia.connector.withEDFAlloMedia
import edf.allomedia.response.EDFStoryResponse
import mu.KotlinLogging
import org.litote.kmongo.json
import org.litote.kmongo.util.idValue

/**
 * The greetings handler.
 */
private val logger = KotlinLogging.logger {}

val bonjour = story("bonjour") {
    //cleanup state
    resetDialogState()

    logger.info { "Info Action ${this.action}" }
    logger.info { "Info BotDefinition ${this.botDefinition}" }
    logger.info { "Info ConnectorData ${this.connectorData}" }
    logger.info { "Info Dialog ${this.dialog}" }
    logger.info { "Info Entities ${this.entities}" }
    logger.info { "Info UserTimeLine ${this.userTimeline}" }
    logger.info { "Info UserTimeLine ${this.userTimeline.dialogs.size}" }
    logger.info { "Info UserTimeLine ${this.userTimeline.dialogs[0].id}" }
    logger.info { "Info UserTimeLine ${this.userTimeline.dialogs[0].stories.size}" }
    logger.info { "Info UserTimeLine ${this.userTimeline.dialogs[0].stories[0].starterIntent}" }
    logger.info { "Info UserTimeLine ${this.userTimeline.dialogs[0].stories[0].definition}" }
    logger.info { "Info Json Dialogs ${mapper.writeValueAsString(this.userTimeline.dialogs[0])}"}
    logger.info { "Info Json Dialogs Stories ${mapper.writeValueAsString(this.userTimeline.dialogs[0].stories[0])}"}
    logger.info { "Info UserText ${this.userText}" }
    logger.info { "Info Story ${this.story}" }
    logger.info { "Info Step ${this.step}" }
    logger.info { "Info UserPreferences ${this.userPreferences}" }
    logger.info { "Info Intent ${this.intent}" }
    logger.info { "Info ApplicationId ${this.applicationId}" }
    logger.info { "Info BotId ${this.botId}" }
    logger.info { "Info ContextId ${this.contextId}" }
    logger.info { "Info UserId ${this.userId}" }
    logger.info { "Info StepName ${this.stepName}" }
    logger.info { "Info UserLocale ${this.userLocale}" }
    logger.info { "Info Destination ${this.destination}" }
    logger.info { "Info Location ${this.location}" }
    logger.info { "Info Origin ${this.origin}" }
    logger.info { "Info IdValue ${this.idValue}" }
    //logger.info { "Info Json ${this.json}" }

    val response: EDFStoryResponse =
        EDFStoryResponse(
            //this.action,
            null,
            //this.botDefinition,
            null,
            //this.connectorData,
            null,
            this.dialog,
            this.entities,
            this.userTimeline,
            this.userText,
            this.story,
            //this.step,
            //this.userPreferences,
            null,
            this.intent,
            this.applicationId,
            this.botId,
            this.contextId,
            this.userId,
            //this.stepName,
            this.userLocale,
            //this.idValue,
            //null,
            "Oui que puis-je faire pour vous",
            this.userTimeline.dialogs[0].stories[0]
        )

    withEDFAlloMedia(EDFAlloMediaMessage(true))
    end(mapper.writeValueAsString(response))
}