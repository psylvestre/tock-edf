package edf

import ai.tock.bot.definition.bot

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