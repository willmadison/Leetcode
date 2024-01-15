package com.willmadison.leetcode

import java.lang.Integer.max
import java.util.*
import kotlin.collections.LinkedHashSet
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

@Suppress("unused")
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
            val indicies = indiciesByValue.getOrDefault(nums[i], mutableListOf())
            indicies.add(i)
            indiciesByValue[nums[i]] = indicies
        }

        for (i in nums.indices) {
            val complement = target - nums[i]
            val indicies = indiciesByValue.getOrDefault(complement, mutableListOf())

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

        val zero = '0'
        val one = '1'

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
                1 -> resultDigits.addFirst(one)
                0 -> resultDigits.addFirst(zero)
            }
        }

        if (carry == 1) {
            resultDigits.addFirst(one)
        }

        val resultSb = StringBuilder()

        for (c in resultDigits) {
            resultSb.append(c)
        }

        return resultSb.toString()
    }

    // https://leetcode.com/problems/diameter-of-binary-tree/
    private var maxHeight = 0
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
    private fun maxDepth(root: TreeNode?): Int {
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
        val starts = mutableListOf(interval[0])
        val ends = mutableListOf(interval[1])

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

        val queue = ArrayDeque<NodeLevel>()

        if (root != null) {
            queue.offer(NodeLevel(root, 0))
        }

        while (!queue.isEmpty()) {
            val current = queue.poll()

            val nodesAtLevel = nodesByLevel.getOrDefault(current.level, mutableListOf())

            nodesAtLevel.add(current.node)

            nodesByLevel[current.level] = nodesAtLevel

            if (current.node.left != null) {
                queue.offer(NodeLevel(current.node.left!!, current.level + 1))
            }
            if (current.node.right != null) {
                queue.offer(NodeLevel(current.node.right!!, current.level + 1))
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

    private fun cloneGraph(node: Node?): Node? {
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
            var hasOverlap =
                batch.find { j -> batch.find { j.duration.intersect(it.duration).size > 1 && it != j } != null } != null

            while (hasOverlap) {
                val pruneCandidates =
                    batch.filter { j -> batch.find { j.duration.intersect(it.duration).size > 1 && it != j } != null }
                        .toMutableList()
                pruneCandidates.sortBy { it.profit }

                if (pruneCandidates.isNotEmpty()) {
                    val leastProfitable = pruneCandidates.removeFirst()
                    batch.remove(leastProfitable)
                    jobs.add(leastProfitable)
                }

                hasOverlap =
                    batch.find { j -> batch.find { j.duration.intersect(it.duration).size > 1 && it != j } != null } != null
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
            cost += abs(lastLocation - location!!)
            lastLocation = location
        }

        return cost
    }

    // https://leetcode.com/problems/jump-game/description/
    fun canJump(nums: IntArray): Boolean {
        var goal = nums.size - 1

        for (i in nums.size - 2 downTo 0) {
            if (i + nums[i] >= goal) {
                goal = i
            }
        }

        return goal == 0
    }

    // https://leetcode.com/problems/valid-parentheses/
    fun isValid(s: String): Boolean {
        val openingParenByClosingParen = mapOf(
            ')' to '(',
            ']' to '[',
            '}' to '{',
        )

        val stack = Stack<Char>()

        for (c in s) {
            when (c) {
                '(', '[', '{' -> stack.push(c)
                else -> {
                    val opening = openingParenByClosingParen[c]

                    if (stack.isEmpty() || opening != stack.pop()) {
                        return false
                    }
                }
            }
        }

        return stack.isEmpty()
    }

    // https://leetcode.com/problems/range-sum-of-bst
    private fun rangeSumBST(root: TreeNode?, low: Int, high: Int): Int {
        if (root == null) {
            return 0
        }

        val range = IntRange(low, high)

        if (range.contains(root.`val`)) {
            return root.`val` + rangeSumBST(root.left, low, high) + rangeSumBST(root.right, low, high)
        }

        return rangeSumBST(root.left, low, high) + rangeSumBST(root.right, low, high)
    }

    // https://leetcode.com/problems/remove-duplicates-from-sorted-array/
    fun removeDuplicates(nums: IntArray): Int {
        val uniques = LinkedHashSet<Int>()

        for (num in nums) {
            uniques.add(num)
        }

        val answer = uniques.size

        for (i in 0 until uniques.size) {
            nums[i] = uniques.first()
            uniques.remove(nums[i])
        }

        return answer
    }

    // https://leetcode.com/problems/assign-cookies
    fun findContentChildren(greeds: IntArray, rawCookies: IntArray): Int {
        var contents = 0

        greeds.sort()
        val cookies = rawCookies.toMutableList()
        cookies.sort()

        for (greediness in greeds) {
            if (cookies.isEmpty()) {
                break
            }

            try {
                val cookie = cookies.first { it >= greediness }
                cookies.remove(cookie)
                contents++
            } catch (ignore: NoSuchElementException) {
            }

        }

        return contents
    }

    // https://leetcode.com/problems/convert-an-array-into-a-2d-array-with-conditions/
    fun findMatrix(nums: IntArray): List<List<Int>> {
        val matrix = mutableListOf<List<Int>>()
        val minHeap = PriorityQueue(nums.toList())

        val distribution = mutableListOf<LinkedHashSet<Int>>()

        while (minHeap.isNotEmpty()) {
            val min = minHeap.remove()

            if (distribution.isEmpty()) {
                distribution.add(LinkedHashSet())
            }

            var row: MutableSet<Int>? = null

            for (r in distribution) {
                if (!r.contains(min)) {
                    row = r
                    break
                }
            }

            if (row == null) {
                row = LinkedHashSet()
                distribution.add(row)
            }

            row.add(min)
        }

        for (row in distribution) {
            matrix.add(row.toList())
        }

        return matrix
    }

    // https://leetcode.com/problems/number-of-laser-beams-in-a-bank/
    fun numberOfBeams(bank: Array<String>): Int {
        var beams = 0

        val securityDevicesByRow = mutableMapOf<Int, MutableCollection<Location>>()

        for ((row, value) in bank.withIndex()) {
            for ((col, character) in value.withIndex()) {
                if (character == '1') {
                    val devicesInRow = securityDevicesByRow.getOrDefault(row, mutableListOf())
                    devicesInRow.add(Location(row, col))
                    securityDevicesByRow[row] = devicesInRow
                }
            }
        }

        for (row in 0 until bank.size - 1) {
            var nearestDevices: Collection<Location>? = null

            for (i in row + 1 until bank.size) {
                if (securityDevicesByRow.containsKey(i)) {
                    nearestDevices = securityDevicesByRow[i]
                    break
                }
            }

            if (nearestDevices != null) {
                beams += securityDevicesByRow.getOrDefault(row, emptySet()).size * nearestDevices.size
            }
        }

        return beams
    }

    // https://leetcode.com/problems/minimum-number-of-operations-to-make-array-empty/
    fun minOperations(nums: IntArray): Int {
        val countsByNumber = nums.asIterable().groupingBy { it }.eachCount().toMutableMap()

        var operations = 0

        for ((value, initialOccurrences) in countsByNumber) {
            var ocurrences = initialOccurrences

            if (ocurrences % 3 == 0) {
                operations += ocurrences / 3
                ocurrences = 0
            } else if (ocurrences > 3) {
                while (ocurrences >= 3 && ocurrences > 4) {
                    ocurrences -= 3
                    operations++
                }
            }

            if (ocurrences > 0) {
                operations += ocurrences / 2
                ocurrences %= 2
            }

            countsByNumber[value] = ocurrences
        }

        if (countsByNumber.any { it.value > 0 }) {
            return -1
        }

        return operations
    }

    // https://leetcode.com/problems/3sum/
    fun threeSum(nums: IntArray): List<List<Int>> {
        nums.sort()

        val n = nums.size

        val result = arrayListOf<List<Int>>()

        for (i in 0 until n - 2) {
            if (i == 0 || (nums[i] != nums[i - 1])) {
                var low = i + 1
                var high = nums.size - 1
                val sum = 0 - nums[i]

                while (low < high) {
                    if (nums[i] + nums[low] + nums[high] == 0) {
                        result.add(listOf(nums[i], nums[low], nums[high]))

                        while (low < high && nums[low] == nums[low + 1]) low++
                        while (low < high && nums[high] == nums[high - 1]) high--

                        low++
                        high--
                    } else if (nums[low] + nums[high] > sum) {
                        high--
                    } else {
                        low++
                    }
                }
            }
        }

        return result
    }

    // https://leetcode.com/problems/path-sum/description/
    private fun hasPathSum(root: TreeNode?, targetSum: Int): Boolean {
        if (root == null) {
            return false
        }

        if (root.left == null && root.right == null) {
            return targetSum == root.`val`
        }

        var leftHasPathSum = false
        var rightHasPathSum = false

        if (root.left != null) {
            leftHasPathSum = hasPathSum(root.left, targetSum - root.`val`)
        }

        if (!leftHasPathSum && root.right != null) {
            rightHasPathSum = hasPathSum(root.right, targetSum - root.`val`)
        }

        return leftHasPathSum || rightHasPathSum
    }

    // https://leetcode.com/problems/longest-palindromic-substring/
    fun longestPalindromicSubstring(s: String): String {
        var start = 0
        var end = 0

        fun growPalindromicStringInRange(q: Int, p: Int) {
            var i = q
            var j = p
            while (i >= 0 && j < s.length && s[i] == s[j]) {
                i--
                j++
            }
            if (j - i > end - start) {
                end = j
                start = i + 1
            }
        }

        for (i in s.indices) {
            if (i != 0) growPalindromicStringInRange(i - 1, i)
            growPalindromicStringInRange(i, i)
        }

        return s.substring(start, end)
    }

    // https://leetcode.com/problems/leaf-similar-trees/
    fun leafSimilar(root1: TreeNode?, root2: TreeNode?): Boolean {
        val leftLeaves = root1?.leaves()
        val rightLeaves = root2?.leaves()

        return leftLeaves?.size == rightLeaves?.size && leftLeaves?.filterIndexed { i, n -> rightLeaves?.elementAt(i)?.`val` != n.`val` }
            ?.isEmpty() == true
    }

    private fun TreeNode?.leaves(): Collection<TreeNode> {
        val leaves = mutableListOf<TreeNode>()

        fun doFindLeaves(node: TreeNode) {
            if (node.isLeaf()) {
                leaves.add(node)
            } else {
                if (node.left != null) {
                    doFindLeaves(node.left!!)
                }

                if (node.right != null) {
                    doFindLeaves(node.right!!)
                }
            }
        }

        if (this != null) {
            doFindLeaves(this)
        }

        return leaves
    }

    private fun TreeNode?.isLeaf() = this != null && this.left == null && this.right == null

    // https://leetcode.com/problems/valid-anagram
    fun isAnagram(s: String, t: String) = s.length == t.length && key(s) == key(t)

    private fun key(s: String): String {
        val characters = s.toCharArray()
        characters.sort()
        return characters.joinToString("")
    }

    // https://leetcode.com/problems/product-of-array-except-self/
    fun productExceptSelf(nums: IntArray): IntArray {
        val answer = IntArray(nums.size)

        answer[0] = 1

        for (i in 1 until nums.size) {
            answer[i] = nums[i - 1] * answer[i - 1]
        }

        var rightProduct = 1

        for (i in nums.size - 1 downTo 0) {
            answer[i] = answer[i] * rightProduct
            rightProduct *= nums[i]
        }

        return answer
    }

    // https://leetcode.com/problems/excel-sheet-column-number/
    fun titleToNumber(columnTitle: String): Int {
        var answer = 0

        val deque = ArrayDeque<Char>()

        for (c in columnTitle) {
            deque.add(c)
        }

        var exponent = 0

        while (deque.isNotEmpty()) {
            val character = deque.removeLast()
            answer += (character - 'A' + 1) * 26.toDouble().pow(exponent).toInt()
            exponent++
        }

        return answer
    }

    // https://leetcode.com/problems/sort-the-students-by-their-kth-score/
    fun sortTheStudents(scores: Array<IntArray>, k: Int): Array<IntArray> {
        return scores.sortedByDescending { it[k] }.toTypedArray()
    }

    fun coinChange(coins: IntArray, changeDue: Int): Int {
        val cache = IntArray(changeDue + 1) { changeDue + 1 }
        cache[0] = 0

        for (amount in 1..changeDue) {
            for (coin in coins) {
                if (amount - coin >= 0) {
                    cache[amount] = minOf(cache[amount], 1 + cache[amount - coin])
                }
            }
        }

        return if (cache[changeDue] != changeDue + 1) cache[changeDue] else -1
    }


    // https://leetcode.com/problems/amount-of-time-for-binary-tree-to-be-infected
    @Suppress("DuplicatedCode")
    fun amountOfTime(root: TreeNode?, start: Int): Int {
        var initialInfectedNode: TreeNode? = null

        val adjacentNodesByNode = mutableMapOf<TreeNode, MutableCollection<TreeNode>>()

        val queue = ArrayDeque<TreeNode>()

        if (root != null) {
            queue.add(root)
        }

        var totalNumberOfNodes = 0

        while (queue.isNotEmpty()) {
            val node = queue.pop()

            totalNumberOfNodes++

            if (node.`val` == start) {
                initialInfectedNode = node
            }

            val adjacents = adjacentNodesByNode.getOrDefault(node, mutableListOf())

            if (node.left != null) {
                queue.add(node.left!!)
                adjacents.add(node.left!!)

                val leftAdjacents = adjacentNodesByNode.getOrDefault(node.left, mutableListOf())
                leftAdjacents.add(node)
                adjacentNodesByNode[node.left!!] = leftAdjacents
            }

            if (node.right != null) {
                queue.add(node.right!!)
                adjacents.add(node.right!!)

                val rightAdjacents = adjacentNodesByNode.getOrDefault(node.right, mutableListOf())
                rightAdjacents.add(node)
                adjacentNodesByNode[node.right!!] = rightAdjacents
            }

            adjacentNodesByNode[node] = adjacents
        }

        val infectedNodes = mutableSetOf(initialInfectedNode!!)

        val toProcess = mutableListOf(initialInfectedNode)

        var minutesTranspired = 0

        while (infectedNodes.size != totalNumberOfNodes) {
            val nodes = toProcess.take(toProcess.size)
            toProcess.removeAll(nodes)

            for (infectedNode in nodes) {
                val adjacents = adjacentNodesByNode[infectedNode]!!

                for (adjacent in adjacents) {
                    if (!infectedNodes.contains(adjacent)) {
                        infectedNodes.add(adjacent)

                        val hasNonInfectedAdjacents =
                            adjacentNodesByNode[adjacent]!!.any { !infectedNodes.contains(it) }

                        if (hasNonInfectedAdjacents) {
                            toProcess.add(adjacent)
                        }
                    }
                }
            }

            minutesTranspired++
        }

        return minutesTranspired
    }

    // https://leetcode.com/problems/minimum-window-substring/
    fun minWindow(source: String, target: String): String {
        val countsByCharacterInTarget = target.groupingBy { it }.eachCount().toMutableMap()

        var substringStart = 0
        var substringEnd = Int.MAX_VALUE

        var satisfiedCharacters = 0

        var start = 0

        for (end in source.indices) {
            val letter = source[end]

            if (countsByCharacterInTarget.containsKey(letter)) {
                val count = countsByCharacterInTarget[letter]!!
                countsByCharacterInTarget[letter] = count - 1

                if (countsByCharacterInTarget[letter] == 0) {
                    satisfiedCharacters++
                }
            }

            while (satisfiedCharacters == countsByCharacterInTarget.size) {
                val startLetter = source[start]

                if (countsByCharacterInTarget.containsKey(startLetter)) {
                    val count = countsByCharacterInTarget[startLetter]!!
                    countsByCharacterInTarget[startLetter] = count + 1

                    if (countsByCharacterInTarget[startLetter] == 1) {
                        satisfiedCharacters--
                    }
                }

                if (end - start < substringEnd - substringStart) {
                    substringEnd = end
                    substringStart = start
                }

                start++
            }
        }

        if (substringEnd == Int.MAX_VALUE) {
            return ""
        }

        return source.substring(substringStart..substringEnd)
    }

    // https://leetcode.com/problems/difference-between-ones-and-zeros-in-row-and-column
    fun onesMinusZeros(grid: Array<IntArray>): Array<IntArray> {
        val onesByRow = mutableMapOf<Int, Int>()
        val onesByColumn = mutableMapOf<Int, Int>()
        val zeroesByRow = mutableMapOf<Int, Int>()
        val zeroesByColumn = mutableMapOf<Int, Int>()

        for ((row, values) in grid.withIndex()) {
            for ((col, value) in values.withIndex()) {
                if (value == 1) {
                    onesByRow[row] = onesByRow.getOrDefault(row, 0) + 1
                    onesByColumn[col] = onesByColumn.getOrDefault(col, 0) + 1
                } else {
                    zeroesByRow[row] = zeroesByRow.getOrDefault(row, 0) + 1
                    zeroesByColumn[col] = zeroesByColumn.getOrDefault(col, 0) + 1
                }
            }
        }

        val result = mutableListOf<IntArray>()

        for ((row, values) in grid.withIndex()) {
            val diffs = mutableListOf<Int>()

            for (col in values.indices) {
                val onesInRow = onesByRow.getOrDefault(row, 0)
                val onesInColumn = onesByColumn.getOrDefault(col, 0)
                val zeroesInRow = zeroesByRow.getOrDefault(row, 0)
                val zeroesInColumn = zeroesByColumn.getOrDefault(col, 0)

                diffs.add(onesInRow + onesInColumn - zeroesInRow - zeroesInColumn)
            }

            result.add(diffs.toIntArray())
        }

        return result.toTypedArray()
    }

    fun maxAncestorDiff(root: TreeNode?, min: Int = root!!.`val`, max: Int = root!!.`val`): Int =
        if (root == null) max - min
        else listOf(root.left, root.right).maxOf {
            maxAncestorDiff(it, minOf(root.`val`, min), maxOf(root.`val`, max))
        }

    // https://leetcode.com/problems/determine-if-string-halves-are-alike/description/
    fun halvesAreAlike(s: String): Boolean {
        val frontHalf = s.substring(0..<s.length / 2)
        val backHalf = s.substring(s.length / 2)

        return frontHalf.vowelCount() == backHalf.vowelCount()
    }

    private val vowels = setOf('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U')
    private fun String.vowelCount(): Int {
        return count { it in vowels }
    }

    // https://leetcode.com/problems/minimum-number-of-steps-to-make-two-strings-anagram/
    fun minSteps(s: String, t: String): Int {
        val counts = IntArray(26)

        for (i in s.indices) {
            counts[s[i] - 'a']++
            counts[t[i] - 'a']--
        }

        return counts.sumOf { it.coerceAtLeast(0) }
    }

    fun maxFrequencyElements(nums: IntArray): Int {
        val frequencyByNumber = nums.asIterable().groupingBy { it }.eachCount()
        val maxFrequency = frequencyByNumber.values.max()

        return frequencyByNumber.values.filter { it == maxFrequency }.sum()
    }

    fun beautifulIndices(s: String, a: String, b: String, k: Int): List<Int> {
        val indices = mutableListOf<Int>()

        val jRange = IntRange(0, s.length-b.length)
        val iRange = IntRange(0, s.length-a.length)
        val jsMeetingCriteria = mutableSetOf<Int>()

        var found: Boolean
        var startIndex = 0

        do {
            val index = s.indexOf(b, startIndex)  // O(m*n)
            found = index >= 0
            startIndex = index+1

            if (jRange.contains(index)) { // O(1)
                jsMeetingCriteria.add(index)
            }
        } while (found)

        startIndex = 0

        do {
            val index = s.indexOf(a, startIndex) // O(m*n)
            found = index >= 0
            startIndex = index+1

            val hasJ = jsMeetingCriteria.any { abs(it-index) <= k} // O(n)

            if (iRange.contains(index) && hasJ) { // O(1)
                indices.add(index)
            }
        } while (found)

        indices.sort() // O(log(n))

        // O(n*M) + O(log(n)) + O(n)

        return indices
    }

    fun sum(num1: Int, num2: Int) = num1 + num2

    // https://leetcode.com/problems/find-players-with-zero-or-one-losses/
    fun findWinners(matches: Array<IntArray>): List<List<Int>> {
        val lossesByPlayer = mutableMapOf<Int, Int>()

        val allPlayers = mutableSetOf<Int>()

        for (match in matches) {
            val winner = match[0]
            val loser = match[1]

            allPlayers.addAll(listOf(winner, loser))

            val losses = lossesByPlayer.getOrDefault(loser, 0)
            lossesByPlayer[loser] = losses+1
        }

        val undefeated = mutableListOf<Int>()
        val singleLoss = mutableListOf<Int>()

        for (player in allPlayers) {
            val losses = lossesByPlayer.getOrDefault(player, 0)

            when (losses) {
                0 -> undefeated.add(player)
                1 -> singleLoss.add(player)
            }
        }

        undefeated.sort()
        singleLoss.sort()

        return listOf(undefeated, singleLoss)
    }
}

open class VersionControl {
    fun isBadVersion(version: Int): Boolean {
        val rawFirstBad = System.getenv("FIRST_BAD_VERSION")
        val firstBad = rawFirstBad.toInt()
        return version >= firstBad
    }
}

@Suppress("unused")
class ListNode(var `val`: Int, var next: ListNode? = null)

class TreeNode(var `val`: Int, var left: TreeNode? = null, var right: TreeNode? = null)

class Node(var `val`: Int, var neighbors: ArrayList<Node?> = ArrayList<Node?>())