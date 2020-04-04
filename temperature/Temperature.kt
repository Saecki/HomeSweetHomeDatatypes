package bedbrains.shared.datatypes.temperature

import bedbrains.shared.datatypes.clamp
import com.fasterxml.jackson.annotation.JsonProperty

class Temperature : Comparable<Temperature> {

    enum class Unit(val unit: String, val index: Int) {
        KELVIN("K", 0),
        CELSIUS("°C", 1),
        FAHRENHEIT("°F", 2)
    }

    companion object {
        val MIN = Temperature(0.0, Unit.KELVIN)
        val DEFAULT_UNIT = Unit.CELSIUS

        const val DEFAULT_DECIMALS = 1
        var globalUnit = DEFAULT_UNIT
        var globalDecimals = DEFAULT_DECIMALS
    }

    constructor(kelvin: Double, unit: Unit) {
        set(kelvin, unit)
    }

    constructor()


    @field:JsonProperty
    var kelvin = 273.0
        set(value) {
            field = clamp(value, 0.0, Double.MAX_VALUE)
        }

    var celsius: Double
        get() {
            return kelvin - 273
        }
        set(value) {
            kelvin = value + 273
        }

    var fahrenheit: Double
        get() {
            return (9.0 / 5) * (kelvin - 273) + 32
        }
        set(value) {
            this.kelvin = (5.0 / 9) * (value - 32) + 273
        }

    var global: Double
        get() {
            return get(globalUnit)
        }
        set(value) {
            set(value, globalUnit)
        }

    fun get(unit: Unit): Double {
        return when (unit) {
            Unit.KELVIN -> kelvin
            Unit.CELSIUS -> celsius
            Unit.FAHRENHEIT -> fahrenheit
        }
    }

    fun set(temp: Double, unit: Unit) {
        when (unit) {
            Unit.KELVIN -> kelvin = temp
            Unit.CELSIUS -> celsius = temp
            Unit.FAHRENHEIT -> fahrenheit = temp
        }
    }

    fun formatGlobal(appendUnit: Boolean): String {
        return format(
            globalUnit,
            globalDecimals, appendUnit
        )
    }

    fun format(unit: Unit, decimals: Int, appendUnit: Boolean): String {
        return roundTo(get(unit), decimals) + if (appendUnit) unit.unit else ""
    }

    private fun roundTo(value: Double, decimals: Int): String {
        val pattern: String = "%." + decimals + "f"
        return pattern.format(value)
    }

    override fun equals(other: Any?): Boolean = when (other) {
        is Temperature -> other.hashCode() == hashCode()
        else -> false
    }

    override fun hashCode(): Int {
        return kelvin.hashCode()
    }

    override fun compareTo(other: Temperature): Int = when {
        this.kelvin == other.kelvin -> 0
        this.kelvin < other.kelvin -> -1
        else -> 1
    }
}

val Number.kelvin: Temperature
    get() = Temperature(this.toDouble(), Temperature.Unit.KELVIN)

val Number.celsius: Temperature
    get() = Temperature(this.toDouble(), Temperature.Unit.CELSIUS)

val Number.fahrenheit: Temperature
    get() = Temperature(this.toDouble(), Temperature.Unit.FAHRENHEIT)
