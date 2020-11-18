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
import edf.Utilities
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

    Utilities.logData(this)

    val response: EDFStoryResponse =
        EDFStoryResponse(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            this.intent,
            this.applicationId,
            this.botId,
            this.contextId,
            this.userId,
            null,
            null,
            null,
            null,
            "Oui ok, je peux vous aider",
            null
        )

//    withEDFAlloMedia(EDFAlloMediaMessage(true))
    end(mapper.writeValueAsString(response))
}