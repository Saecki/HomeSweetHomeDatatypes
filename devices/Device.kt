package bedbrains.shared.datatypes.devices

import bedbrains.shared.datatypes.rules.Rule

abstract class Device(val uid: String, val type: Int, var room: String, var name: String) {

    var tags: List<String> = ArrayList()
    var rules: List<Rule> = ArrayList()

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