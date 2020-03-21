package bedbrains.shared.datatypes.rules

import bedbrains.platform.Time
import bedbrains.shared.datatypes.clamp
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

class WeeklyTime {

    companion object {
        val MIN = WeeklyTime(0)
        val MAX = WeeklyTime(6, 23, 59, 59, 999)

        val firstDay: Int
            get() {
                return Time.getFirstWeekDay()
            }

        val now: WeeklyTime
            get() {
                return Time.currentWeeklyTime()
            }

    }

    constructor(day: Int, hour: Int, minute: Int, second: Int, millis: Int) {
        this.day = day
        this.hour = hour
        this.minute = minute
        this.second = second
        this.millis = millis
    }

    @JsonCreator
    constructor(@JsonProperty millis: Int) {
        this.day = millis / (24 * 60 * 60 * 1000) % 7
        this.hour = millis / (60 * 60 * 1000) % 24
        this.minute = millis / (60 * 1000) % 60
        this.second = millis / 1000 % 60
        this.millis = millis % 1000
    }

    constructor()

    var day = 0
        set(value) {
            field = clamp(value, 0, 6)
        }

    var hour = 0
        set(value) {
            field = clamp(value, 0, 23)
        }

    var minute = 0
        set(value) {
            field = clamp(value, 0, 59)
        }

    var second: Int = 0
        set(value) {
            field = clamp(value, 0, 59)
        }

    var millis = 0
        set(value) {
            field = clamp(value, 0, 999)
        }

    var localizedDay: Int
        set(value) {
            day = (firstDay + value) % 7
        }
        get() {
            return (7 - firstDay + day) % 7
        }

    val inDays: Double
        get() {
            return day + hour / 60.0 + minute / (24.0 * 60.0) + second / (24.0 * 60.0 * 60.0) + millis / (24.0 * 60.0 * 60.0 * 1000.0)
        }

    val inHours: Double
        get() {
            return day * 24 + hour + minute / 60.0 + second / (60.0 * 60.0) + millis / (60.0 * 60.0 * 1000.0)
        }

    val inMinutes: Double
        get() {
            return day * 24 * 60 + hour * 60 + minute + second / 60.0 + millis / (60.0 * 1000.0)
        }

    val inSeconds: Double
        get() {
            return day * 24 * 60 * 60 + hour * 60 * 60 + minute * 60 + second + millis / 1000.0
        }

    @get:JsonProperty
    val inMillis: Int
        get() {
            return day * 24 * 60 * 60 * 1000 + hour * 60 * 60 * 1000 + minute * 60 * 1000 + second * 1000 + millis
        }

    val inDailyHours: Double
        get() {
            return hour + minute / 60.0 + second / (60.0 * 60.0) + millis / (60.0 * 60.0 * 1000.0)
        }

    val inDailyMinutes: Double
        get() {
            return hour * 60 + minute + second / 60.0 + millis / (60.0 * 1000.0)
        }

    val inDailySeconds: Double
        get() {
            return hour * 60 * 60 + minute * 60 + second + millis / 1000.0
        }

    val inDailyMillis: Int
        get() {
            return hour * 60 * 60 * 1000 + minute * 60 * 1000 + second * 1000 + millis
        }

    fun before(other: WeeklyTime): Boolean {
        return this.inMillis < other.inMillis
    }

    fun localizedBefore(other: WeeklyTime): Boolean {
        return this.localizedDay < other.localizedDay || this.localizedDay == other.localizedDay && this.inDailyMillis > other.inDailyMillis
    }

    fun after(other: WeeklyTime): Boolean {
        return this.inMillis > other.inMillis
    }

    fun localizedAfter(other: WeeklyTime): Boolean {
        return this.localizedDay > other.localizedDay || this.localizedDay == other.localizedDay && this.inDailyMillis > other.inDailyMillis
    }

    override fun equals(other: Any?): Boolean = when (other) {
        is WeeklyTime -> other.hashCode() == hashCode()
        else -> false
    }

    override fun hashCode(): Int {
        return inMillis
    }
}