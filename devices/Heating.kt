package bedbrains.shared.datatypes.devices

import bedbrains.shared.datatypes.Temperature

class Heating(
    id: String,
    name: String,
    room: String,
    var actualTemp: Temperature,
    var targetTemp: Temperature
) : Device(id, TYPE, name, room) {

    var extended = false

    companion object {
        const val TYPE = 1
    }
}