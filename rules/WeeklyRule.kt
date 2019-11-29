package bedbrains.shared.datatypes.rules

class WeeklyRule(id: String, name: String) : Rule(id, TYPE, name) {

    companion object {
        val TYPE = 1
    }

    var timeSpans: MutableList<WeeklyTimeSpan> = mutableListOf()

    fun sort() {
        timeSpans.sortBy { weeklyTimeSpan ->
            weeklyTimeSpan.start.inMillis()
        }
    }

}