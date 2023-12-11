package com.willmadison.leetcode

import java.lang.Integer.max
import kotlin.math.pow

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

    fun countTestedDevices(batteryPercentages: IntArray): Int {
        var testedDevices = 0

        for (i in batteryPercentages.indices) {
            if (batteryPercentages[i] > 0) {
                testedDevices++

                for (j in (i + 1)..<batteryPercentages.size) {
                    batteryPercentages[j] = max(batteryPercentages[j] - 1, 0)
                }
            }
        }

        return testedDevices
    }

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

    fun countSubarrays(nums: IntArray, k: Int): Long {
        var subarrays = 0L

        val max = nums.max()

        if (nums.count { it == max } >= k) {
            subarrays++
        }

        var start = 0
        var end = 0

        while (start <= end) {
            val countInWindow = nums.slice(start..end).count { it == max }
            if (countInWindow >= k) {
                subarrays++
                start++
            } else {
                end++
            }
        }

        return subarrays
    }

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

    fun longestPalindrome(value: String): Int {
        var length = 0
        val occurrencesByCharacter = value.groupingBy { it }.eachCount().toMutableMap()

        var mostFrequentCharacterEntry = occurrencesByCharacter.filter { it.value == occurrencesByCharacter.values.max() }

        while (mostFrequentCharacterEntry.all { it.value > 1 }) {
            for (entry in mostFrequentCharacterEntry) {
                if (entry.value % 2 == 0) {
                    length += entry.value
                    occurrencesByCharacter[entry.key] = 0
                } else {
                    length += entry.value - 1
                    occurrencesByCharacter[entry.key] = 1
                }

                mostFrequentCharacterEntry = occurrencesByCharacter.filter { it.value == occurrencesByCharacter.values.max() }
            }
        }

        if (mostFrequentCharacterEntry.any { it.value == occurrencesByCharacter.values.max() && it.value == 1 }) {
            length++
        }

        return length
    }

}

open class VersionControl {
    fun isBadVersion(version: Int): Boolean {
        val rawFirstBad = System.getenv("FIRST_BAD_VERSION")
        val firstBad = rawFirstBad.toInt()
        return version >= firstBad
    }
}
