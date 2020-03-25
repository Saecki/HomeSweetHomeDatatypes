package bedbrains.shared.datatypes.rules

import com.fasterxml.jackson.annotation.JsonProperty

class WeeklyRule(uid: String, name: String) : Rule(uid, TYPE, name) {

    companion object {
        const val TYPE = 1
    }

    @field:JsonProperty
    var timeSpans: MutableList<WeeklyTimeSpan> = mutableListOf()

    fun sort() {
        timeSpans.sortBy { weeklyTimeSpan ->
            weeklyTimeSpan.start.inMillis
        }
    }

    override fun getValue(time: WeeklyTime): RuleValue {
        timeSpans.forEach {
            if (it.isActive(time)) {
                return it.value
            }
        }
        return RuleValue.UNSPECIFIED
    }
}