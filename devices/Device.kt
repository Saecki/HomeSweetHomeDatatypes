package bedbrains.shared.datatypes.devices

import bedbrains.shared.datatypes.rules.Rule
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
abstract class Device(
        @get:JsonProperty
        val uid: String,
        @get:JsonProperty
        val type: Int,
        @get:JsonProperty
        var room: String,
        @get:JsonProperty
        var name: String) {

    var tags: List<String> = ArrayList()
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