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

import ai.tock.bot.connector.ga.gaMessage
import ai.tock.bot.connector.ga.withGoogleAssistant
import ai.tock.bot.connector.messenger.buttonsTemplate
import ai.tock.bot.connector.messenger.postbackButton
import ai.tock.bot.connector.messenger.withMessenger
import ai.tock.bot.connector.slack.slackAttachment
import ai.tock.bot.connector.slack.slackButton
import ai.tock.bot.connector.slack.slackMessage
import ai.tock.bot.connector.slack.withSlack
import ai.tock.bot.definition.story
import ai.tock.bot.open.data.story.destination
import ai.tock.bot.open.data.story.location
import ai.tock.bot.open.data.story.origin
import mu.KotlinLogging
import opendata.sampleButton
import opendata.sampleMessage
import opendata.withSample
import org.litote.kmongo.json
import org.litote.kmongo.util.idValue

/**
 * The greetings handler.
 */
private val logger = KotlinLogging.logger {}

val greetings = story("hello") {
    //cleanup state
    resetDialogState()

    logger.info { "Info Action ${this.action}" }
    logger.info { "Info BotDefinition ${this.botDefinition}" }
    logger.info { "Info ConnectorData ${this.connectorData}" }
    logger.info { "Info Dialog ${this.dialog}" }
    logger.info { "Info Entities ${this.entities}" }
    logger.info { "Info UserTimeLine ${this.userTimeline}" }
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
    logger.info { "Info Json ${this.json}" }


    end("Oui que puis-je faire pour vous")
}