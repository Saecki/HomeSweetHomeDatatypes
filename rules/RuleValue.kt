package bedbrains.shared.datatypes.rules

import bedbrains.shared.datatypes.Temperature

data class RuleValue(var heating: Temperature, var light: Boolean) {

    companion object {
        val UNSPECIFIED = RuleValue(Temperature(), false)
    }

}