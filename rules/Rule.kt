package bedbrains.shared.datatypes.rules

import bedbrains.shared.datatypes.Unique
import bedbrains.shared.datatypes.time.WeeklyTime
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
abstract class Rule(
    @field:JsonProperty
    override val uid: String,
    @field:JsonProperty
    val type: Int,
    @field:JsonProperty
    var name: String
) : Unique {

    abstract fun getValue(time: WeeklyTime): RuleValue
}
