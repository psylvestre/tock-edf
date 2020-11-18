package edf.genesys.provider

import ai.tock.bot.connector.Connector
import ai.tock.bot.connector.ConnectorConfiguration
import ai.tock.bot.connector.ConnectorProvider
import ai.tock.bot.connector.ConnectorType
import edf.genesys.connector.EDFAlloMediaConnector
import edf.genesys.connector.edfAlloMediaConnectorType

internal object EDFAlloMediaConnectorProvider : ConnectorProvider {

    override val connectorType: ConnectorType = edfAlloMediaConnectorType

    override fun connector(connectorConfiguration: ConnectorConfiguration): Connector {
        return EDFAlloMediaConnector(
                connectorConfiguration.connectorId,
                connectorConfiguration.path
        )
    }
}

internal class EDFAlloMediaConnectorProviderService : ConnectorProvider by EDFAlloMediaConnectorProvider
