package bedbrains.shared.datatypes.rules

import bedbrains.platform.DataProvider
import com.fasterxml.jackson.annotation.JsonProperty

class ValueRule(
    uid: String,
    name: String
) : Rule(uid, TYPE, name) {

    companion object {
        const val TYPE = 2
    }

    @field:JsonProperty
    var valueUID: String? = null

    override var value: RuleValue
        get() = DataProvider.values.find { it.uid == valueUID } ?: RuleValue.UNSPECIFIED
        set(value) {
            valueUID = value.uid
            DataProvider.upsertValue(value)
        }

    override fun equals(other: Any?) = when (other) {
        is ValueRule -> {
            super.equals(other) &&
                this.value == other.value
        }
        else -> false
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }

}
