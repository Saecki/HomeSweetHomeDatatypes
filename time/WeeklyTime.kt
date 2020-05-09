package bedbrains.shared.datatypes.time

import bedbrains.platform.Time
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

class WeeklyTime : Comparable<WeeklyTime>, Serializable {

    companion object {
        val MIN: WeeklyTime
            get() = WeeklyTime(0)

        val MAX: WeeklyTime
            get() = WeeklyTime(6, 23, 59, 59, 999)

        val localMin: WeeklyTime
            get() = WeeklyTime().apply { day = firstDay }

        val localMax: WeeklyTime
            get() = WeeklyTime().apply { day = 6 - firstDay }

        val firstDay: Int
            get() = Time.getFirstWeekDay()

        val now: WeeklyTime
            get() = Time.currentWeeklyTime()
    }

    @field:JsonProperty
    private var milliseconds = 0
        set(value) {
            field = Math.floorMod(value, 7 * 24 * 60 * 60 * 1000)
        }

    constructor(day: Int, hour: Int, minute: Int, second: Int, millis: Int) {
        milliseconds = day * 24 * 60 * 60 * 1000 + hour * 60 * 60 * 1000 + minute * 60 * 1000 + second * 1000 + millis
    }

    constructor(millis: Int) {
        milliseconds = millis
    }

    constructor()

    var day: Int
        get() = milliseconds / (24 * 60 * 60 * 1000)
        set(value) {
            milliseconds = value * 24 * 60 * 60 * 1000 + milliseconds % (24 * 60 * 60 * 1000)
        }

    var hour: Int
        get() = milliseconds % (24 * 60 * 60 * 1000) / (60 * 60 * 1000)
        set(value) {
            milliseconds = milliseconds - milliseconds % (24 * 60 * 60 * 1000) + value * 60 * 60 * 1000 + milliseconds % (60 * 60 * 1000)
        }

    var minute: Int
        get() = milliseconds % (60 * 60 * 1000) / (60 * 1000)
        set(value) {
            milliseconds = milliseconds - milliseconds % (60 * 60 * 1000) + value * 60 * 1000 + milliseconds % (60 * 1000)
        }

    var second: Int
        get() = milliseconds % (60 * 1000) / 1000
        set(value) {
            milliseconds = milliseconds - milliseconds % (60 * 1000) + value * 1000 + milliseconds % 1000
        }

    var millisecond: Int
        get() = milliseconds % 1000
        set(value) {
            milliseconds = milliseconds - milliseconds % 1000 + value
        }

    var localizedDay: Int
        set(value) {
            day = (firstDay + value) % 7
        }
        get() {
            return (7 - firstDay + day) % 7
        }

    var inDays: Double
        get() = milliseconds / (24.0 * 60.0 * 60.0 * 1000.0)
        set(value) {
            milliseconds = (value * 24.0 * 60.0 * 60.0 * 1000.0).toInt()
        }

    var inHours: Double
        get() = milliseconds / (60.0 * 60.0 * 1000.0)
        set(value) {
            milliseconds = (value * 60.0 * 60.0 * 1000.0).toInt()
        }

    var inMinutes: Double
        get() = milliseconds / (60.0 * 1000.0)
        set(value) {
            milliseconds = (value * 60.0 * 1000.0).toInt()
        }

    var inSeconds: Double
        get() = milliseconds / 1000.0
        set(value) {
            milliseconds = (value * 1000.0).toInt()
        }

    var inMilliseconds: Int
        get() = milliseconds
        set(value) {
            milliseconds = value
        }

    val inDailyHours: Double
        get() = inDailyMilliseconds / (60.0 * 60.0 * 1000.0)

    val inDailyMinutes: Double
        get() = inDailyMilliseconds / (60.0 * 1000.0)

    val inDailySeconds: Double
        get() = inDailyMilliseconds / 1000.0

    val inDailyMilliseconds: Int
        get() = milliseconds % (24 * 60 * 60 * 1000)

    fun localizedBefore(other: WeeklyTime): Boolean {
        return this.localizedDay < other.localizedDay || this.localizedDay == other.localizedDay && this.inDailyMilliseconds > other.inDailyMilliseconds
    }

    fun localizedAfter(other: WeeklyTime): Boolean {
        return this.localizedDay > other.localizedDay || this.localizedDay == other.localizedDay && this.inDailyMilliseconds > other.inDailyMilliseconds
    }

    override fun equals(other: Any?): Boolean = when (other) {
        is WeeklyTime -> other.hashCode() == hashCode()
        else -> false
    }

    override fun hashCode(): Int {
        return inMilliseconds
    }

    override fun compareTo(other: WeeklyTime): Int = when {
        this.milliseconds == other.milliseconds -> 0
        this.milliseconds < other.milliseconds -> -1
        else -> 1
    }

    operator fun plus(other: WeeklyTime): WeeklyTime {
        return WeeklyTime(this.milliseconds + other.milliseconds)
    }

    operator fun minus(other: WeeklyTime): WeeklyTime {
        return WeeklyTime(this.milliseconds - other.milliseconds)
    }
}

val Number.days: WeeklyTime
    get() = WeeklyTime((this.toDouble() * 24 * 60 * 60 * 1000).toInt())

val Number.hours: WeeklyTime
    get() = WeeklyTime((this.toDouble() * 60 * 60 * 1000).toInt())

val Number.minutes: WeeklyTime
    get() = WeeklyTime((this.toDouble() * 60 * 1000).toInt())

val Number.seconds: WeeklyTime
    get() = WeeklyTime((this.toDouble() * 1000).toInt())

val Number.milliseconds: WeeklyTime
    get() = WeeklyTime(this.toInt())

