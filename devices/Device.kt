package bedbrains.shared.datatypes.devices

import bedbrains.shared.datatypes.rules.Rule

abstract class Device(val uid: String, val type: Int, var room: String, var name: String) {

    var tags: List<String> = ArrayList()
    var rules: List<Rule> = ArrayList()
}