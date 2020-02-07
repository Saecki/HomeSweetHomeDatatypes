package bedbrains.shared.datatypes.rules

class WeeklyRule(uid: String, name: String) : Rule(uid, TYPE, name) {

    class JSON(uid: String, type: Int, name: String, val timeSpans: List<WeeklyTimeSpan.JSON>) : Rule.JSON(uid, type, name) {

        override fun toRule() = WeeklyRule(uid, name).also {
            it.timeSpans = timeSpans.map { it.toWeeklyTimeSpan() }.toMutableList()
        }
    }

    companion object {
        const val TYPE = 1
    }

    var timeSpans: MutableList<WeeklyTimeSpan> = mutableListOf()

    override fun toJSON(): JSON = JSON(uid, type, name, timeSpans.map { it.toJSON() })

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