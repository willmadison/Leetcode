package com.willmadison.leetcode.extensions

import kotlin.math.pow

fun getGoodIndices(variables: Array<IntArray>, target: Int): List<Int> {
    val goodIndices = mutableListOf<Int>()

    for (i in variables.indices) {
        if (variables[i].isGood(target)) {
            goodIndices.add(i)
        }
    }

    return goodIndices
}

private fun IntArray.isGood(target: Int): Boolean {
    val a = this[0]
    val b = this[1]
    val c = this[2]
    val m = this[3]

    return (a.toDouble().pow(b).toInt() % 10).toDouble().pow(c).toInt() % m == target
}
