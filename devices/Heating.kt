package bedbrains.shared.datatypes.devices

import bedbrains.shared.datatypes.Temperature
import bedbrains.shared.datatypes.rules.Rule

class Heating(uid: String, room: String, name: String, var actualTemp: Temperature, var targetTemp: Temperature) : Device(uid, TYPE, room, name) {

    class JSON(uid: String, type: Int, room: String, name: String, tags: List<String>, rules: List<Rule.JSON>, val actualTemp: Temperature.JSON, val targetTemp: Temperature.JSON) :
            Device.JSON(uid, type, room, name, tags, rules) {

        override fun toDevice() = Heating(uid, room, name, actualTemp.toTemperature(), targetTemp.toTemperature()).also {
            it.tags = tags
            it.rules = rules.map { it.toRule() }
        }
    }

    companion object {
        const val TYPE = 1
    }

    var extended = false

    override fun toJSON(): Device.JSON = JSON(uid, type, room, name, tags, rules.map { it.toJSON() }, actualTemp.json, targetTemp.json)

    override fun equals(other: Any?): Boolean = when (other) {
        is Heating -> {
            super.equals(other) &&
                    this.actualTemp == other.actualTemp &&
                    this.targetTemp == other.targetTemp
        }
        else -> false
    }

}