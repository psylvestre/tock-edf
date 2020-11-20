package allocovid

import java.util.*

data class AlloMediaRequest(
        val session: AlloMediaSession,
        val text: String?,
        val intent: String?,
        val locale: Locale? = null
)