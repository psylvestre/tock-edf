package edf.genesys.response

import java.time.Instant

data class EDFSentence(
        val text: CharSequence?,
        val intent: String?,
        val date: Instant?
)