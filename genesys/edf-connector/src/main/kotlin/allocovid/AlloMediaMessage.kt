package allocovid

import ai.tock.bot.connector.ConnectorMessage
import ai.tock.bot.connector.ConnectorType

data class AlloMediaMessage(
    val goodbye: Boolean? = null
) : ConnectorMessage {

    override val connectorType: ConnectorType = alloMediaConnectorType
}