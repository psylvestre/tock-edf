package edf.allomedia

data class AlloMediaResponse(
        val sessionId: String,
        val outputText: OutputText,
        val conversationEnd: Boolean? = null
)