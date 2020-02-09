package bedbrains.shared.datatypes

inline fun <T> MutableList<T>.upsert(element: T, predicate: (T) -> Boolean): Boolean {
    val index = this.indexOfFirst(predicate)
    return if (index == -1) {
        this.add(element)
        false
    } else {
        this[index] = element
        true
    }
}

fun clamp(value: Double, min: Double, max: Double): Double {
    return when {
        value < min -> min
        value > max -> max
        else -> value
    }
}

fun clamp(value: Int, min: Int, max: Int): Int {
    return when {
        value < min -> min
        value > max -> max
        else -> value
    }
}