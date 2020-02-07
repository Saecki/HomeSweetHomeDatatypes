package bedbrains.shared.datatypes.devices

import bedbrains.shared.datatypes.rules.Rule

class Light(uid: String, room: String, name: String, var state: Boolean) : Device(uid, TYPE, room, name) {

    class JSON(uid: String, type: Int, room: String, name: String, tags: List<String>, rules: List<Rule.JSON>, val state: Boolean) :
            Device.JSON(uid, type, room, name, tags, rules) {

        override fun toDevice() = Light(uid, room, name, state).also {
            it.tags = tags
            it.rules = rules.map { it.toRule() }
        }
    }

    companion object {
        const val TYPE = 2
    }

    override fun toJSON(): Device.JSON = JSON(uid, type, room, name, tags, rules.map { it.toJSON() }, state)

    override fun equals(other: Any?): Boolean = when (other) {
        is Light -> {
            super.equals(other) &&
                    this.state == other.state
        }
        else -> false
    }

}