package edf.allomedia.request

import java.util.*

data class EDFAlloMediaRequest(
        val session: EDFAlloMediaSession,
        val text: String?,
        val intent: String?,
        val locale: Locale? = null
)