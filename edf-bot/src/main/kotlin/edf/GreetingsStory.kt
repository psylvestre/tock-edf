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
import opendata.sampleButton
import opendata.sampleMessage
import opendata.withSample

/**
 * The greetings handler.
 */
val greetings = story("hello") {
    //cleanup state
    resetDialogState()

    end("Oui que puis-je faire pour vous")
}