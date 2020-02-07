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