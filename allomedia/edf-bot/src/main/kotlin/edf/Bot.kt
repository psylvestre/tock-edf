package ai.tock.bot.edf

import ai.tock.bot.definition.bot
import ai.tock.bot.open.data.story.*

val edfBot = bot(
        "bot_edf",
        stories =
        listOf(
                greetings,
                bonjour
        ),
        hello = greetings
)