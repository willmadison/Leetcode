package com.willmadison.leetcode

class Solution : VersionControl() {

    // https://leetcode.com/problems/first-bad-version
    fun firstBadVersion(n: Int): Int {
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

    // https://leetcode.com/problems/ransom-note
    fun canConstruct(note: String, magazine: String): Boolean {
        val characterCountsInNote = note.groupingBy { it }.eachCount()
        val characterCountsInMagazine = magazine.groupingBy { it }.eachCount()

        for (entry in characterCountsInNote) {
            if (characterCountsInMagazine.getOrDefault(entry.key, 0) < entry.value) {
                return false
            }
        }

        return true
    }

    // https://leetcode.com/problems/two-sum/
    fun twoSum(nums: IntArray, target: Int): IntArray {
        val indiciesByValue = mutableMapOf<Int, MutableList<Int>>()

        for (i in nums.indices) {
            val indicies = indiciesByValue.getOrDefault(nums[i], mutableListOf<Int>())
            indicies.add(i)
            indiciesByValue[nums[i]] = indicies
        }

        for (i in nums.indices) {
            val complement = target - nums[i]
            val indicies = indiciesByValue.getOrDefault(complement, mutableListOf<Int>())

            if (indicies.size > 0 &&  i != indicies[0]) {
                return intArrayOf(i, indicies[0])
            }
        }

        return intArrayOf()
    }
}

open class VersionControl {
    fun isBadVersion(version: Int): Boolean {
        val rawFirstBad = System.getenv("FIRST_BAD_VERSION")
        val firstBad = rawFirstBad.toInt()
        return version >= firstBad
    }
}
