package bedbrains.shared.datatypes.rules

import bedbrains.shared.datatypes.Temperature

data class RuleValue(val uid: String, var name: String, var heating: Temperature, var light: Boolean) {

    companion object {
        val UNSPECIFIED = RuleValue("", "", Temperature(), false) //TODO generate uid
    }

}