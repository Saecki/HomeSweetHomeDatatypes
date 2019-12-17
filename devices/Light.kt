package bedbrains.shared.datatypes.devices

class Light(id: String, room: String, name: String, var state: Boolean) : Device(id, TYPE, room, name) {

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