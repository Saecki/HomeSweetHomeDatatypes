package bedbrains.shared.datatypes.rules

import android.annotation.TargetApi
import android.os.Build
import bedbrains.homesweethomeandroidclient.Tools
import java.time.LocalDateTime
import java.util.*

class WeeklyTime {

    var day = 0
        set(day) {
            field = Tools.clamp(day, 0, 6)
        }

    var hour = 0
        set(hour) {
            field = Tools.clamp(hour, 0, 23)
        }

    var minute = 0
        set(minute) {
            field = Tools.clamp(minute, 0, 59)
        }

    var second: Int = 0
        set(second) {
            field = Tools.clamp(second, 0, 59)
        }

    var millis = 0
        set(millis) {
            field = Tools.clamp(millis, 0, 999)
        }

    companion object {
        val MIN = WeeklyTime(0)
        val MAX = WeeklyTime(6, 23, 59, 59, 999)

        fun now(): WeeklyTime {
            val sdkVersion = Build.VERSION.SDK_INT
            if (sdkVersion < Build.VERSION_CODES.O) {
                val day = Calendar.DAY_OF_WEEK
                val hour = Calendar.HOUR
                val minute = Calendar.MINUTE
                val second = Calendar.SECOND
                val millis = Calendar.MILLISECOND

                return WeeklyTime(day, hour, minute, second, millis)

            } else @TargetApi(Build.VERSION_CODES.O) {
                val dateTime = LocalDateTime.now()
                val day = dateTime.dayOfWeek.value
                val hour = dateTime.hour
                val minute = dateTime.minute
                val second = dateTime.second
                val millis = dateTime.nano / 1000000

                return WeeklyTime(day, hour, minute, second, millis)
            }
        }
    }

    constructor(day: Int, hour: Int, minute: Int, second: Int, millis: Int) {
        this.day = day
        this.hour = hour
        this.minute = minute
        this.second = second
        this.millis = millis
    }

    constructor(millis: Int) {
        this.day = millis / (24 * 60 * 60 * 1000) % 7
        this.hour = millis / (60 * 60 * 1000) % 24
        this.minute = millis / (60 * 1000) % 60
        this.second = millis / 1000 % 60
        this.millis = millis % 1000
    }

    constructor()

    fun inDays(): Double {
        return day + hour / 60.0 + minute / (24.0 * 60.0) + second / (24.0 * 60.0 * 60.0) + millis / (24.0 * 60.0 * 60.0 * 1000.0)
    }

    fun inHours(): Double {
        return day * 24 + hour + minute / 60.0 + second / (60.0 * 60.0) + millis / (60.0 * 60.0 * 1000.0)
    }

    fun inMinutes(): Double {
        return day * 24 * 60 + hour * 60 + minute + second / 60.0 + millis / (60.0 * 1000.0)
    }

    fun inSeconds(): Double {
        return day * 24 * 60 * 60 + hour * 60 * 60 + minute * 60 + second + millis / 1000.0
    }

    fun inMillis(): Int {
        return day * 24 * 60 * 60 * 1000 + hour * 60 * 60 * 1000 + minute * 60 * 1000 + second * 1000 + millis
    }

    fun inDailyHours(): Double {
        return hour + minute / 60.0 + second / (60.0 * 60.0) + millis / (60.0 * 60.0 * 1000.0)
    }

    fun inDailyMinutes(): Double {
        return hour * 60 + minute + second / 60.0 + millis / (60.0 * 1000.0)
    }

    fun inDailySeconds(): Double {
        return hour * 60 * 60 + minute * 60 + second + millis / 1000.0
    }

    fun inDailyMillis(): Int {
        return hour * 60 * 60 * 1000 + minute * 60 * 1000 + second * 1000 + millis
    }

    fun before(time: WeeklyTime): Boolean {
        return this.inMillis() < time.inMillis()
    }

    fun after(time: WeeklyTime): Boolean {
        return this.inMillis() > time.inMillis()
    }

    override fun equals(other: Any?): Boolean = when (other) {
        is WeeklyTime -> other.hashCode() == hashCode()
        else -> false
    }

    override fun hashCode(): Int {
        var result = day
        result = 31 * result + hour
        result = 31 * result + minute
        result = 31 * result + second
        result = 31 * result + millis
        return result
    }
}