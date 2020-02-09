package bedbrains.shared.datatypes

class Temperature : Comparable<Temperature> {

    data class JSON(val temp: Double) {

        fun toTemperature(): Temperature = temp.kelvin
    }

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

    constructor(temp: Double, unit: Unit) {
        set(temp, unit)
    }

    constructor()

    private var temp: Double = 273.0
        set(temp) {
            field = clamp(temp, 0.0, Double.MAX_VALUE)
        }

    var kelvin: Double
        get() {
            return temp
        }
        set(value) {
            temp = value
        }

    var celsius: Double
        get() {
            return temp - 273
        }
        set(value) {
            temp = value + 273
        }

    var fahrenheit: Double
        get() {
            return (9.0 / 5) * (temp - 273) + 32
        }
        set(value) {
            this.temp = (5.0 / 9) * (value - 32) + 273
        }

    var global: Double
        get() {
            return get(globalUnit)
        }
        set(value) {
            set(value, globalUnit)
        }

    val json: JSON
        get() = JSON(temp)

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
        return format(globalUnit, globalDecimals, appendUnit)
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
        return temp.hashCode()
    }

    override fun compareTo(other: Temperature): Int = when {
        this.temp == other.temp -> 0
        this.temp < other.temp -> -1
        else -> 1
    }
}

val Double.kelvin: Temperature
    get() = Temperature(this, Temperature.Unit.KELVIN)

val Int.kelvin: Temperature
    get() = Temperature(this.toDouble(), Temperature.Unit.KELVIN)

val Double.celsius: Temperature
    get() = Temperature(this, Temperature.Unit.CELSIUS)

val Int.celsius: Temperature
    get() = Temperature(this.toDouble(), Temperature.Unit.CELSIUS)

val Double.fahrenheit: Temperature
    get() = Temperature(this, Temperature.Unit.FAHRENHEIT)

val Int.fahrenheit: Temperature
    get() = Temperature(this.toDouble(), Temperature.Unit.FAHRENHEIT)
