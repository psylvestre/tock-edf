package edf

import ai.tock.bot.connector.Connector
import ai.tock.bot.connector.ConnectorConfiguration
import ai.tock.bot.connector.ConnectorProvider
import ai.tock.bot.connector.ConnectorType
import ai.tock.bot.connector.ConnectorTypeConfiguration
import ai.tock.shared.resourceAsString

internal object EDFRestConnectorProvider : ConnectorProvider {

    override val connectorType: ConnectorType get() = edfRestConnectorType

    override fun connector(connectorConfiguration: ConnectorConfiguration): Connector {
        with(connectorConfiguration) {
            return EDFRestConnector(
                    connectorId,
                    path
            )
        }
    }

    override fun configuration(): ConnectorTypeConfiguration =
        ConnectorTypeConfiguration(
                edfRestConnectorType,
            svgIcon = resourceAsString("/edfConnector.svg")
        )
}

//used in file META-INF/services/ai.tock.bot.connector.ConnectorProvider
internal class EDFRestConnectorProviderService : ConnectorProvider by EDFRestConnectorProvider