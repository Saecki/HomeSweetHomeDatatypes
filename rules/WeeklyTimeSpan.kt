package bedbrains.shared.datatypes.rules

import bedbrains.platform.UIDProvider
import bedbrains.shared.datatypes.Unique
import com.fasterxml.jackson.annotation.JsonProperty

class WeeklyTimeSpan(
    @field:JsonProperty
    override val uid: String,
    @field:JsonProperty
    var value: RuleValue,
    @field:JsonProperty
    var start: WeeklyTime,
    @field:JsonProperty
    var end: WeeklyTime
) : Unique {

    companion object {
        val UNSPECIFIED = WeeklyTimeSpan(UIDProvider.newUID, RuleValue.UNSPECIFIED, WeeklyTime(), WeeklyTime())
    }

    constructor(start: WeeklyTime, end: WeeklyTime) : this(UIDProvider.newUID, RuleValue.UNSPECIFIED, start, end)

    fun length(): WeeklyTime {
        val max = WeeklyTime.MAX.inMilliseconds
        return WeeklyTime((((end.inMilliseconds - start.inMilliseconds) % max) + max) % max)
    }

    fun isActive(time: WeeklyTime): Boolean {
        return if (start.before(end)) {
            time.after(start) && time.before(end)
        } else {
            time.after(start) || time.before(end)
        }
    }

}
