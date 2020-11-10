package edf

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY
import ai.tock.bot.connector.ConnectorMessage
import ai.tock.bot.connector.ConnectorType

data class EDFButton(val title: String, val payload: String? = null)

@JsonInclude(NON_EMPTY)
data class EDFMessage(val text: String, val buttons: List<EDFButton> = emptyList()) : ConnectorMessage {
    @get:JsonIgnore
    override val connectorType: ConnectorType = edfRestConnectorType
}

internal data class EDFConnectorResponse(val responses: List<EDFMessage>)