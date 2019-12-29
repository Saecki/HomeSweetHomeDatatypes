package bedbrains.shared.datatypes.rules

class WeeklyTimeSpan {

    constructor(start: WeeklyTime, end: WeeklyTime) {
        this.start = start
        this.end = end
    }

    constructor()

    val value: RuleValue = RuleValue.UNSPECIFIED
    var start: WeeklyTime = WeeklyTime()
    var end: WeeklyTime = WeeklyTime()

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
