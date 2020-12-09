package edf.allomedia.request

import java.util.*

data class EDFAlloMediaRequest(
        val session: EDFAlloMediaSession,
        var text: String?,
        val intent: String?,
        val locale: Locale? = null
)