package com.example.core.extensions

fun <T> MutableList<T>.addFlatLists(vararg collections: Collection<List<T>>): MutableList<T> {
    return this.apply {
        collections.forEach {
            addAll(it.flatten())
        }
    }
}