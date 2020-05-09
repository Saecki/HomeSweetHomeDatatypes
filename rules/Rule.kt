package bedbrains.shared.datatypes.rules

import bedbrains.shared.datatypes.Unique
import bedbrains.shared.datatypes.time.WeeklyTime
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.io.Serializable

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
abstract class Rule(
    @field:JsonProperty override val uid: String,
    @field:JsonProperty val type: Int,
    @field:JsonProperty var name: String
) : Unique, Serializable {

    abstract val value: RuleValue

    override fun equals(other: Any?): Boolean = when (other) {
        is Rule -> {
            this.uid == other.uid &&
                this.type == other.type &&
                this.name == other.name
        }
        else -> false
    }

    override fun hashCode(): Int {
        var result = uid.hashCode()
        result = 31 * result + type
        result = 31 * result + name.hashCode()
        return result
    }
}
