package bedbrains.shared.datatypes.devices

import bedbrains.shared.datatypes.Unique
import bedbrains.shared.datatypes.rules.Rule
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
abstract class Device(
    @field:JsonProperty
    override val uid: String,
    @field:JsonProperty
    val type: Int,
    @field:JsonProperty
    var room: String,
    @field:JsonProperty
    var name: String
) : Unique {

    @field:JsonProperty
    var tags: List<String> = ArrayList()

    @field:JsonProperty
    var rules: List<Rule> = ArrayList()

    override fun equals(other: Any?): Boolean = when (other) {
        is Device -> {
            this.uid == other.uid &&
                this.type == other.type &&
                this.room == other.room &&
                this.name == other.name &&
                this.tags == other.tags &&
                this.rules == other.rules
        }
        else -> false
    }
}