package bedbrains.shared.datatypes.rules

class WeeklyTimeSpan {

    data class JSON(val value: RuleValue.JSON, val start: WeeklyTime.JSON, val end: WeeklyTime.JSON) {

        fun toWeeklyTimeSpan() = WeeklyTimeSpan(start.toWeeklyTime(), end.toWeeklyTime()).also {
            it.value = value.toRuleValue()
        }
    }

    constructor(start: WeeklyTime, end: WeeklyTime) {
        this.start = start
        this.end = end
    }

    constructor()

    var value: RuleValue = RuleValue.UNSPECIFIED
    var start: WeeklyTime = WeeklyTime()
    var end: WeeklyTime = WeeklyTime()

    fun toJSON(): JSON = JSON(value.toJSON(), start.toJSON(), end.toJSON())

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
