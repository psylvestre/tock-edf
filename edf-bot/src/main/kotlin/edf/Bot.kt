package edf

import ai.tock.bot.definition.bot
import edf.stories.bonjour
import edf.stories.conversation
import edf.stories.demarrage
import edf.stories.greetings

val edfBot = bot(
        "bot_edf",
        stories =
        listOf(
                demarrage,
                greetings,
                bonjour,
                conversation
        ),
        hello = greetings
)