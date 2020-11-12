package edf.allomedia.connector

import ai.tock.bot.connector.ConnectorMessage
import ai.tock.bot.connector.ConnectorType
import edf.allomedia.connector.edfAlloMediaConnectorType

data class EDFAlloMediaMessage(
    val goodbye: Boolean? = null
) : ConnectorMessage {

    override val connectorType: ConnectorType = edfAlloMediaConnectorType
}