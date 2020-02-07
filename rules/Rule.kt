package bedbrains.shared.datatypes.rules

abstract class Rule(val uid: String, val type: Int, var name: String) {

    abstract class JSON(val uid: String, val type: Int, val name: String) {

        abstract fun toRule(): Rule
    }

    abstract fun toJSON(): JSON
    abstract fun getValue(time: WeeklyTime): RuleValue

}
