package com.willmadison.leetcode

import java.lang.Integer.max
import java.lang.Integer.min
import java.util.*
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
        var majorityElement = nums[0]
        var counter = 0

        for (num in nums) {
            if (num == majorityElement) {
                counter++
            } else {
                counter--
            }

            if (counter == 0) {
                majorityElement = num
                counter = 1
            }
        }

        return majorityElement
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

    private data class Point(var x: Int, var y: Int) {
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

    // https://leetcode.com/problems/binary-tree-level-order-traversal/description/
    fun levelOrder(root: TreeNode?): List<List<Int>> {
        val result = mutableListOf<List<Int>>()

        var level: List<TreeNode> = if (root != null) mutableListOf(root) else emptyList()

        while (level.isNotEmpty()) {
            result.add(level.map { it.`val` })
            level = level.flatMap { listOf(it.left, it.right) }.filterNotNull()
        }

        return result
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

        for (neighbor in node.children) {
            copy.children.add(cloneGraph(neighbor))
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

        val jRange = IntRange(0, s.length - b.length)
        val iRange = IntRange(0, s.length - a.length)
        val jsMeetingCriteria = mutableSetOf<Int>()

        var found: Boolean
        var startIndex = 0

        do {
            val index = s.indexOf(b, startIndex)  // O(m*n)
            found = index >= 0
            startIndex = index + 1

            if (jRange.contains(index)) { // O(1)
                jsMeetingCriteria.add(index)
            }
        } while (found)

        startIndex = 0

        do {
            val index = s.indexOf(a, startIndex) // O(m*n)
            found = index >= 0
            startIndex = index + 1

            val hasJ = jsMeetingCriteria.any { abs(it - index) <= k } // O(n)

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
            lossesByPlayer[loser] = losses + 1
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

    // https://leetcode.com/problems/find-the-difference/
    fun findTheDifference(s: String, t: String): Char {
        val tCountsByCharacter = t.groupingBy { it }.eachCount()
        val sCountsByCharacter = s.groupingBy { it }.eachCount()

        return tCountsByCharacter.keys.first { sCountsByCharacter[it] != tCountsByCharacter[it] }
    }

    // https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string/
    fun strStr(haystack: String, needle: String) = haystack.indexOf(needle)

    // https://leetcode.com/problems/merge-strings-alternately/
    fun mergeAlternately(word1: String, word2: String): String {
        val queue = ArrayDeque<String>()

        queue.add(word1)
        queue.add(word2)

        val merged = StringBuilder()

        while (queue.isNotEmpty()) {
            if (queue.size == 1) {
                merged.append(queue.pop())
                break
            }

            val word = queue.pop()

            val c = word.take(1)

            merged.append(c)

            val remaining = word.substring(1)

            if (remaining.isNotEmpty()) {
                queue.add(remaining)
            }
        }

        return merged.toString()
    }

    // https://leetcode.com/problems/repeated-substring-pattern/
    fun repeatedSubstringPattern(s: String): Boolean {
        val key = s + s
        return key.substring(1, key.length - 1).contains(s)
    }

    // https://leetcode.com/problems/move-zeroes/
    fun moveZeroes(nums: IntArray) {
        var lastNonZeroIndex = 0
        for (i in nums.indices) {
            if (nums[i] != 0) {
                nums[lastNonZeroIndex++] = nums[i]
            }
        }

        for (i in lastNonZeroIndex until nums.size) {
            nums[i] = 0
        }
    }

    // https://leetcode.com/problems/unique-number-of-occurrences/
    fun uniqueOccurrences(nums: IntArray): Boolean {
        val countsByValue = nums.asIterable().groupingBy { it }.eachCount()
        return countsByValue.size == countsByValue.values.toSet().size
    }

    // https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/
    fun findMin(nums: IntArray): Int {
        var left = 0
        var right = nums.size - 1
        var r = nums[right]

        while (left + 1 < right) {
            val middle = (left + right) / 2
            when {
                nums[middle] > r -> left = middle
                else -> {
                    right = middle
                    r = nums[right]
                }
            }
        }

        return minOf(nums[left], nums[right])
    }

    // https://leetcode.com/problems/count-vowel-strings-in-ranges/description/

    private val lowerCasedVowels = setOf('a', 'e', 'i', 'o', 'u')
    fun vowelStrings(words: Array<String>, queries: Array<IntArray>): IntArray {
        val answers = IntArray(queries.size)

        val vowelPrefixAndSuffix = mutableSetOf<Int>()

        for ((i, word) in words.withIndex()) {
            if (lowerCasedVowels.contains(word[0]) && lowerCasedVowels.contains(word[word.length - 1])) {
                vowelPrefixAndSuffix.add(i)
            }
        }

        for ((i, query) in queries.withIndex()) {
            val range = IntRange(query[0], query[1])
            answers[i] = vowelPrefixAndSuffix.count { range.contains(it) }
        }

        return answers
    }

    // https://leetcode.com/problems/smallest-even-multiple/
    fun smallestEvenMultiple(n: Int) = if (n % 2 == 0) n else 2 * n

    // https://leetcode.com/problems/plus-one/
    fun plusOne(digits: IntArray): IntArray {
        val sumDigits = mutableListOf<Int>()

        var carry = 0

        for (i in digits.size - 1 downTo 0) {
            var digit: Int
            val sum = if (i == digits.size - 1) digits[i] + 1 else digits[i] + carry

            if (sum >= 10) {
                carry = 1
                digit = sum % 10
            } else {
                carry = 0
                digit = sum
            }

            sumDigits.add(digit)
        }

        if (carry == 1) {
            sumDigits.add(1)
        }

        sumDigits.reverse()
        return sumDigits.toIntArray()
    }

    // https://leetcode.com/problems/sign-of-the-product-of-an-array/
    fun arraySign(nums: IntArray): Int {
        val numNegatives = nums.count { it < 0 }
        val numZeros = nums.count { it == 0 }

        return when {
            numZeros > 0 -> 0
            numNegatives % 2 > 0 -> -1
            else -> 1
        }
    }

    // https://leetcode.com/problems/can-make-arithmetic-progression-from-sequence/
    fun canMakeArithmeticProgression(nums: IntArray): Boolean {
        nums.sort()

        val difference = abs(nums[0] - nums[1])

        for (i in 0..nums.size - 2) {
            if (abs(nums[i] - nums[i + 1]) != difference) {
                return false
            }
        }

        return true
    }

    // https://leetcode.com/problems/monotonic-array
    fun isMonotonic(nums: IntArray): Boolean {
        var isIncreasingMonotonic = true
        var isDecreasingMonotonic = true

        for (i in 0..nums.size - 2) {
            if (nums[i] > nums[i + 1]) {
                isIncreasingMonotonic = false
                break
            }
        }

        if (!isIncreasingMonotonic) {
            for (i in 0..nums.size - 2) {
                if (nums[i] < nums[i + 1]) {
                    isDecreasingMonotonic = false
                    break
                }
            }
        }

        return isIncreasingMonotonic || isDecreasingMonotonic
    }

    fun romanToInt(s: String): Int {
        val prefixedNumerals = mapOf(
            "IV" to 4,
            "IX" to 9,
            "XL" to 40,
            "XC" to 90,
            "CD" to 400,
            "CM" to 900,
        )

        val valuesByCharacter = mapOf(
            'I' to 1,
            'V' to 5,
            'X' to 10,
            'L' to 50,
            'C' to 100,
            'D' to 500,
            'M' to 1000,
        )

        var value = 0

        var romanRepresentation = s

        while (romanRepresentation.isNotEmpty()) {
            if (romanRepresentation.length >= 2) {
                val characters = romanRepresentation.take(2)

                if (prefixedNumerals.containsKey(characters)) {
                    value += prefixedNumerals[characters]!!
                    romanRepresentation = romanRepresentation.substring(2)
                } else {
                    value += valuesByCharacter[characters[0]]!!
                    romanRepresentation = romanRepresentation.substring(1)
                }
            } else {
                val characters = romanRepresentation.take(1)

                value += valuesByCharacter[characters[0]]!!

                romanRepresentation = romanRepresentation.substring(1)
            }
        }

        return value
    }

    // https://leetcode.com/problems/minimum-falling-path-sum
    fun minFallingPathSum(matrix: Array<IntArray>): Int {
        var minFallingSum = Int.MAX_VALUE

        val cache = mutableMapOf<Location, Int>()

        for (col in matrix.indices) {
            minFallingSum = min(
                minFallingSum,
                doMinFallingPathSum(matrix, Location(0, col), cache)
            )
        }

        return minFallingSum
    }

    private fun doMinFallingPathSum(
        matrix: Array<IntArray>,
        location: Location,
        cache: MutableMap<Location, Int>
    ): Int {
        if (location.col < 0 || location.col == matrix.size) {
            return Int.MAX_VALUE
        }

        if (location.row == matrix.size - 1) {
            return matrix[location.row][location.col]
        }

        if (cache.contains(location)) {
            return cache[location]!!
        }

        val left = doMinFallingPathSum(matrix, Location(location.row + 1, location.col - 1), cache)
        val middle = doMinFallingPathSum(matrix, Location(location.row + 1, location.col), cache)
        val right = doMinFallingPathSum(matrix, Location(location.row + 1, location.col + 1), cache)

        cache[location] = min(left, min(middle, right)) + matrix[location.row][location.col]

        return cache[location]!!
    }

    fun sumSubarrayMins(arr: IntArray): Int {
        val divisor = 1000000007

        val stack = ArrayDeque<Int>()

        var sumOfMinimums: Long = 0

        for (i in 0..arr.size) {

            while (stack.isNotEmpty() && (i == arr.size || arr[stack.peek()] >= arr[i])) {
                val mid = stack.pop()
                val leftBoundary = if (stack.isEmpty()) -1 else stack.peek()
                val rightBoundary = i

                val count = (mid - leftBoundary) * (rightBoundary - mid) % divisor

                sumOfMinimums += (count * arr[mid]) % divisor
                sumOfMinimums %= divisor
            }
        }

        return sumOfMinimums.toInt()
    }

    // https://leetcode.com/problems/set-mismatch/description/
    fun findErrorNums(nums: IntArray): IntArray {
        val n = nums.size
        val expectedSum = n * (n + 1) / 2

        val countsByNumber = nums.asIterable().groupingBy { it }.eachCount()

        val duplicate = countsByNumber.entries.find { it.value > 1 }?.key

        val missing = expectedSum - countsByNumber.keys.sum()

        return intArrayOf(duplicate!!, missing)
    }

    // https://leetcode.com/problems/search-insert-position/description/
    fun searchInsert(nums: IntArray, target: Int): Int {
        var (left, right) = 0 to nums.size - 1

        var insertionPoint: Int = -1

        while (left <= right) {
            val midpoint = (left + right) / 2

            when {
                nums[midpoint] == target || (midpoint - 1 >= 0 && midpoint + 1 <= nums.size - 1 && nums[midpoint - 1] < target && nums[midpoint + 1] > target) -> {
                    insertionPoint = if (target > nums[midpoint]) midpoint + 1 else midpoint
                    break
                }

                target > nums[midpoint] -> left = midpoint + 1
                else -> right = midpoint - 1
            }
        }

        if (insertionPoint == -1) {
            insertionPoint = left
        }

        return insertionPoint
    }

    fun divideArray(nums: IntArray, k: Int): Array<IntArray> {
        nums.sort()

        return Array<IntArray>(nums.size / 3) {
            println(it)
            val i = it * 3
            if (nums[i + 2] - nums[i] > k) return emptyArray()
            intArrayOf(nums[i], nums[i + 1], nums[i + 2])
        }
    }

    // https://leetcode.com/problems/linked-list-cycle/description/
    fun hasCycle(head: ListNode?): Boolean {
        var slow = head
        var fast = head

        while (fast?.next != null) {
            fast = fast.next?.next
            slow = slow?.next

            if (fast == slow) {
                return true
            }
        }

        return false
    }

    // https://leetcode.com/problems/linked-list-cycle-ii
    fun detectCycle(head: ListNode?): ListNode? {
        val cycleNode = findCycleNode(head) ?: return null

        var slow = head
        var other = cycleNode

        while (slow != other) {
            slow = slow!!.next
            other = other.next!!
        }

        return slow
    }

    private fun findCycleNode(head: ListNode?): ListNode? {
        var slow = head
        var fast = head

        while (fast?.next != null) {
            fast = fast.next?.next
            slow = slow?.next

            if (fast == slow) {
                return fast
            }
        }

        return null
    }

    // https://leetcode.com/problems/remove-nth-node-from-end-of-list/
    fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
        val length = head.length()

        if (length == 0) {
            return null
        }

        val removalIndex = length - n

        if (removalIndex == 0) {
            return head?.next
        }

        val previousNode = head.getNodeAt(removalIndex - 1)
        previousNode?.next = previousNode?.next?.next

        return head
    }

    private fun ListNode?.length(): Int {
        if (this == null) {
            return 0
        }

        var length = 0

        var current = this

        while (current != null) {
            current = current.next
            length++
        }

        return length
    }

    private fun ListNode?.getNodeAt(index: Int): ListNode? {
        if (index == 0) {
            return this
        }

        return this?.next.getNodeAt(index - 1)
    }

    // https://leetcode.com/problems/partition-array-for-maximum-sum
    fun maxSumAfterPartitioning(nums: IntArray, k: Int): Int {
        val memo = IntArray(nums.size) { -1 }
        return doMaxSum(nums, k, memo, 0)
    }

    private fun doMaxSum(nums: IntArray, k: Int, memo: IntArray, start: Int): Int {
        val n = nums.size

        if (start >= n) {
            return 0
        }

        if (memo[start] != -1) {
            return memo[start]
        }

        var (currentMax, answer) = 0 to 0

        val end = min(n, start + k)

        for (i in start until end) {
            currentMax = max(currentMax, nums[i])
            answer = max(answer, currentMax * (i - start + 1) + doMaxSum(nums, k, memo, i + 1))
        }

        memo[start] = answer

        return memo[start]
    }

    fun returnToBoundaryCount(nums: IntArray): Int {
        var position = 0

        var boundaryTouches = 0

        for (move in nums) {
            position += move

            if (position == 0) {
                boundaryTouches++
            }
        }

        return boundaryTouches
    }

    // https://leetcode.com/problems/length-of-last-word/description/
    fun lengthOfLastWord(word: String): Int = word.trim().split(" ").last().length

    // https://leetcode.com/problems/baseball-game/description/
    fun calPoints(operations: Array<String>): Int {
        val record = mutableListOf<Int>()

        for (operation in operations) {
            when (operation) {
                "+" -> record.add(record[record.size - 1] + record[record.size - 2])
                "D" -> record.add(record[record.size - 1] * 2)
                "C" -> record.removeLast()
                else -> record.add(operation.toInt())
            }
        }

        return record.sum()
    }

    // https://leetcode.com/problems/robot-return-to-origin/
    fun judgeCircle(moves: String): Boolean {
        val position = Point(0, 0)

        for (move in moves) {
            when (move) {
                'U' -> position.y += 1
                'D' -> position.y -= 1
                'L' -> position.x -= 1
                'R' -> position.x += 1
            }
        }

        return position.x == 0 && position.y == 0
    }

    fun tictactoe(moves: Array<IntArray>): String {
        val symbolsByLocation = mutableMapOf<Location, String>()

        for ((i, move) in moves.withIndex()) {
            val character = if (i % 2 == 0) "X" else "Y"
            symbolsByLocation[Location(move[0], move[1])] = character
        }

        for (row in 0..2) {
            val characters = mutableSetOf<String>()

            characters.add(symbolsByLocation.getOrDefault(Location(row, 0), ""))
            characters.add(symbolsByLocation.getOrDefault(Location(row, 1), ""))
            characters.add(symbolsByLocation.getOrDefault(Location(row, 2), ""))

            if (characters.size == 1 && !characters.contains("")) {
                return if (characters.first() == "X") "A" else "B"
            }
        }

        for (col in 0..2) {
            val characters = mutableSetOf<String>()

            characters.add(symbolsByLocation.getOrDefault(Location(0, col), ""))
            characters.add(symbolsByLocation.getOrDefault(Location(1, col), ""))
            characters.add(symbolsByLocation.getOrDefault(Location(2, col), ""))

            if (characters.size == 1 && !characters.contains("")) {
                return if (characters.first() == "X") "A" else "B"
            }
        }

        var characters = mutableSetOf<String>()

        characters.add(symbolsByLocation.getOrDefault(Location(0, 0), ""))
        characters.add(symbolsByLocation.getOrDefault(Location(1, 1), ""))
        characters.add(symbolsByLocation.getOrDefault(Location(2, 2), ""))

        if (characters.size == 1 && !characters.contains("")) {
            return if (characters.first() == "X") "A" else "B"
        }

        characters = mutableSetOf<String>()

        characters.add(symbolsByLocation.getOrDefault(Location(2, 0), ""))
        characters.add(symbolsByLocation.getOrDefault(Location(1, 1), ""))
        characters.add(symbolsByLocation.getOrDefault(Location(0, 2), ""))

        if (characters.size == 1 && !characters.contains("")) {
            return if (characters.first() == "X") "A" else "B"
        }

        return if (moves.size == 9) "Draw" else "Pending"
    }

    fun maximumWealth(accounts: Array<IntArray>): Int {
        val wealthByCustomer = mutableMapOf<Int, Int>()

        for ((customer, customerAccounts) in accounts.withIndex()) {
            wealthByCustomer[customer] = customerAccounts.sum()
        }

        return wealthByCustomer.values.max()
    }

    // https://leetcode.com/problems/first-unique-character-in-a-string/
    fun firstUniqChar(word: String): Int {
        val countsByCharacter = word.groupingBy { it }.eachCount()

        for ((index, char) in word.withIndex()) {
            if (countsByCharacter[char] == 1) {
                return index
            }
        }

        return -1
    }

    fun groupAnagrams(words: Array<String>): List<List<String>> {
        val anagramsByKey = mutableMapOf<String, MutableList<String>>()

        for (word in words) {
            val k = key(word)
            val anagrams = anagramsByKey.getOrDefault(k, mutableListOf())
            anagrams.add(word)
            anagramsByKey[k] = anagrams
        }

        return anagramsByKey.values.toList()
    }

    fun getIntersectionNode(headA: ListNode?, headB: ListNode?): ListNode? {
        val aNodes = ArrayDeque<ListNode?>()
        val bNodes = ArrayDeque<ListNode?>()

        var aHead = headA
        var bHead = headB

        while (aHead != null) {
            aNodes.add(aHead)
            aHead = aHead.next
        }

        while (bHead != null) {
            bNodes.add(bHead)
            bHead = bHead.next
        }

        var intersection: ListNode? = null

        while (aNodes.peekLast() == bNodes.peekLast()) {
            intersection = aNodes.pollLast()
            bNodes.pollLast()
        }

        return intersection
    }

    fun modifiedMatrix(matrix: Array<IntArray>): Array<IntArray> {
        val answer = mutableListOf<IntArray>()
        val maximumsByColumn = mutableMapOf<Int, Int>()

        for (col in 0 until matrix[0].size) {
            var max = Int.MIN_VALUE

            for (row in matrix.indices) {
                if (matrix[row][col] > max) {
                    max = matrix[row][col]
                }
            }

            maximumsByColumn[col] = max
        }

        for (row in matrix) {
            val values = mutableListOf<Int>()

            for ((col, value) in row.withIndex()) {
                if (value == -1) {
                    values.add(maximumsByColumn[col]!!)
                } else {
                    values.add(value)
                }
            }

            answer.add(values.toIntArray())
        }

        return answer.toTypedArray()
    }

    fun countMatchingSubarrays(nums: IntArray, pattern: IntArray): Int {
        val subArraySize = pattern.size + 1

        var matches = 0

        for (i in 0..nums.size - subArraySize) {
            if (matchesPattern(nums.slice(i..<i + subArraySize).toIntArray(), pattern)) {
                matches++
            }
        }

        return matches
    }

    private fun matchesPattern(subArray: IntArray, pattern: IntArray): Boolean {
        for ((i, value) in pattern.withIndex()) {
            when (value) {
                -1 -> {
                    if (subArray[i] <= subArray[i + 1]) {
                        return false
                    }
                }

                0 -> {
                    if (subArray[i] != subArray[i + 1]) {
                        return false
                    }
                }

                1 -> {
                    if (subArray[i] >= subArray[i + 1]) {
                        return false
                    }
                }
            }

        }

        return true
    }

    fun preorder(root: Node?): List<Int> {
        if (root == null) {
            return emptyList()
        }

        val values = mutableListOf<Int>()

        values.add(root.`val`)

        for (child in root.children) {
            values.addAll(preorder(child))
        }

        return values
    }

    // https://leetcode.com/problems/find-first-palindromic-string-in-the-array/description/?envType=daily-question&envId=2024-02-13
    fun firstPalindrome(words: Array<String>): String {
        return words.find { it.isPalindrome() } ?: ""
    }

    private fun String.isPalindrome(): Boolean {
        var (start, end) = 0 to this.length - 1

        while (start < end) {
            if (this[start] != this[end]) {
                return false
            }

            start++
            end--
        }

        return true
    }

    // https://leetcode.com/problems/rearrange-array-elements-by-sign/description/
    fun rearrangeArray(nums: IntArray): IntArray {
        val positives = nums.filter { it > 0 }
        val negatives = nums.filter { it < 0 }

        var valuesAdded = 0
        var i = 0

        while (valuesAdded < nums.size) {
            nums[valuesAdded++] = positives[i]
            nums[valuesAdded++] = negatives[i]
            i++
        }

        return nums
    }

    fun frequencySort(string: String): String {
        val countsByCharacter = string.groupingBy { it }.eachCount()

        val descendingCounts = countsByCharacter.entries.sortedBy { it.value }.reversed()

        val result = StringBuilder()

        for ((character, count) in descendingCounts) {
            for (i in 1..count) {
                result.append(character)
            }
        }

        return result.toString()
    }

    // https://leetcode.com/problems/least-number-of-unique-integers-after-k-removals/description/?envType=daily-question&envId=2024-02-16
    fun findLeastNumOfUniqueInts(array: IntArray, k: Int): Int {
        val countsByNumber = array.asIterable().groupingBy { it }.eachCount()

        val compareByCount: Comparator<Map.Entry<Int, Int>> = compareBy { it.value }

        val minHeap = PriorityQueue(compareByCount)
        minHeap.addAll(countsByNumber.entries)

        var numbersRemoved = 0

        while (numbersRemoved < k && minHeap.isNotEmpty()) {
            val leastUnique = minHeap.remove()
            val amountRemoved = min(leastUnique.value, k - numbersRemoved)

            if (amountRemoved < leastUnique.value) {
                val amountRemaining = leastUnique.value - amountRemoved
                minHeap.add(AbstractMap.SimpleEntry(leastUnique.key, amountRemaining))
            }

            numbersRemoved += amountRemoved
        }

        return minHeap.size
    }

    // https://leetcode.com/problems/furthest-building-you-can-reach/description/
    fun furthestBuilding(heights: IntArray, bricks: Int, ladders: Int): Int {
        val ladderAllocations = PriorityQueue<Int>(max(1, ladders))

        var bricksUsed = 0

        for (i in 0..heights.size - 2) {
            val currentHeight = heights[i]
            val nextHeight = heights[i + 1]

            val climb = nextHeight - currentHeight

            if (climb <= 0) {
                continue
            }

            ladderAllocations.add(climb)

            if (ladderAllocations.size <= ladders) {
                continue
            }

            bricksUsed += ladderAllocations.remove()

            if (bricksUsed > bricks) {
                return i
            }
        }

        return heights.size - 1
    }

    // https://leetcode.com/problems/power-of-two/description/
    fun isPowerOfTwo(n: Int): Boolean {
        if (n == 0) return false

        var value = n

        while (value % 2 == 0) value /= 2

        return value == 1
    }

    // https://leetcode.com/problems/missing-number/description/
    fun missingNumber(nums: IntArray): Int {
        val n = nums.size
        val expectedSum = (n * (n + 1)) / 2
        return abs(nums.sum() - expectedSum)
    }

    // https://leetcode.com/problems/maximum-product-of-two-elements-in-an-array/description/
    fun maxProduct(nums: IntArray): Int {
        val pq = PriorityQueue<Int>()

        for (num in nums) {
            pq.add(num)

            if (pq.size > 2) {
                pq.remove()
            }
        }

        return pq.map { it - 1 }.reduce { acc, i -> acc * i }
    }

    // https://leetcode.com/problems/find-the-town-judge/description/
    fun findJudge(n: Int, trusts: Array<IntArray>): Int {
        if (trusts.isEmpty() && n == 1) return n

        val trustors = mutableSetOf<Int>()
        val trustCountsByTrustee = mutableMapOf<Int, Int>()

        for ((trustor, trustee) in trusts) {
            trustors.add(trustor)
            var count = trustCountsByTrustee.getOrPut(trustee) { 0 }
            count++
            trustCountsByTrustee[trustee] = count
        }

        return trustCountsByTrustee.entries.filter { it.value == n - 1 }.find { !trustors.contains(it.key) }?.key ?: -1
    }

    // https://leetcode.com/problems/kth-largest-element-in-an-array/description/
    fun findKthLargest(nums: IntArray, k: Int): Int {
        val minHeap = PriorityQueue<Int>()

        for (num in nums) {
            minHeap.add(num)

            if (minHeap.size > k) {
                minHeap.remove()
            }
        }

        return minHeap.peek()
    }

    // https://leetcode.com/problems/top-k-frequent-elements/description/
    fun topKFrequent(nums: IntArray, k: Int): IntArray {
        val compareByCount: Comparator<Map.Entry<Int, Int>> = compareBy { it.value }
        val countsByNumber = nums.asIterable().groupingBy { it }.eachCount()

        val minHeap = PriorityQueue(compareByCount)

        for (entry in countsByNumber.entries) {
            minHeap.add(entry)

            if (minHeap.size > k) {
                minHeap.remove()
            }
        }

        return minHeap.map { it.key }.toIntArray()
    }

    // https://leetcode.com/problems/valid-word-square/description/?envType=study-plan-v2&envId=programming-skills
    fun validWordSquare(words: List<String>): Boolean {
        for ((row, word) in words.withIndex()) {
            val columnarWord = getWordFromColumn(row, words)

            if (columnarWord != word) {
                return false
            }
        }

        return true
    }

    private fun getWordFromColumn(column: Int, words: List<String>): String {
        val sb = StringBuilder()

        for (word in words) {
            if (word.length > column) {
                sb.append(word[column])
            }
        }

        return sb.toString()
    }

    // https://leetcode.com/problems/cheapest-flights-within-k-stops/description/
    fun findCheapestPrice(n: Int, flights: Array<IntArray>, src: Int, dst: Int, k: Int): Int {
        // Bellman Ford Algorithm
        var distancesFromSource = IntArray(n) { Int.MAX_VALUE }
        distancesFromSource[src] = 0

        for (i in 0..k) {
            val temp = distancesFromSource.copyOf(n)

            for (flight in flights) {
                val from = flight[0]
                val to = flight[1]
                val cost = flight[2]

                if (distancesFromSource[from] != Int.MAX_VALUE) {
                    temp[to] = min(temp[to], distancesFromSource[from] + cost)
                }
            }

            distancesFromSource = temp
        }

        return if (distancesFromSource[dst] == Int.MAX_VALUE) -1 else distancesFromSource[dst]
    }

    fun isSameTree(p: TreeNode?, q: TreeNode?): Boolean {
        if (p == null && q == null) {
            return true
        }

        if (p?.`val` != q?.`val`) {
            return false
        }

        return isSameTree(p?.left, q?.left) && isSameTree(p?.right, q?.right)
    }

    fun mergeKLists(lists: Array<ListNode?>): ListNode? {
        var head: ListNode? = null
        var last: ListNode? = null

        val valueComparator = compareBy<ListNode?> { it?.`val` }
        val pq = PriorityQueue<ListNode?>(valueComparator)

        for (list in lists) {
            pq.add(list)
        }

        while (pq.isNotEmpty()) {
            var current = pq.remove()

            if (head == null) {
                head = current
            }

            if (last != null) {
                last.next = current
            }

            last = current

            current = current?.next

            if (current != null) {
                pq.add(current)
            }
        }

        return head
    }

    private var maxDepth = -1
    private var bottomMostValue = Int.MIN_VALUE
    fun findBottomLeftValue(root: TreeNode?, depth: Int = 0): Int {
        if (depth > maxDepth) {
            bottomMostValue = root?.`val`!!
            maxDepth = depth
        }

        if (root?.left != null) {
            findBottomLeftValue(root.left, depth + 1)
        }

        if (root?.right != null) {
            findBottomLeftValue(root.right, depth + 1)
        }

        return bottomMostValue
    }

    data class Meeting(val meetingWith: Int, val meetingTime: Int)

    fun findAllPeople(n: Int, meetings: Array<IntArray>, firstPerson: Int): List<Int> {
        val meetingsByPerson = mutableMapOf<Int, MutableCollection<Meeting>>()

        for (rawMeeting in meetings) {
            val personA = rawMeeting[0]
            val personB = rawMeeting[1]
            val meetingTime = rawMeeting[2]

            var individualMeetings = meetingsByPerson.getOrPut(personA) { mutableListOf() }
            individualMeetings.add(Meeting(personB, meetingTime))

            individualMeetings = meetingsByPerson.getOrPut(personB) { mutableListOf() }
            individualMeetings.add(Meeting(personA, meetingTime))
        }

        val compareByMeetingTime: Comparator<Meeting> = compareBy { it.meetingTime }

        val minHeap = PriorityQueue(n, compareByMeetingTime)

        minHeap.add(Meeting(0, 0))
        minHeap.add(Meeting(firstPerson, 0))

        val seen = mutableSetOf<Int>()

        while (minHeap.isNotEmpty()) {
            val meeting = minHeap.poll()

            if (seen.contains(meeting.meetingWith)) {
                continue
            }

            seen.add(meeting.meetingWith)

            for (otherMeeting in meetingsByPerson.getOrDefault(meeting.meetingWith, emptyList())) {
                if (!seen.contains(otherMeeting.meetingWith) && otherMeeting.meetingTime >= meeting.meetingTime) {
                    minHeap.add(otherMeeting)
                }
            }
        }

        return seen.toList()
    }

    data class NodeLevel(val node: TreeNode, val level: Int = 0)

    fun isEvenOddTree(root: TreeNode?): Boolean {
        if (root == null) {
            return false
        }

        val nodeValuesByLevel = mutableMapOf<Int, MutableCollection<Int>>()

        val q = ArrayDeque<NodeLevel>()

        q.add(NodeLevel(root))

        while (q.isNotEmpty()) {
            val nodeLevel = q.remove()
            val node = nodeLevel.node

            val values = nodeValuesByLevel.getOrPut(nodeLevel.level) { emptyList<Int>().toMutableList() }
            values.add(node.`val`)

            if (node.left != null) {
                q.add(NodeLevel(node.left!!, nodeLevel.level + 1))
            }

            if (node.right != null) {
                q.add(NodeLevel(node.right!!, nodeLevel.level + 1))
            }
        }

        for ((level, values) in nodeValuesByLevel) {
            if (level % 2 == 0) {
                if (!values.isSortedAscending() || !values.areAllOdd()) {
                    return false
                }
            } else {
                if (!values.isSortedDescending() || !values.areAllEven()) {
                    return false
                }

            }
        }


        return true
    }

    private fun MutableCollection<Int>.isSortedAscending(): Boolean {
        if (this.isEmpty()) {
            return false
        }

        var last = this.first()

        for ((i, value) in this.withIndex()) {
            if (i == 0) {
                continue
            }

            if (value <= last) {
                return false
            }

            last = value
        }

        return true
    }

    private fun MutableCollection<Int>.isSortedDescending(): Boolean {
        if (this.isEmpty()) {
            return false
        }

        var last = this.first()

        for ((i, value) in this.withIndex()) {
            if (i == 0) {
                continue
            }

            if (value >= last) {
                return false
            }

            last = value
        }

        return true
    }

    private fun MutableCollection<Int>.areAllEven() = this.all { it % 2 == 0 }

    private fun MutableCollection<Int>.areAllOdd() = this.all { it % 2 != 0 }
    fun maximumOddBinaryNumber(s: String): String {
        val sb = StringBuilder()

        val countsByCharacter = s.groupingBy { it }.eachCount().toMutableMap()

        while (countsByCharacter['1']!! > 1) {
            sb.append('1')
            countsByCharacter['1'] = countsByCharacter['1']!! - 1
        }

        while (countsByCharacter.containsKey('0') && countsByCharacter['0']!! > 0) {
            sb.append('0')
            countsByCharacter['0'] = countsByCharacter['0']!! - 1
        }

        sb.append('1')

        return sb.toString()
    }

    private data class Island(val locations: MutableCollection<Location>)

    fun numIslands(grid: Array<CharArray>): Int {
        val islands = discoverIslands(grid)
        return islands.size
    }

    private fun discoverIslands(grid: Array<CharArray>): Collection<Island> {
        val islands = mutableSetOf<Island>()

        val visited = mutableSetOf<Location>()

        var unvisitedLocation = findFirstUnvisitedLandMass(grid, visited)

        while (unvisitedLocation.isPresent) {
            val coordinates = discoverContiguousLand(unvisitedLocation.get(), grid, visited)
            islands.add(Island(coordinates))
            coordinates.map { visited.add(it) }
            unvisitedLocation = findFirstUnvisitedLandMass(grid, visited)
        }

        return islands
    }

    private fun findFirstUnvisitedLandMass(grid: Array<CharArray>, visited: MutableSet<Location>): Optional<Location> {
        for (row in grid.indices) {
            for (col in grid[row].indices) {
                val l = Location(row, col)
                val isLand = grid[row][col] == '1'

                if (isLand && !visited.contains(l)) {
                    return Optional.of(l)
                }
            }
        }

        return Optional.empty()
    }

    private fun discoverContiguousLand(
        location: Location,
        grid: Array<CharArray>,
        visited: MutableSet<Location>
    ): MutableCollection<Location> {
        val locations = mutableListOf<Location>()

        val q = ArrayDeque<Location>()

        if (!visited.contains(location)) {
            q.add(location)
        }

        while (q.isNotEmpty()) {
            val current = q.remove()

            visited.add(current)
            locations.add(current)

            val above = Location(current.row - 1, current.col)
            val right = Location(current.row, current.col + 1)
            val below = Location(current.row + 1, current.col)
            val left = Location(current.row, current.col - 1)

            val hasLandAbove = current.row - 1 >= 0 && grid[current.row - 1][current.col] == '1'
            val hasLandRight = current.col + 1 < grid[current.row].size && grid[current.row][current.col + 1] == '1'
            val hasLandBelow = current.row + 1 < grid.size && grid[current.row + 1][current.col] == '1'
            val hasLandLeft = current.col - 1 >= 0 && grid[current.row][current.col - 1] == '1'

            if (hasLandAbove) {
                if (!visited.contains(above)) {
                    q.add(above)
                }
            }

            if (hasLandRight) {
                if (!visited.contains(right)) {
                    q.add(right)
                }
            }

            if (hasLandBelow) {
                if (!visited.contains(below)) {
                    q.add(below)
                }
            }

            if (hasLandLeft) {
                if (!visited.contains(left)) {
                    q.add(left)
                }
            }
        }

        return locations
    }

    fun sortedSquares(nums: IntArray) = nums.map { it * it }.sorted().toIntArray()
    fun buddyStrings(s: String, goal: String): Boolean {
        if (s.length != goal.length) return false

        val sCountsByCharacter = s.asIterable().groupingBy { it }.eachCount()

        var firstMismatchedIndex = Optional.empty<Int>()
        var secondMismatchedIndex = Optional.empty<Int>()

        for ((i, c) in goal.withIndex()) {
            if (s[i] != c) {
                if (!firstMismatchedIndex.isPresent) {
                    firstMismatchedIndex = Optional.of(i)
                } else if (!secondMismatchedIndex.isPresent) {
                    secondMismatchedIndex = Optional.of(i)
                } else {
                    return false
                }
            }
        }

        if (firstMismatchedIndex.isEmpty && secondMismatchedIndex.isEmpty) {
            return sCountsByCharacter.values.any { it > 1 }
        }

        if (secondMismatchedIndex.isEmpty) return false

        return s[firstMismatchedIndex.get()] == goal[secondMismatchedIndex.get()] &&
                s[secondMismatchedIndex.get()] == goal[firstMismatchedIndex.get()]
    }

    fun bagOfTokensScore(tokens: IntArray, power: Int): Int {
        tokens.sort()

        var score = 0
        var currentPower = power

        val deque = ArrayDeque<Int>()

        for (token in tokens) {
            deque.add(token)
        }

        while (deque.isNotEmpty()) {
            if (currentPower >= deque.peekFirst()) {
                currentPower -= deque.removeFirst()
                score++
            } else if (deque.size > 1 && score > 0) {
                currentPower += deque.removeLast()
                score--
            } else {
                return score
            }
        }

        return score
    }

    fun minimumLength(s: String): Int {
        var start = 0
        var end = s.length - 1

        while (start < end && s[start] == s[end]) {
            val c = s[start]

            while (start <= end && s[start] == c) {
                start++
            }

            while (end > start && s[end] == c) {
                end--
            }
        }

        return end - start + 1
    }

    fun findMissingRanges(nums: IntArray, lower: Int, upper: Int): List<List<Int>> {
        val missingRanges = mutableListOf<MutableList<Int>>()

        if (nums.isEmpty()) {
            return listOf(listOf(lower, upper))
        }

        if (lower < nums[0]) {
            missingRanges.add(mutableListOf(lower, nums[0]-1))
        }

        for (i in 0..<nums.size-1) {
            val delta = nums[i+1]-nums[i]

            if (delta > 1) {
                missingRanges.add(mutableListOf(nums[i]+1, nums[i+1]-1))
            }
        }

        if (upper > nums.last()) {
            missingRanges.add(mutableListOf(nums.last()+1, upper))
        }

        return missingRanges
    }

    fun confusingNumber(n: Int): Boolean {
        val invalidDigits = setOf(2,3,4,5,7)
        val newDigitsBySource = mapOf(0 to 0, 1 to 1, 6 to 9, 8 to 8, 9 to 6)

        val digits = n.digits()

        if (digits.intersect(invalidDigits).isNotEmpty()) {
            return false
        }

        val rotatedDigits = mutableListOf<Int>()

        for (d in digits.reversed()) {
            rotatedDigits.add(newDigitsBySource[d]!!)
        }

        return rotatedDigits != digits
    }

    private fun Int.digits(): Collection<Int> {
        val digits = mutableListOf<Int>()

        var v = this

        while (v > 0) {
            digits.add(v % 10)
            v /= 10
        }

        return digits.reversed()
    }

    fun intersection(nums1: IntArray, nums2: IntArray): IntArray {
        val uniquesIn1 = nums1.toSet()
        val common = mutableSetOf<Int>()

        for (n in nums2) {
            if (uniquesIn1.contains(n)) {
                common.add(n)
            }
        }

        return common.toIntArray()
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

class Node(var `val`: Int, var children: MutableList<Node?> = ArrayList<Node?>())

@Suppress("unused")
class ParkingSystem(big: Int, medium: Int, small: Int) {
    private enum class ParkingSpotType(val id: Int) {
        BIG(1),
        MEDIUM(2),
        SMALL(3);

        companion object {
            fun typeById(id: Int): ParkingSpotType {
                return when (id) {
                    1 -> BIG
                    2 -> MEDIUM
                    3 -> SMALL
                    else -> BIG
                }
            }
        }
    }

    private val spotAvailabilityByType = mutableMapOf<ParkingSpotType, Int>()

    init {
        spotAvailabilityByType[ParkingSpotType.BIG] = big
        spotAvailabilityByType[ParkingSpotType.MEDIUM] = medium
        spotAvailabilityByType[ParkingSpotType.SMALL] = small
    }

    fun addCar(carType: Int): Boolean {
        val spotType = ParkingSpotType.typeById(carType)

        if (spotAvailabilityByType[spotType]!! > 0) {
            var slots = spotAvailabilityByType[spotType]!!
            slots--
            spotAvailabilityByType[spotType] = slots
            return true
        }

        return false
    }

}