package com.willmadison.leetcode

// https://leetcode.com/problems/first-bad-version
class Solution: VersionControl() {
    fun firstBadVersion(n: Int): Int {
        var start = 1
        var end = n

        var result = -1

        while (start <= end) {
            val midpoint = start + (end-start)/2

            if (isBadVersion(midpoint)) {
                result = midpoint
                end = midpoint-1
            } else {
                start = midpoint+1
            }
        }

        return result
    }

}

open class VersionControl {
    fun isBadVersion(version: Int): Boolean {
        val rawFirstBad = System.getenv("FIRST_BAD_VERSION")
        val firstBad = rawFirstBad.toInt()
        return version >= firstBad
    }
}
