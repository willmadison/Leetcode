package com.willmadison.leetcode.extensions

private val memo = mutableMapOf<Int, Int>()

fun climbStairs(n: Int): Int {
    if (n <= 3) {
        return n
    }

    memo[1] = 1
    memo[2] = 2
    memo[3] = 3

    for (i in 4..n) {
        memo[i] = memo[i - 2]!! + memo[i - 1]!!
    }

    return memo[n]!!
}