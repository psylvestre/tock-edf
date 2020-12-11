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

package edf.stories

import ai.tock.bot.definition.story
import ai.tock.bot.engine.action.Action
import ai.tock.shared.jackson.mapper
import edf.Utilities
import mu.KotlinLogging

/**
 * The conversation handler.
 */
private val logger = KotlinLogging.logger {}

val conversation = story("xxxxxxxxxx") {
    //cleanup state
    resetDialogState()

    Utilities.logData(this)

    val conversation: List<Action> = this.dialog.allActions()
        .filter { it.applicationId == "bot_edf" }
        .also { println(it) }
        .sortedBy { it.date }
    logger.info { "Info Json All Bot Edf Actions Sorted ${mapper.writeValueAsString(conversation)}"}

    end(mapper.writeValueAsString(conversation))
}