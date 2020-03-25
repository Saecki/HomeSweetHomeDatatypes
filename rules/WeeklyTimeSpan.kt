package bedbrains.shared.datatypes.rules

import com.fasterxml.jackson.annotation.JsonProperty

class WeeklyTimeSpan(
    @field:JsonProperty
    var value: RuleValue,
    @field:JsonProperty
    var start: WeeklyTime,
    @field:JsonProperty
    var end: WeeklyTime
) {

    companion object {
        val UNSPECIFIED = WeeklyTimeSpan(RuleValue.UNSPECIFIED, WeeklyTime(), WeeklyTime())
    }

    constructor(start: WeeklyTime, end: WeeklyTime) : this(RuleValue.UNSPECIFIED, start, end)

    fun length(): WeeklyTime {
        val max = WeeklyTime.MAX.inMillis
        return WeeklyTime((((end.inMillis - start.inMillis) % max) + max) % max)
    }

    fun isActive(time: WeeklyTime): Boolean {
        return if (start.before(end)) {
            time.after(start) && time.before(end)
        } else {
            time.after(start) || time.before(end)
        }
    }

}
