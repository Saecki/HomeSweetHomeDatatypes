package bedbrains.shared.datatypes.devices

import bedbrains.shared.datatypes.temperature.Temperature
import com.fasterxml.jackson.annotation.JsonProperty

class Heating(
    uid: String,
    room: String,
    name: String,
    @field:JsonProperty var actualTemp: Temperature
) : Device(uid, TYPE, room, name) {

    companion object {
        const val TYPE = 1
    }

    @field:JsonProperty
    var targetTemp = Temperature()
        get() = rule?.value?.heating ?: field
        set(value) {
            if (rule?.value?.heating != null)
                rule?.value?.heating = value
            else
                field = value
        }

    override fun equals(other: Any?): Boolean = when (other) {
        is Heating -> {
            super.equals(other) &&
                this.actualTemp == other.actualTemp &&
                this.targetTemp == other.targetTemp
        }
        else -> false
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + actualTemp.hashCode()
        result = 31 * result + targetTemp.hashCode()
        return result
    }
}