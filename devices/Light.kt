package bedbrains.shared.datatypes.devices

import com.fasterxml.jackson.annotation.JsonProperty

class Light(
        uid: String,
        room: String,
        name: String,
        @get:JsonProperty
        var state: Boolean
) : Device(uid, TYPE, room, name) {

    companion object {
        const val TYPE = 2
    }

    override fun equals(other: Any?): Boolean = when (other) {
        is Light -> {
            super.equals(other) &&
                    this.state == other.state
        }
        else -> false
    }

}