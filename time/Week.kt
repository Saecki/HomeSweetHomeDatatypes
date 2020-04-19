package bedbrains.shared.datatypes.time

import bedbrains.shared.datatypes.deepCopy
import bedbrains.shared.datatypes.rules.RuleValue

class Week : ArrayList<WeeklyTimeSpan>() {

    private val timeSpans = mutableListOf<WeeklyTimeSpan>()

    override fun add(element: WeeklyTimeSpan): Boolean {

        if (this.overlays(element))
            return false

        super.add(element)

        return true
    }

    override fun addAll(elements: Collection<WeeklyTimeSpan>): Boolean {
        return elements.map { this.add(it) }.any { it }
    }

    override fun add(index: Int, element: WeeklyTimeSpan) {}

    override fun addAll(index: Int, elements: Collection<WeeklyTimeSpan>): Boolean {
        return false
    }

    fun contains(time: WeeklyTime): Boolean {
        for (t in this) {
            if (t.contains(time))
                return true
        }

        return false
    }

    fun overlays(timeSpan: WeeklyTimeSpan): Boolean {
        for (t in this) {
            if (t.uid != timeSpan.uid && t.overlays(timeSpan))
                return true
        }

        return false
    }

    fun applyToAll(value: RuleValue) {
        this.map {
            it.value = value.deepCopy()
        }
    }
}