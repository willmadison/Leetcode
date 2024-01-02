package com.willmadison.leetcode

import java.lang.Integer.max
import java.util.*
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

            if (indicies.size > 0 && i != indicies[0]) {
                return intArrayOf(i, indicies[0])
            }
        }

        return intArrayOf()
    }

    // https://leetcode.com/problems/reverse-linked-list/
    fun reverseList(head: ListNode?): ListNode? {
        val stack = Stack<ListNode?>()

        if (head?.next == null) {
            return head
        }

        var current = head

        while (current != null) {
            stack.push(current)
            current = current.next
        }

        val newHead = stack.pop()

        current = newHead

        while (!stack.isEmpty()) {
            current?.next = stack.pop()
            current = current?.next
        }

        current?.next = null

        return newHead
    }

    // https://leetcode.com/problems/majority-element/
    fun majorityElement(nums: IntArray): Int {
        val occurrencesByValue = nums.asIterable().groupingBy { it }.eachCount()

        return occurrencesByValue.entries.find { it.value > nums.size / 2 }!!.key
    }

    // https://leetcode.com/problems/add-binary/
    fun addBinary(a: String, b: String): String {
        var carry = 0

        val aDigits = ArrayDeque<Char>()
        val bDigits = ArrayDeque<Char>()
        val resultDigits = ArrayDeque<Char>()

        for (c in a) {
            aDigits.add(c)
        }

        for (c in b) {
            bDigits.add(c)
        }

        val ZERO = '0'
        val ONE = '1'

        while (!aDigits.isEmpty() || !bDigits.isEmpty()) {
            var aDigit = '0'
            var bDigit = '0'

            if (!aDigits.isEmpty())  {
                aDigit = aDigits.removeLast()
            }

            if (!bDigits.isEmpty())  {
                bDigit = bDigits.removeLast()
            }

            var resultDigit: Int

            val addends = Pair(aDigit, bDigit)

            when (addends) {
                '1' to '1' -> {
                    resultDigit = 0

                    if (carry == 1) {
                        resultDigit = 1
                    }

                    carry = 1
                }
                '0' to '0' -> {
                    resultDigit = 0

                    if (carry == 1) {
                        resultDigit = 1
                    }

                    carry = 0
                }
                else -> {
                    resultDigit = 1

                    if (carry == 1) {
                        resultDigit = 0
                    }
                }
            }

            when (resultDigit) {
                1 -> resultDigits.addFirst(ONE)
                0 -> resultDigits.addFirst(ZERO)
            }
        }

        if (carry == 1) {
            resultDigits.addFirst(ONE)
        }

        val resultSb = StringBuilder()

        for (c in resultDigits) {
            resultSb.append(c)
        }

        return resultSb.toString()
    }

    // https://leetcode.com/problems/diameter-of-binary-tree/
    var maxHeight = 0
    fun diameterOfBinaryTree(root: TreeNode?): Int {
        maxHeight = 1
        calculateHeight(root)
        return maxHeight - 1
    }

    private fun calculateHeight(node: TreeNode?): Int {
        if (node == null) {
            return 0
        }

        val leftHeight = calculateHeight(node.left)
        val rightHeight = calculateHeight(node.right)
        maxHeight = max(maxHeight, leftHeight+rightHeight+1)

        return max(leftHeight, rightHeight) + 1
    }

    // https://leetcode.com/problems/middle-of-the-linked-list/
    fun middleNode(head: ListNode?): ListNode? {
        var slow = head
        var fast = head

        while (fast?.next != null) {
            fast = fast.next

            if (fast?.next != null) {
                fast = fast?.next
            }

            slow = slow?.next
        }

        return slow
    }

    // https://leetcode.com/problems/maximum-depth-of-binary-tree/
    fun maxDepth(root: TreeNode?): Int {
        if (root == null) {
            return 0
        }

        return max(maxDepth(root.left), maxDepth(root.right)) + 1
    }

    // https://leetcode.com/problems/contains-duplicate
    fun containsDuplicate(nums: IntArray): Boolean {
        return nums.asIterable().groupingBy { it }.eachCount().any { it.value >= 2}
    }

    // https://leetcode.com/problems/maximum-subarray/
    fun maxSubArray(nums: IntArray): Int {
        var maxSum = Int.MIN_VALUE
        var sum = Int.MIN_VALUE

        for (v in nums) {
            sum = max(0, sum)
            sum += v
            maxSum = max(sum, maxSum)
        }

        return maxSum
    }
}

open class VersionControl {
    fun isBadVersion(version: Int): Boolean {
        val rawFirstBad = System.getenv("FIRST_BAD_VERSION")
        val firstBad = rawFirstBad.toInt()
        return version >= firstBad
    }
}

class ListNode(var `val`: Int, var next: ListNode? = null)

class TreeNode(var `val`: Int, var left: TreeNode? = null, var right: TreeNode? = null)
