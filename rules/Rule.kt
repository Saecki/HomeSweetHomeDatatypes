package bedbrains.shared.datatypes.rules

abstract class Rule(val uid: String, val type: Int, var name: String) {

    abstract fun getValue(time: WeeklyTime): RuleValue

}