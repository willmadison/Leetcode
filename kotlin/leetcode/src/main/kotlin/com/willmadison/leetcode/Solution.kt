package com.willmadison.leetcode

import java.lang.Integer.max
import java.util.*
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

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

        var mostFrequentCharacterEntry =
            occurrencesByCharacter.filter { it.value == occurrencesByCharacter.values.max() }

        while (mostFrequentCharacterEntry.all { it.value > 1 }) {
            for (entry in mostFrequentCharacterEntry) {
                if (entry.value % 2 == 0) {
                    length += entry.value
                    occurrencesByCharacter[entry.key] = 0
                } else {
                    length += entry.value - 1
                    occurrencesByCharacter[entry.key] = 1
                }

                mostFrequentCharacterEntry =
                    occurrencesByCharacter.filter { it.value == occurrencesByCharacter.values.max() }
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

            if (!aDigits.isEmpty()) {
                aDigit = aDigits.removeLast()
            }

            if (!bDigits.isEmpty()) {
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
        maxHeight = max(maxHeight, leftHeight + rightHeight + 1)

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
        return nums.asIterable().groupingBy { it }.eachCount().any { it.value >= 2 }
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

    // https://leetcode.com/problems/insert-interval/
    fun insert(intervals: Array<IntArray>, newInterval: IntArray): Array<IntArray> {
        val overlappingIntervals = intervals.filter { it.overlaps(newInterval) }.toSet()

        val merged = merge(newInterval, overlappingIntervals)

        val fullyMerged = mutableListOf(merged)

        for (interval in intervals) {
            if (!overlappingIntervals.contains(interval)) {
                fullyMerged.add(interval)
            }
        }

        fullyMerged.sortBy { it[0] }

        return fullyMerged.toTypedArray()
    }

    private fun merge(interval: IntArray, overlappingIntervals: Collection<IntArray>): IntArray {
        val starts = mutableListOf<Int>(interval[0])
        val ends = mutableListOf<Int>(interval[1])

        for (i in overlappingIntervals) {
            starts.add(i[0])
            ends.add(i[1])
        }

        return intArrayOf(starts.min(), ends.max())
    }

    private fun IntArray.overlaps(other: IntArray) =
        other[0] <= this[0] && this[0] <= other[1] ||
                other[0] <= this[1] && this[1] <= other[1] ||
                this[0] <= other[0] && other[0] <= this[1] ||
                this[0] <= other[1] && other[1] <= this[1]

    // https://leetcode.com/problems/01-matrix/
    fun updateMatrix(matrix: Array<IntArray>): Array<IntArray> {
        val zeroes = mutableSetOf<Location>()
        val locationsToUpdate = ArrayDeque<Location>()

        val updated = mutableListOf<IntArray>()

        for (row in matrix.indices) {
            val values = mutableListOf<Int>()
            for (col in 0..<matrix[0].size) {
                if (matrix[row][col] == 0) {
                    values.add(0)
                    zeroes.add(Location(row, col))
                } else {
                    values.add(1)
                    locationsToUpdate.add(Location(row, col))
                }
            }

            updated.add(values.toIntArray())
        }

        while (locationsToUpdate.isNotEmpty()) {
            val location = locationsToUpdate.pop()
            val validNeighbors = location.neighbors()
                .filter { it.row >= 0 && it.row < matrix.size && it.col >= 0 && it.col < matrix[0].size }

            var locationUpdated = false

            for (neighbor in validNeighbors) {
                if (zeroes.contains(neighbor)) {
                    updated[location.row][location.col] = 1
                    locationUpdated = true
                }
            }

            if (!locationUpdated) {
                val nearest = zeroes.minBy { it.manhattanDistance(location) }
                updated[location.row][location.col] = location.manhattanDistance(nearest)
            }
        }

        return updated.toTypedArray()
    }

    // https://leetcode.com/problems/k-closest-points-to-origin/
    fun kClosest(points: Array<IntArray>, k: Int): Array<IntArray> {
        points.sortBy {
            Point(it[0], it[1]).distanceFrom()
        }
        return points.take(k).toTypedArray()
    }

    // https://leetcode.com/problems/longest-substring-without-repeating-characters/
    fun lengthOfLongestSubstring(s: String): Int {
        var maxLength = 0

        val charactersSeen = mutableSetOf<Char>()
        var start = 0

        for ((end, character) in s.withIndex()) {
            while (charactersSeen.contains(character) && start < s.length) {
                charactersSeen.remove(s[start])
                start++
            }

            charactersSeen.add(character)

            maxLength = max(maxLength, end - start + 1)
        }

        return maxLength
    }

    private data class Point(val x: Int, val y: Int) {
        fun distanceFrom(other: Point = Point(0, 0)) =
            sqrt(((other.x - this.x) * (other.x - this.x) + (other.y - this.y) * (other.y - this.y)).toDouble())
    }

    private data class Location(val row: Int, val col: Int) {
        fun neighbors() = setOf(
            Location(this.row - 1, this.col),
            Location(this.row, this.col + 1),
            Location(this.row + 1, this.col),
            Location(this.row, this.col - 1),
        )

        fun manhattanDistance(other: Location) = abs(this.row - other.row) + abs(this.col - other.col)
    }

    data class NodeLevel(val node: TreeNode, val level: Int)

    // https://leetcode.com/problems/binary-tree-level-order-traversal/description/
    fun levelOrder(root: TreeNode?): List<List<Int>> {
        val nodesByLevel = mutableMapOf<Int, MutableList<TreeNode>>()

        val queue = ArrayDeque<NodeLevel>();

        if (root != null) {
            queue.offer(NodeLevel(root, 0));
        }

        while (!queue.isEmpty()) {
            val current = queue.poll();

            val nodesAtLevel = nodesByLevel.getOrDefault(current.level, mutableListOf())

            nodesAtLevel.add(current.node)

            nodesByLevel[current.level] = nodesAtLevel

            if (current.node.left != null) {
                queue.offer(NodeLevel(current.node.left!!, current.level + 1));
            }
            if (current.node.right != null) {
                queue.offer(NodeLevel(current.node.right!!, current.level + 1));
            }
        }

        val valuesByLevel = mutableListOf<List<Int>>()

        for (nodes in nodesByLevel.values) {
            val values = mutableListOf<Int>()

            for (node in nodes) {
                values.add(node.`val`)
            }

            valuesByLevel.add(values)
        }

        return valuesByLevel
    }


    // https://leetcode.com/problems/clone-graph
    private val cache = mutableMapOf<Int, Node?>()

    fun cloneGraph(node: Node?): Node? {
        if (node == null) {
            return null
        }

        if (cache.containsKey(node.`val`)) {
            return cache[node.`val`]
        }

        val copy = Node(node.`val`)
        cache[node.`val`] = copy

        for (neighbor in node.neighbors) {
            copy.neighbors.add(cloneGraph(neighbor))
        }

        return copy
    }

    // https://leetcode.com/problems/evaluate-reverse-polish-notation/
    fun evalRPN(tokens: Array<String>): Int {
        val stack = Stack<Int>()

        for (token in tokens) {
            when (token) {
                "+" -> {
                    val a = stack.pop()
                    val b = stack.pop()
                    stack.push(a + b)
                }

                "-" -> {
                    val a = stack.pop()
                    val b = stack.pop()
                    stack.push(b - a)
                }

                "*" -> {
                    val a = stack.pop()
                    val b = stack.pop()
                    stack.push(a * b)
                }

                "/" -> {
                    val a = stack.pop()
                    val b = stack.pop()
                    stack.push(b / a)
                }

                else -> stack.push(token.toInt())
            }
        }

        return stack.pop()
    }

    // https://leetcode.com/problems/longest-increasing-subsequence/
    fun lengthOfLIS(nums: IntArray): Int {
        val sequence = mutableListOf<Int>()

        for (num in nums) {
            var insertionPoint = sequence.binarySearch(num)
            if (insertionPoint < 0) insertionPoint = -insertionPoint - 1
            if (insertionPoint == sequence.size) sequence.add(num) else sequence[insertionPoint] = num
        }

        return sequence.size
    }

    // https://leetcode.com/problems/palindrome-number/
    fun isPalindrome(x: Int): Boolean {
        if (x < 0) {
            return false
        }

        var value = x

        val digits = mutableListOf<Int>()

        while (value > 0) {
            val digit = value % 10
            digits.add(digit)
            value /= 10
        }

        var start = 0
        var end = digits.size - 1

        while (start <= end) {
            if (digits[start] != digits[end]) {
                return false
            }

            start++
            end--
        }

        return true
    }

    // https://leetcode.com/problems/longest-common-prefix
    fun longestCommonPrefix(words: Array<String>): String {
        var end = 0

        var sameCharacter = true

        do {
            if (words[0].isEmpty()) {
                break
            }

            val c = words[0][end]

            for (word in words) {
                if (end >= word.length || word[end] != c) {
                    sameCharacter = false
                    break
                }
            }

            if (sameCharacter) {
                end++
            }
        } while (sameCharacter && end < words[0].length)

        return words[0].substring(0..<end)
    }

    // https://leetcode.com/problems/maximum-profit-in-job-scheduling/

    private data class Job(val duration: IntRange, val profit: Int)

    fun jobScheduling(startTimes: IntArray, endTimes: IntArray, profits: IntArray): Int {
        var maxProfit = 0

        val jobs = mutableListOf<Job>()

        for ((i, startTime) in startTimes.withIndex()) {
            val endTime = endTimes[i]
            val profit = profits[i]

            jobs.add(Job(IntRange(startTime, endTime), profit))
        }

        val batches = batchNonConcurrentJobs(jobs)

        for (batch in batches.values) {
            val totalProfit = batch.sumOf { it.profit }
            maxProfit = max(totalProfit, maxProfit)
        }

        return maxProfit
    }

    private fun batchNonConcurrentJobs(jobs: MutableList<Job>): Map<UUID, Collection<Job>> {
        val batchedNonConcurrentJobs = mutableMapOf<UUID, Collection<Job>>()

        while (jobs.isNotEmpty()) {
            val batchId = UUID.randomUUID()
            val job = jobs.removeFirst()
            val batch = mutableSetOf(job)

            val nonConcurrentJobs = jobs.filter {
                val intersection = job.duration.intersect(it.duration)
                intersection.size <= 1
            }

            batch.addAll(nonConcurrentJobs)
            jobs.removeAll(batch)

            // Prune the least profitable job from this batch that is concurrent
            // with another job in the batch and add it back to the job pool
            var hasOverlap = batch.find { j -> batch.find { j.duration.intersect(it.duration).size > 1 && it != j} != null } != null

            while (hasOverlap) {
                val pruneCandidates = batch.filter { j -> batch.find { j.duration.intersect(it.duration).size > 1 && it != j} != null}.toMutableList()
                pruneCandidates.sortBy { it.profit }

                if (pruneCandidates.isNotEmpty()) {
                    val leastProfitable = pruneCandidates.removeFirst()
                    batch.remove(leastProfitable)
                    jobs.add(leastProfitable)
                }

                 hasOverlap = batch.find { j -> batch.find { j.duration.intersect(it.duration).size > 1 && it != j} != null } != null
            }

            batchedNonConcurrentJobs[batchId] = batch
        }

        return batchedNonConcurrentJobs
    }

    // https://leetcode.com/problems/single-row-keyboard/
    fun calculateTime(keyboard: String, word: String): Int {
        var cost = 0

        val locationByKey = mutableMapOf<Char, Int>()

        for ((i, key) in keyboard.withIndex()) {
            locationByKey[key] = i
        }

        var lastLocation = 0

        for (char in word) {
            val location = locationByKey[char]
            cost += abs(lastLocation-location!!)
            lastLocation = location
        }

        return cost
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

class Node(var `val`: Int, var neighbors: ArrayList<Node?> = ArrayList<Node?>())