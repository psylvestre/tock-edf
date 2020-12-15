package edf.allomedia.response

data class EDFAlloMediaResponse(
        val sessionId: String,
        val outputText: EDFOutputText,
        val call: String? = null
)