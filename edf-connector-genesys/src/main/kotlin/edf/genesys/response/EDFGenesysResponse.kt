package edf.genesys.response

data class EDFGenesysResponse(
        val sessionId: String,
        val conversation: List<EDFSentence>
)