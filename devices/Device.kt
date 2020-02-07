package bedbrains.shared.datatypes.devices

import bedbrains.shared.datatypes.rules.Rule
import com.fasterxml.jackson.annotation.JsonTypeInfo

abstract class Device(val uid: String, val type: Int, var room: String, var name: String) {

    @JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
    abstract class JSON(val uid: String, val type: Int, val room: String, val name: String, val tags: List<String>, val rules: List<Rule.JSON>) {

        abstract fun toDevice(): Device
    }

    var tags: List<String> = ArrayList()
    var rules: List<Rule> = ArrayList()

    abstract fun toJSON(): JSON

    override fun equals(other: Any?): Boolean = when (other) {
        is Device -> {
            this.uid == other.uid &&
                    this.type == other.type &&
                    this.room == other.room &&
                    this.name == other.name &&
                    this.tags == other.tags &&
                    this.rules == other.rules
        }
        else -> false
    }
}