package edf.allomedia.provider

import ai.tock.bot.connector.Connector
import ai.tock.bot.connector.ConnectorConfiguration
import ai.tock.bot.connector.ConnectorProvider
import ai.tock.bot.connector.ConnectorType
import edf.allomedia.connector.EDFAlloMediaConnector
import edf.allomedia.connector.edfAlloMediaConnectorType

internal object AlloMediaConnectorProvider : ConnectorProvider {

    override val connectorType: ConnectorType = edfAlloMediaConnectorType

    override fun connector(connectorConfiguration: ConnectorConfiguration): Connector {
        return EDFAlloMediaConnector(
                connectorConfiguration.connectorId,
                connectorConfiguration.path
        )
    }
}

internal class AlloMediaConnectorProviderService : ConnectorProvider by AlloMediaConnectorProvider
