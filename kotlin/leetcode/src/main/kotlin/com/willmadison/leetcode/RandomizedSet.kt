package com.willmadison.leetcode

import kotlin.collections.HashSet

// https://leetcode.com/problems/insert-delete-getrandom-o1/
class RandomizedSet {

    private val store = HashSet<Int>(0)

    fun insert(`val`: Int): Boolean {
        return store.add(`val`)
    }

    fun remove(`val`: Int): Boolean {
        return store.remove(`val`)
    }

    fun getRandom(): Int {
        return store.random()
    }


}
