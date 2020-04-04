package bedbrains.shared.datatypes.rules

import bedbrains.shared.datatypes.time.Week
import bedbrains.shared.datatypes.time.WeeklyTime
import com.fasterxml.jackson.annotation.JsonProperty

class WeeklyRule(uid: String, name: String) : Rule(uid, TYPE, name) {

    companion object {
        const val TYPE = 1
    }

    @field:JsonProperty
    var timeSpans = Week()

    override fun getValue(time: WeeklyTime): RuleValue {
        timeSpans.forEach {
            if (it.contains(time)) {
                return it.value
            }
        }
        return RuleValue.UNSPECIFIED
    }

    fun applyValueToAllTimeSpans(value: RuleValue) {
        timeSpans.map {
            it.value = value
        }
    }
}