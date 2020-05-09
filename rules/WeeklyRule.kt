package bedbrains.shared.datatypes.rules

import bedbrains.shared.datatypes.time.Week
import bedbrains.shared.datatypes.time.WeeklyTime
import com.fasterxml.jackson.annotation.JsonProperty

class WeeklyRule(uid: String, name: String) : Rule(uid, TYPE, name) {

    companion object {
        const val TYPE = 1
    }

    override val value: RuleValue
        get() = getValue(WeeklyTime.now)

    @field:JsonProperty
    var timeSpans = Week()


    fun getValue(time: WeeklyTime): RuleValue {
        return timeSpans.find { it.contains(time) }?.value ?: RuleValue.UNSPECIFIED
    }

    override fun equals(other: Any?): Boolean = when (other) {
        is WeeklyRule -> super.equals(other) &&
            this.timeSpans == other.timeSpans
        else -> false
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + timeSpans.hashCode()
        return result
    }
}