package edf.genesys.response

import ai.tock.bot.engine.action.Action
import ai.tock.bot.engine.action.SendSentence

data class EDFGenesysResponse(
        val sessionId: String,
        val conversationEnd: Boolean? = null,
        val conversation: List<SendSentence>?
)