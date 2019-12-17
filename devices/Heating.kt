package bedbrains.shared.datatypes.devices

import bedbrains.shared.datatypes.Temperature

class Heating(id: String, room: String, name: String, var actualTemp: Temperature, var targetTemp: Temperature) : Device(id, TYPE, room, name) {

    var extended = false

    companion object {
        const val TYPE = 1
    }

    override fun equals(other: Any?): Boolean = when (other) {
        is Heating -> {
            super.equals(other) &&
                    this.actualTemp == other.actualTemp &&
                    this.targetTemp == other.targetTemp
        }
        else -> false
    }

}