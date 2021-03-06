package bedbrains.shared.datatypes

import java.io.*

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

inline fun <T> MutableList<T>.update(element: T, predicate: (T) -> Boolean): Boolean {
    val index = this.indexOfFirst(predicate)
    return if (index == -1) {
        false
    } else {
        this[index] = element
        true
    }
}

inline fun <T> List<T>.upserted(element: T, predicate: (T) -> Boolean): List<T> {
    val index = this.indexOfFirst(predicate)
    return if (index == -1) {
        this.plus(element)
    } else {
        this.mapIndexed { i, e -> if (i == index) element else e }
    }
}

inline fun <T> List<T>.updated(element: T, predicate: (T) -> Boolean): List<T> {
    return this.map { if (predicate(it)) element else it }
}

fun <T : Comparable<T>> T.clamped(min: T, max: T) = when {
    this < min -> min
    this > max -> max
    else -> this
}

fun <T : Serializable> T.deepCopy(): T {
    val baos = ByteArrayOutputStream()
    val oos = ObjectOutputStream(baos)

    oos.writeObject(this)
    oos.close()

    val bais = ByteArrayInputStream(baos.toByteArray())
    val ois = ObjectInputStream(bais)

    @Suppress("UNCHECKED_CAST")
    return ois.readObject() as T
}