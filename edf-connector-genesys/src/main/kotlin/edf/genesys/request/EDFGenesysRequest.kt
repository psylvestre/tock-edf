package edf.genesys.request

import java.util.*

data class EDFGenesysRequest(
        val session: EDFGenesysSession,
        val text: String?,
        val intent: String?,
        val locale: Locale? = null
)