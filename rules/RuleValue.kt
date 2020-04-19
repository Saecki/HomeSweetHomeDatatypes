package bedbrains.shared.datatypes.rules

import bedbrains.platform.UIDProvider
import bedbrains.shared.datatypes.Unique
import bedbrains.shared.datatypes.temperature.Temperature
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class RuleValue(
    @field:JsonProperty
    override val uid: String,
    @field:JsonProperty
    var name: String,
    @field:JsonProperty
    var heating: Temperature,
    @field:JsonProperty
    var light: Boolean
) : Unique, Serializable {

    companion object {
        val UNSPECIFIED
            get() = RuleValue(UIDProvider.newUID, "", Temperature(), false)
    }
}