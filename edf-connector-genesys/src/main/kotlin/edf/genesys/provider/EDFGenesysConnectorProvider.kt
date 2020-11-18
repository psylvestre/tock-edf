package edf.genesys.provider

import ai.tock.bot.connector.Connector
import ai.tock.bot.connector.ConnectorConfiguration
import ai.tock.bot.connector.ConnectorProvider
import ai.tock.bot.connector.ConnectorType
import edf.genesys.connector.EDFGenesysConnector
import edf.genesys.connector.edfGenesysConnectorType

internal object EDFGenesysConnectorProvider : ConnectorProvider {

    override val connectorType: ConnectorType = edfGenesysConnectorType

    override fun connector(connectorConfiguration: ConnectorConfiguration): Connector {
        return EDFGenesysConnector(
                connectorConfiguration.connectorId,
                connectorConfiguration.path
        )
    }
}

internal class EDFGenesysConnectorProviderService : ConnectorProvider by EDFGenesysConnectorProvider
