package edf.genesys.connector

import ai.tock.bot.connector.ConnectorMessage
import ai.tock.bot.connector.ConnectorType

data class EDFGenesysMessage(
    val goodbye: Boolean? = null
) : ConnectorMessage {

    override val connectorType: ConnectorType = edfAlloMediaConnectorType
}