package bedbrains.shared.datatypes.devices

import bedbrains.platform.DataProvider
import bedbrains.shared.datatypes.Unique
import bedbrains.shared.datatypes.rules.Rule
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.io.Serializable

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
abstract class Device(
    @field:JsonProperty override val uid: String,
    @field:JsonProperty val type: Int,
    @field:JsonProperty var room: String,
    @field:JsonProperty var name: String
) : Unique, Serializable {

    @field:JsonProperty
    var tags: MutableSet<String> = mutableSetOf()

    @field:JsonProperty
    var ruleUID: String? = null

    var rule: Rule?
        get() = DataProvider.rules.find { it.uid == ruleUID }
        set(value) {
            if (value == null) {
                ruleUID = null
            } else {
                ruleUID = value.uid
                DataProvider.upsertRule(value)
            }
        }

    override fun equals(other: Any?): Boolean = when (other) {
        is Device -> {
            this.uid == other.uid &&
                this.type == other.type &&
                this.room == other.room &&
                this.name == other.name &&
                this.tags == other.tags &&
                this.ruleUID == other.ruleUID
        }
        else -> false
    }

    override fun hashCode(): Int {
        var result = uid.hashCode()
        result = 31 * result + type
        result = 31 * result + room.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + tags.hashCode()
        result = 31 * result + ruleUID.hashCode()
        return result
    }
}