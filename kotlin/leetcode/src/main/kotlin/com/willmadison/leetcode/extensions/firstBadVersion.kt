package com.willmadison.leetcode.extensions

import com.willmadison.leetcode.Solution

// https://leetcode.com/problems/first-bad-version
fun Solution.firstBadVersion(n: Int): Int {
    var start = 1
    var end = n

    var result = -1

    while (start <= end) {
        val midpoint = start + (end - start) / 2

        if (isBadVersion(midpoint)) {
            result = midpoint
            end = midpoint - 1
        } else {
            start = midpoint + 1
        }
    }

    return result
}