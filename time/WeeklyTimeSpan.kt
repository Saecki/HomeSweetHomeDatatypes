package bedbrains.shared.datatypes.time

import bedbrains.platform.UIDProvider
import bedbrains.shared.datatypes.Unique
import bedbrains.shared.datatypes.rules.RuleValue
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

class WeeklyTimeSpan(
    @field:JsonProperty override val uid: String,
    @field:JsonProperty var value: RuleValue,
    @field:JsonProperty var start: WeeklyTime,
    @field:JsonProperty var end: WeeklyTime
) : Unique, Comparable<WeeklyTimeSpan>, Serializable {

    companion object {
        val UNSPECIFIED
            get() = WeeklyTimeSpan(
                UIDProvider.newUID,
                RuleValue.UNSPECIFIED,
                WeeklyTime.localMin,
                WeeklyTime.localMin
            )
    }

    constructor(start: WeeklyTime, end: WeeklyTime) :
        this(UIDProvider.newUID, RuleValue.UNSPECIFIED, start, end)

    val length: WeeklyTime
        get() = if (start <= end) end - start
        else WeeklyTime.MAX - start + end

    fun contains(time: WeeklyTime): Boolean {
        return if (start <= end) {
            time > start && time < end
        } else {
            time > start || time < end
        }
    }

    fun overlays(other: WeeklyTimeSpan): Boolean {
        return this.contains(other.start)
            || this.contains(other.end)
            || other.contains(this.start)
            || other.contains(this.end)
    }

    override fun compareTo(other: WeeklyTimeSpan): Int = when {
        this.length == other.length -> 0
        this.length < other.length -> -1
        else -> 1
    }
}
