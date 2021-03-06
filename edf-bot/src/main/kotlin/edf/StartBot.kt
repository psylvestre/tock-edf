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

package edf

import ai.tock.bot.importNlpDump
import ai.tock.bot.registerAndInstallBot
import ai.tock.translator.Translator

fun main(args: Array<String>) {
    StartBot.start()
}

/**
 * This is the entry point of the bot.
 */
object StartBot {

    fun start() {
        //set default zone id, these are french trains, so...
        System.setProperty("tock_default_zone", "Europe/Paris")
        System.setProperty("tock_default_locale", "fr")

        //enable i18n as two locales are supported
        Translator.enabled = false

        //register the bot
        registerAndInstallBot(edfBot)

        //load NLP model and i18n labels
        importNlpDump("/edf_model.json")


    }
}