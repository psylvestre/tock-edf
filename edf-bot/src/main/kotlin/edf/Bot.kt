package edf

import ai.tock.bot.definition.bot
import edf.stories.*

val edfBot = bot(
        "bot_edf",
        stories =
        listOf(
                demarrage,
                greetings,
                bonjour,
                billing,
                conversation
        ),
        hello = greetings
)