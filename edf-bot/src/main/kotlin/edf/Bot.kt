package edf

import ai.tock.bot.definition.bot

val edfBot = bot(
        "bot_edf",
        stories =
        listOf(
                greetings,
                bonjour
        ),
        hello = greetings
)