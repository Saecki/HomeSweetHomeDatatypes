package bedbrains.shared.datatypes.rules

import com.fasterxml.jackson.annotation.JsonProperty

abstract class Rule(
        @get:JsonProperty
        val uid: String,
        @get:JsonProperty
        val type: Int,
        @get:JsonProperty
        var name: String
) {

    abstract fun getValue(time: WeeklyTime): RuleValue
}
