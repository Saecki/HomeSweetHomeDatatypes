package bedbrains.shared.datatypes.rules

import bedbrains.shared.datatypes.temperature.Temperature
import com.fasterxml.jackson.annotation.JsonProperty

data class RuleValue(
        @get:JsonProperty
        val uid: String,
        @get:JsonProperty
        var name: String,
        @get:JsonProperty
        var heating: Temperature,
        @get:JsonProperty
        var light: Boolean
) {

    companion object {
        val UNSPECIFIED = RuleValue("", "", Temperature(), false) //TODO generate uid
    }
}