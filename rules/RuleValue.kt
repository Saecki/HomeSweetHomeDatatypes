package bedbrains.shared.datatypes.rules

import bedbrains.platform.UIDProvider
import bedbrains.shared.datatypes.Unique
import bedbrains.shared.datatypes.temperature.Temperature
import com.fasterxml.jackson.annotation.JsonProperty

data class RuleValue(
    @get:JsonProperty
    override val uid: String,
    @get:JsonProperty
    var name: String,
    @get:JsonProperty
    var heating: Temperature,
    @get:JsonProperty
    var light: Boolean
) : Unique {

    companion object {
        val UNSPECIFIED = RuleValue(UIDProvider.newUID, "", Temperature(), false)
    }
}