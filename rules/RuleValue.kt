package bedbrains.shared.datatypes.rules

import bedbrains.shared.datatypes.Temperature

data class RuleValue(val uid: String, var name: String, var heating: Temperature, var light: Boolean) {

    data class JSON(val uid: String, var name: String, var heating: Temperature.JSON, var light: Boolean) {

        fun toRuleValue() = RuleValue(uid, name, heating.toTemperature(), light)
    }

    companion object {
        val UNSPECIFIED = RuleValue("", "", Temperature(), false) //TODO generate uid
    }

    fun toJSON(): JSON = JSON(uid, name, heating.json, light)

}