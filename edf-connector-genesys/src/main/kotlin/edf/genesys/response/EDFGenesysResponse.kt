package edf.genesys.response

data class EDFGenesysResponse(
        val sessionId: String,
        val outputText: EDFOutputText,
        val conversationEnd: Boolean? = null
)