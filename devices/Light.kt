package bedbrains.shared.datatypes.devices

import com.fasterxml.jackson.annotation.JsonProperty

class Light(
    uid: String,
    room: String,
    name: String
) : Device(uid, TYPE, room, name) {

    @field:JsonProperty
    var isOn: Boolean = false
        get() = rule?.value?.light ?: field
        set(value) {
            if (rule?.value?.light != null)
                rule?.value?.light = value
            else
                field = value
        }

    companion object {
        const val TYPE = 2
    }

    override fun equals(other: Any?): Boolean = when (other) {
        is Light -> {
            super.equals(other) &&
                this.isOn == other.isOn
        }
        else -> false
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + isOn.hashCode()
        return result
    }

}