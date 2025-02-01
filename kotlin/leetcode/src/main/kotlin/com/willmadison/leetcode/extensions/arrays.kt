package com.willmadison.leetcode.extensions

import com.willmadison.leetcode.Location
import com.willmadison.leetcode.Point
import java.util.*
import kotlin.math.*


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


// https://leetcode.com/problems/contains-duplicate
fun containsDuplicate(nums: IntArray): Boolean {
    return nums.asIterable().groupingBy { it }.eachCount().any { it.value >= 2 }
}

// https://leetcode.com/problems/maximum-subarray/
fun maxSubArray(nums: IntArray): Int {
    var maxSum = Int.MIN_VALUE
    var sum = Int.MIN_VALUE

    for (v in nums) {
        sum = Integer.max(0, sum)
        sum += v
        maxSum = Integer.max(sum, maxSum)
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
        maxProfit = Integer.max(totalProfit, maxProfit)
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


@Suppress("unused")
fun findDuplicate(nums: IntArray): Int {
    var slow = nums[0]
    var fast = nums[0]

    do {
        slow = nums[slow]
        fast = nums[nums[fast]]
    } while (slow != fast)

    slow = nums[0]

    while (slow != fast) {
        slow = nums[slow]
        fast = nums[fast]
    }

    return fast
}

fun findDuplicates(nums: IntArray): List<Int> {
    val dupes = mutableListOf<Int>()

    for (i in nums) {
        if (nums[abs(i) - 1] < 0) {
            dupes.add(abs(i))
            continue
        }

        nums[abs(i) - 1] *= -1
    }

    return dupes
}

fun firstMissingPositive(nums: IntArray): Int {
    val n = nums.size
    val hasAOne = nums.contains(1)

    for (i in nums.indices) {
        if (nums[i] <= 0 || nums[i] > n) {
            nums[i] = 1
        }
    }

    if (!hasAOne) return 1

    for (i in nums.indices) {
        val v = abs(nums[i])

        if (v == n) {
            nums[0] = -1 * abs(nums[0])
        } else {
            nums[v] = -1 * abs(nums[v])
        }
    }

    for (i in 1..<nums.size) {
        if (nums[i] > 0) {
            return i
        }
    }

    if (nums[0] > 0) {
        return n
    }

    return n + 1
}

fun numSubarraysWithSum(nums: IntArray, goal: Int): Int {
    var numSubarrays = 0
    var currentSum = 0
    val occurrencesByPrefixSum = mutableMapOf<Int, Int>()

    for (num in nums) {
        currentSum += num

        if (currentSum == goal) {
            numSubarrays++
        }

        numSubarrays += occurrencesByPrefixSum[currentSum - goal] ?: 0

        occurrencesByPrefixSum[currentSum] = occurrencesByPrefixSum.getOrDefault(currentSum, 0) + 1
    }

    return numSubarrays
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

fun findMissingRanges(nums: IntArray, lower: Int, upper: Int): List<List<Int>> {
    val missingRanges = mutableListOf<MutableList<Int>>()

    if (nums.isEmpty()) {
        return listOf(listOf(lower, upper))
    }

    if (lower < nums[0]) {
        missingRanges.add(mutableListOf(lower, nums[0] - 1))
    }

    for (i in 0..<nums.size - 1) {
        val delta = nums[i + 1] - nums[i]

        if (delta > 1) {
            missingRanges.add(mutableListOf(nums[i] + 1, nums[i + 1] - 1))
        }
    }

    if (upper > nums.last()) {
        missingRanges.add(mutableListOf(nums.last() + 1, upper))
    }

    return missingRanges
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

fun countSubarrays(nums: IntArray, k: Int): Long {
    var subarrays = 0L

    val max = nums.max()

    var start = 0

    var occurrencesOfMax = 0

    for (end in nums.indices) {
        if (nums[end] == max) {
            occurrencesOfMax++
        }

        while (k == occurrencesOfMax) {
            if (nums[start] == max) {
                occurrencesOfMax--
            }
            start++
        }

        subarrays += start
    }

    return subarrays
}

fun countSubarrays(nums: IntArray, minK: Int, maxK: Int): Long {
    var subarrays = 0L

    var minPosition = -1
    var maxPosition = -1
    var leftBound = -1

    for (i in nums.indices) {
        if (nums[i] < minK || nums[i] > maxK) leftBound = i

        if (nums[i] == minK) minPosition = i
        if (nums[i] == maxK) maxPosition = i

        subarrays += maxOf(0, (min(maxPosition, minPosition) - leftBound)).toLong()
    }

    return subarrays
}

fun sortedSquares(nums: IntArray) = nums.map { it * it }.sorted().toIntArray()


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

// https://leetcode.com/problems/least-number-of-unique-integers-after-k-removals/description/?envType=daily-question&envId=2024-02-16
fun findLeastNumOfUniqueInts(array: IntArray, k: Int): Int {
    val countsByNumber = array.asIterable().groupingBy { it }.eachCount()

    val compareByCount: Comparator<Map.Entry<Int, Int>> = compareBy { it.value }

    val minHeap = PriorityQueue(compareByCount)
    minHeap.addAll(countsByNumber.entries)

    var numbersRemoved = 0

    while (numbersRemoved < k && minHeap.isNotEmpty()) {
        val leastUnique = minHeap.remove()
        val amountRemoved = Integer.min(leastUnique.value, k - numbersRemoved)

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
    val ladderAllocations = PriorityQueue<Int>(Integer.max(1, ladders))

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

@Suppress("unused")
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

@Suppress("unused")
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

    val end = Integer.min(n, start + k)

    for (i in start until end) {
        currentMax = Integer.max(currentMax, nums[i])
        answer = Integer.max(answer, currentMax * (i - start + 1) + doMaxSum(nums, k, memo, i + 1))
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

// https://leetcode.com/problems/minimum-falling-path-sum
fun minFallingPathSum(matrix: Array<IntArray>): Int {
    var minFallingSum = Int.MAX_VALUE

    val cache = mutableMapOf<Location, Int>()

    for (col in matrix.indices) {
        minFallingSum = Integer.min(
            minFallingSum,
            doMinFallingPathSum(matrix, Location(0, col), cache)
        )
    }

    return minFallingSum
}

//  https://leetcode.com/problems/minimum-falling-path-sum-ii/?envType=daily-question&envId=2024-04-30
fun minFallingPathSum2(grid: Array<IntArray>): Int {
    val memo = Array(grid.size) { IntArray(grid.size) }

    for (col in grid.indices) {
        memo[grid.size - 1][col] = grid[grid.size - 1][col]
    }

    for (row in grid.size - 2 downTo 0) {
        for (col in grid.indices) {
            var nextMinimum = Int.MAX_VALUE
            for (nextRowCol in grid.indices) {
                if (nextRowCol != col) {
                    nextMinimum = minOf(nextMinimum, memo[row + 1][nextRowCol])
                }
            }

            memo[row][col] = grid[row][col] + nextMinimum
        }
    }

    var answer = Int.MAX_VALUE
    for (col in grid.indices) {
        answer = minOf(answer, memo[0][col])
    }


    return answer
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

    cache[location] = Integer.min(left, Integer.min(middle, right)) + matrix[location.row][location.col]

    return cache[location]!!
}

@Suppress("unused")
fun sumSubarrayMins(arr: IntArray): Int {
    val divisor = 1000000007

    val stack = ArrayDeque<Int>()

    var sumOfMinimums: Long = 0

    for (i in 0..arr.size) {

        while (stack.isNotEmpty() && (i == arr.size || arr[stack.peek()] >= arr[i])) {
            val mid = stack.pop()
            val leftBoundary = if (stack.isEmpty()) -1 else stack.peek()

            val count = (mid - leftBoundary) * (i - mid) % divisor

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

    return Array(nums.size / 3) {
        println(it)
        val i = it * 3
        if (nums[i + 2] - nums[i] > k) return emptyArray()
        intArrayOf(nums[i], nums[i + 1], nums[i + 2])
    }
}

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

fun maxFrequencyElements(nums: IntArray): Int {
    val frequencyByNumber = nums.asIterable().groupingBy { it }.eachCount()
    val maxFrequency = frequencyByNumber.values.max()

    return frequencyByNumber.values.filter { it == maxFrequency }.sum()
}


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

fun numSubarrayProductLessThanK(nums: IntArray, k: Int): Int {
    if (k <= 1) return 0

    var start = 0

    var numSubarrays = 0
    var cumulativeProduct = 1

    for (end in nums.indices) {
        cumulativeProduct *= nums[end]

        while (cumulativeProduct >= k && start < nums.size) {
            cumulativeProduct /= nums[start++]
        }

        numSubarrays += end - start + 1
    }

    return numSubarrays
}

fun maxSubarrayLength(nums: IntArray, k: Int): Int {
    var start = 0

    var maxLength = 0

    val frequencyByNumber = mutableMapOf<Int, Int>()

    for (end in nums.indices) {
        val count = frequencyByNumber.getOrPut(nums[end]) { 0 }
        frequencyByNumber[nums[end]] = count + 1

        while (frequencyByNumber[nums[end]]!! > k) {
            frequencyByNumber[nums[start]] = frequencyByNumber[nums[start]]!! - 1
            start++
        }

        maxLength = max(maxLength, end - start + 1)
    }

    return maxLength
}

fun subarraysWithKDistinct(nums: IntArray, k: Int): Int {
    return subarrayWithAtMostKDistinct(nums, k) -
            subarrayWithAtMostKDistinct(nums, k - 1)
}

fun subarrayWithAtMostKDistinct(nums: IntArray, k: Int): Int {
    val countsByValue = mutableMapOf<Int, Int>()

    var numSubarrays = 0
    var start = 0

    for (end in nums.indices) {
        val count = countsByValue.getOrPut(nums[end]) { 0 }
        countsByValue[nums[end]] = count + 1

        while (countsByValue.size > k) {
            if (countsByValue.containsKey(nums[start])) {
                countsByValue[nums[start]] = countsByValue[nums[start]]!! - 1

                if (countsByValue[nums[start]] == 0) {
                    countsByValue.remove(nums[start])
                }

                start++
            }
        }

        numSubarrays += end - start + 1
    }

    return numSubarrays
}

// https://leetcode.com/problems/number-of-students-unable-to-eat-lunch/?envType=daily-question&envId=2024-04-30
fun countStudents(students: IntArray, sandwiches: IntArray): Int {
    val q = ArrayDeque<Int>()
    q.addAll(students.asIterable())

    val stack = ArrayDeque<Int>()
    stack.addAll(sandwiches.asIterable())

    var considerations = 0

    while (q.isNotEmpty() && stack.isNotEmpty()) {
        if (q.peek() == stack.peek()) {
            q.removeFirst()
            stack.pop()
            considerations = 0
        } else {
            q.add(q.removeFirst())
            considerations++
        }

        if (considerations == q.size) {
            break
        }
    }

    return q.size
}

// https://leetcode.com/problems/time-needed-to-buy-tickets/?envType=daily-question&envId=2024-04-30
fun timeRequiredToBuy(tickets: IntArray, k: Int): Int {
    var timeElapsed = 0

    val q = ArrayDeque<Int>()
    q.addAll(tickets.indices)

    while (tickets[k] > 0) {
        val customer = q.removeFirst()
        tickets[customer]--

        if (tickets[customer] > 0) {
            q.add(customer)
        }

        timeElapsed++
    }

    return timeElapsed
}

// https://leetcode.com/problems/reveal-cards-in-increasing-order/?envType=daily-question&envId=2024-04-30
fun deckRevealedIncreasing(deck: IntArray): IntArray {
    val newDeck = IntArray(deck.size)
    val q = ArrayDeque<Int>()
    q.addAll(deck.indices)

    deck.sort()

    for (i in deck.indices) {
        newDeck[q.removeFirst()] = deck[i]

        if (q.isNotEmpty()) {
            q.add(q.removeFirst())
        }
    }

    return newDeck
}

// https://leetcode.com/problems/trapping-rain-water/?envType=daily-question&envId=2024-04-30
fun trap(heights: IntArray): Int {
    var totalVolume = 0
    var i = 0
    val stack = ArrayDeque<Int>()

    while (i < heights.size) {
        while (stack.isNotEmpty() && heights[i] > heights[stack.peek()]) {
            val top = stack.pop()
            if (stack.isEmpty()) break

            val distance = i - stack.peek() - 1
            val boundedHeight = minOf(heights[i], heights[stack.peek()]) - heights[top]
            totalVolume += distance * boundedHeight
        }
        stack.push(i++)
    }

    return totalVolume
}

// https://leetcode.com/problems/maximal-rectangle/description/?envType=daily-question&envId=2024-04-30
fun maximalRectangle(matrix: Array<CharArray>): Int {
    if (matrix.isEmpty()) return 0

    var maxArea = 0
    val histogram = IntArray(matrix[0].size)

    for (i in matrix.indices) {
        for (j in matrix[0].indices) {
            histogram[j] = if (matrix[i][j] == '1') histogram[j] + 1 else 0
        }

        maxArea = maxOf(maxArea, maximalHistogramArea(histogram))
    }

    return maxArea
}

fun maximalHistogramArea(histogram: IntArray): Int {
    val stack = ArrayDeque<Int>()
    stack.push(-1)

    var maxArea = 0

    for (i in histogram.indices) {
        while (stack.peek() != -1 && histogram[stack.peek()] >= histogram[i]) {
            maxArea = maxOf(maxArea, histogram[stack.pop()] * (i - stack.peek() - 1))
        }
        stack.push(i)
    }

    while (stack.peek() != -1) maxArea =
        maxOf(maxArea, histogram[stack.pop()] * (histogram.size - stack.peek() - 1))

    return maxArea
}

// https://leetcode.com/problems/island-perimeter/?envType=daily-question&envId=2024-04-30
fun islandPerimeter(grid: Array<IntArray>): Int {
    var perimeter = 0

    for (row in grid.indices) {
        for (col in grid[0].indices) {
            if (grid[row][col] == 1) {
                perimeter += 4

                if (row > 0 && grid[row - 1][col] == 1) {
                    perimeter -= 2
                }

                if (col > 0 && grid[row][col - 1] == 1) {
                    perimeter -= 2
                }
            }
        }
    }

    return perimeter
}

fun maximumWealth(accounts: Array<IntArray>): Int {
    val wealthByCustomer = mutableMapOf<Int, Int>()

    for ((customer, customerAccounts) in accounts.withIndex()) {
        wealthByCustomer[customer] = customerAccounts.sum()
    }

    return wealthByCustomer.values.max()
}

fun numRescueBoats(people: IntArray, maxWeight: Int): Int {
    people.sort()

    var numBoats = 0

    var i = people.indices.first
    var j = people.indices.last

    while (i <= j) {
        numBoats++

        if (people[i] + people[j] <= maxWeight) {
            i++
        }

        j--
    }

    return numBoats
}

fun findRelativeRanks(scores: IntArray): Array<String> {
    val sortedScores = scores.sorted().reversed()
    val indicesByScore = mutableMapOf<Int?, Int>()

    for ((i, score) in sortedScores.withIndex()) {
        indicesByScore[score] = i
    }

    val placementByIndex = mapOf(
        0 to "Gold Medal",
        1 to "Silver Medal",
        2 to "Bronze Medal"
    )

    val answers = Array(scores.size) { "" }

    for ((i, score) in scores.withIndex()) {
        answers[i] = placementByIndex.getOrDefault(indicesByScore[score], (indicesByScore[score]!! + 1).toString())
    }

    return answers
}

fun maximumHappinessSum(happinesses: IntArray, k: Int): Long {
    val maxHeap = PriorityQueue<Int>(happinesses.size, Comparator.reverseOrder())
    maxHeap.addAll(happinesses.asIterable())

    var sum = 0L
    var turns = 0

    while (turns < k) {
        val current = maxHeap.remove()
        sum += maxOf(current - turns++, 0)
    }

    return sum
}

fun kthSmallestPrimeFraction(numbers: IntArray, k: Int): IntArray {
    val compareByQuotient: Comparator<Pair<Int, Int>> = compareBy { it.first.toDouble() / it.second }
    val minHeap = PriorityQueue(k, compareByQuotient)

    for (i in numbers.indices) {
        for (j in i + 1 until numbers.size) {
            minHeap.add(Pair(numbers[i], numbers[j]))
        }
    }

    var kthSmallest: Pair<Int, Int> = Pair(Int.MIN_VALUE, Int.MAX_VALUE)

    repeat(k) {
        kthSmallest = minHeap.remove()
    }

    return intArrayOf(kthSmallest.first, kthSmallest.second)
}

fun kidsWithCandies(candies: IntArray, extraCandies: Int): List<Boolean> {
    val currentMax = candies.max()
    return candies.map { it + extraCandies >= currentMax }
}

// https://leetcode.com/problems/minimum-cost-to-hire-k-workers/description/?envType=daily-question&envId=2024-05-11
fun mincostToHireWorkers(qualities: IntArray, minWages: IntArray, numWorkers: Int): Double {
    var totalCost = Double.MAX_VALUE
    var currentTotalQuality = 0.0

    val wageToQualityRatios = mutableListOf<Pair<Double, Int>>()

    for (i in qualities.indices) {
        wageToQualityRatios.add(minWages[i].toDouble() / qualities[i] to qualities[i])
    }

    wageToQualityRatios.sortBy { it.first }

    val maxHeap = PriorityQueue<Int>(Collections.reverseOrder())

    for (i in qualities.indices) {
        maxHeap.add(wageToQualityRatios[i].second)
        currentTotalQuality += wageToQualityRatios[i].second.toDouble()

        if (maxHeap.size > numWorkers) {
            currentTotalQuality -= maxHeap.remove().toDouble()
        }

        if (numWorkers == maxHeap.size) {
            totalCost = minOf(totalCost, currentTotalQuality * wageToQualityRatios[i].first)
        }
    }

    return totalCost
}

fun largestLocal(grid: Array<IntArray>): Array<IntArray> {
    val result = Array(grid.size - 2) { IntArray(grid.size - 2) }

    for (i in 0 until grid.size - 2) {
        for (j in 0 until grid.size - 2) {
            result[i][j] = findMax(grid, i, j)
        }
    }

    return result
}

fun findMax(grid: Array<IntArray>, x: Int, y: Int): Int {
    var max = 0

    for (i in x until x + 3) {
        for (j in y until y + 3) {
            max = maxOf(max, grid[i][j])
        }
    }

    return max
}

fun matrixScore(grid: Array<IntArray>): Int {
    val numRows = grid.size
    val numColumns = grid[0].size

    var score = 0

    for (i in 0 until numRows) {
        if (grid[i][0] == 0) {
            for (j in 0 until numColumns) {
                grid[i][j] = 1 - grid[i][j]
            }
        }
    }

    for (j in 1 until numColumns) {
        var numZeroes = 0

        for (i in 0 until numRows) {
            if (grid[i][j] == 0) {
                numZeroes++
            }
        }

        if (numZeroes > numRows - numZeroes) {
            for (i in 0 until numRows) {
                grid[i][j] = 1 - grid[i][j]
            }
        }
    }

    for (i in 0 until numRows) {
        for (j in 0 until numColumns) {
            val columnScore = grid[i][j] shl numColumns - j - 1
            score += columnScore
        }
    }

    return score
}

fun subsets(nums: IntArray): List<List<Int>> {
    val numSubsets = 2.0.pow(nums.size.toDouble()).toInt()

    val subsets = mutableListOf<List<Int>>()

    for (i in 0 until numSubsets) {
        subsets.add(determineSubset(i, nums))
    }

    return subsets
}

fun determineSubset(i: Int, nums: IntArray): List<Int> {
    if (i == 0) return emptyList()

    val subset = mutableListOf<Int>()

    for ((index, number) in nums.withIndex()) {
        if (bitSet(i, index)) {
            subset.add(number)
        }
    }

    return subset
}

fun bitSet(number: Int, bit: Int) = number and (1 shl bit) != 0

fun beautifulSubsets(nums: IntArray, k: Int): Int {
    return countBeautifulSubsets(nums, k, 0, 0)
}

fun countBeautifulSubsets(nums: IntArray, difference: Int, index: Int, mask: Int): Int {
    if (index == nums.size) return if (mask > 0) 1 else 0

    var isBeautiful = true

    var j = 0

    while (j < index && isBeautiful) {
        isBeautiful = ((1 shl j) and mask) == 0 || abs(nums[j] - nums[index]) != difference
        j++
    }

    val skip = countBeautifulSubsets(nums, difference, index + 1, mask)
    var take = 0

    if (isBeautiful) {
        take = countBeautifulSubsets(nums, difference, index + 1, mask + (1 shl index))
    }

    return skip + take
}

fun specialArray(nums: IntArray): Int {
    nums.sort()
    val max = nums.last()

    for (value in 1..max) {
        var i = nums.binarySearch(value)
        val index = nums.indexOf(value)

        if (i < 0) {
            i = -(i + 1)
        }

        if (index != -1) {
            i = minOf(i, index)
        }

        if (nums.size - i == value) {
            return value
        }
    }

    return -1
}

fun equalSubstring(s: String, t: String, maxCost: Int): Int {
    val n = s.length

    var maxLength = 0
    var start = 0
    var currentCost = 0

    for (end in 0 until n) {
        currentCost += abs(s[end].code - t[end].code)

        while (currentCost > maxCost) {
            currentCost -= abs(s[start].code - t[start].code)
            start++
        }

        maxLength = maxOf(maxLength, end - start + 1)
    }

    return maxLength
}

fun countTriplets(a: IntArray): Int {
    val prefixes = IntArray(a.size + 1) { if (it == 0) 0 else a[it - 1] }
    val n = prefixes.size

    for (i in 1 until n) {
        prefixes[i] = prefixes[i] xor prefixes[i - 1]
    }

    var count = 0

    for (start in 0 until n) {
        for (end in start + 1 until n) {
            if (prefixes[start] == prefixes[end]) {
                count += end - start - 1
            }
        }
    }

    return count
}

fun singleNumber(nums: IntArray): IntArray {
    val countsByNumber = nums.asIterable().groupingBy { it }.eachCount()
    return countsByNumber.entries.filter { it.value == 1 }.map { it.key }.toIntArray()
}

fun isNStraightHand(hand: IntArray, groupSize: Int): Boolean {
    if (hand.size % groupSize != 0) return false

    val countsByCard = hand.asIterable().groupingBy { it }.eachCount()

    val sortedCountsByCard = TreeMap(countsByCard)

    while (sortedCountsByCard.isNotEmpty()) {
        val currentCard = sortedCountsByCard.keys.first()

        for (i in 0 until groupSize) {
            if (!sortedCountsByCard.containsKey(currentCard + i)) return false

            sortedCountsByCard[currentCard + i] = sortedCountsByCard[currentCard + i]!! - 1

            if (sortedCountsByCard[currentCard + i] == 0) {
                sortedCountsByCard.remove(currentCard + i)
            }
        }
    }

    return true
}

fun checkSubarraySum(nums: IntArray, k: Int): Boolean {
    var prefixMod = 0
    val modulosSeen = mutableMapOf<Int, Int>(
        0 to -1
    )

    for (i in nums.indices) {
        prefixMod = (prefixMod + nums[i]) % k

        if (modulosSeen.containsKey(prefixMod)) {
            if (i - modulosSeen[prefixMod]!! > 1) {
                return true
            }
        } else {
            modulosSeen[prefixMod] = i
        }
    }

    return false
}

fun subarraysDivByK(nums: IntArray, k: Int): Int {
    val moduloGroups = IntArray(k)
    moduloGroups[0] = 1

    var prefixMod = 0
    var numSubarrays = 0

    for (n in nums) {
        prefixMod = (prefixMod + n % k + k) % k

        numSubarrays += moduloGroups[prefixMod]
        moduloGroups[prefixMod]++
    }

    return numSubarrays
}

fun heightChecker(heights: IntArray): Int {
    val expected = heights.sorted()
    return heights.indices.count() { expected[it] != heights[it] }
}

fun relativeSortArray(arr1: IntArray, arr2: IntArray): IntArray {
    var (present, unaccountedFor) = arr1.partition { arr2.indexOf(it) != -1 }
    unaccountedFor = unaccountedFor.sorted()

    val result = present.sortedBy { arr2.indexOf(it) }.toMutableList()

    result.addAll(unaccountedFor)

    return result.toIntArray()
}

fun minMovesToSeat(seats: IntArray, students: IntArray): Int {
    seats.sort()
    students.sort()

    return seats.indices.sumOf { abs(seats[it] - students[it]) }
}

fun minIncrementForUnique(nums: IntArray): Int {
    nums.sort()

    val countsByValue = nums.asIterable().groupingBy { it }.eachCount().toMutableMap()

    if (countsByValue.values.count { it > 1 } == 0) {
        return 0
    }

    // Note: May need to be smarter about how/when we choose candidates to increment...
    val dupes = countsByValue.entries.filter { it.value > 1 }

    var moves = 0

    for (dupe in dupes) {
        val num = dupe.key
        val occurrences = dupe.value
        countsByValue[num] = 1

        for (occurrence in 2..occurrences) {
            var candidate = num
            var placed = false

            while (!placed) {
                candidate++
                moves++

                if (!countsByValue.containsKey(candidate)) {
                    countsByValue[candidate] = 1
                    placed = true
                }
            }
        }

    }

    return moves
}

private data class Project(val capital: Int, val profit: Int)

fun findMaximizedCapital(k: Int, w: Int, profits: IntArray, capital: IntArray): Int {
    val compareByCapitalRequirement: Comparator<Project> = compareBy<Project> { it.capital }

    var projects = mutableListOf<Project>()

    for (i in profits.indices) {
        projects.add(Project(capital[i], profits[i]))
    }

    projects = projects.sortedWith(compareByCapitalRequirement).toMutableList()

    var totalCapital = w

    val q = PriorityQueue<Int>(Collections.reverseOrder())

    var current = 0

    for (i in 0 until k) {
        while (current < projects.size && projects[current].capital <= totalCapital) {
            q.add(projects[current++].profit)
        }

        if (q.isEmpty()) {
            break
        }

        totalCapital += q.remove()
    }

    return totalCapital
}

fun minPatches(nums: IntArray, n: Int): Int {
    var patches = 0
    var i = 0

    var misses = 1L

    while (misses <= n) {
        if (i < nums.size && nums[i] <= misses) {
            misses += nums[i++]
        } else {
            misses += misses
            patches++
        }
    }

    return patches
}

private data class Assignment(val difficulty: Int, val profit: Int)

fun maxProfitAssignment(difficulties: IntArray, profits: IntArray, workers: IntArray): Int {
    val decreasingProfitability = compareBy<Assignment> { it.profit }.reversed()

    var assignments = mutableListOf<Assignment>()

    for (i in difficulties.indices) {
        assignments.add(Assignment(difficulties[i], profits[i]))
    }

    assignments = assignments.sortedWith(decreasingProfitability).toMutableList()

    var totalProfit = 0

    for (ability in workers) {
        try {
            val assignment = assignments.first { it.difficulty <= ability }
            totalProfit += assignment.profit
        } catch (ignore: Exception) {
        }
    }

    return totalProfit
}

fun minDays(bloomDay: IntArray, numBouquets: Int, flowersPerBouquet: Int): Int {
    if (bloomDay.size < numBouquets * flowersPerBouquet) return -1

    var start = 0
    var end = 0

    for (day in bloomDay) {
        end = maxOf(day, end)
    }

    var minimumDays = -1

    while (start <= end) {
        val mid = (start + end) / 2

        if (getNumBouquets(bloomDay, mid, flowersPerBouquet) >= numBouquets) {
            minimumDays = mid
            end = mid - 1
        } else {
            start = mid + 1
        }
    }

    return minimumDays
}

private fun getNumBouquets(bloomDay: IntArray, mid: Int, flowerPerBouquet: Int): Int {
    var numBouquets = 0
    var count = 0

    for (i in bloomDay.indices) {
        if (bloomDay[i] <= mid) {
            count++
        } else {
            count = 0
        }

        if (count == flowerPerBouquet) {
            numBouquets++
            count = 0
        }
    }

    return numBouquets
}

fun maxDistance(basketPositions: IntArray, numBalls: Int): Int {
    var maxDistance = 0
    basketPositions.sort()
    val n = basketPositions.size

    var low = 1
    var high = ceil(basketPositions[n - 1] / (numBalls - 1.0)).toInt()

    while (low <= high) {
        val mid = low + (high - low) / 2

        if (canPlaceBalls(mid, basketPositions, numBalls)) {
            maxDistance = mid
            low = mid + 1
        } else {
            high = mid - 1
        }
    }

    return maxDistance
}

fun canPlaceBalls(x: Int, basketPositions: IntArray, numBalls: Int): Boolean {
    var previousBallPosition = basketPositions[0]
    var ballsPlaced = 1

    var i = 1

    while (i < basketPositions.size && ballsPlaced < numBalls) {
        val currentPosition = basketPositions[i]

        if (currentPosition - previousBallPosition >= x) {
            ballsPlaced++
            previousBallPosition = currentPosition
        }

        i++
    }

    return ballsPlaced == numBalls
}

fun maxSatisfied(customers: IntArray, grumpy: IntArray, minutes: Int): Int {
    val n = customers.size
    var unrealizedCustomers = 0

    for (i in 0..<minutes) {
        unrealizedCustomers += customers[i] * grumpy[i]
    }

    var maxUnrealizedCustomers = unrealizedCustomers

    for (i in minutes..<n) {
        unrealizedCustomers += customers[i] * grumpy[i]
        unrealizedCustomers -= customers[i - minutes] * grumpy[i - minutes]

        maxUnrealizedCustomers = maxOf(maxUnrealizedCustomers, unrealizedCustomers)
    }

    var totalPotentialCustomers = maxUnrealizedCustomers

    for (i in customers.indices) {
        totalPotentialCustomers += customers[i] * (1 - grumpy[i])
    }

    return totalPotentialCustomers
}

fun numberOfSubarrays(nums: IntArray, k: Int): Int {
    var currentSum = 0
    var subarrays = 0

    val prefixSums = mutableMapOf(currentSum to 1)

    for (i in nums.indices) {
        currentSum += nums[i] % 2

        if (prefixSums.containsKey(currentSum - k)) {
            subarrays += prefixSums[currentSum - k]!!
        }

        prefixSums[currentSum] = prefixSums.getOrDefault(currentSum, 0) + 1
    }

    return subarrays
}

fun longestSubarray(nums: IntArray, limit: Int): Int {
    var start = 0

    val localMaxima = ArrayDeque<Int>()
    val localMinima = ArrayDeque<Int>()

    var numSubarrays = 0

    for (end in nums.indices) {
        if (localMinima.isEmpty() || nums[end] < localMinima.peek()) {
            localMinima.push(nums[end])
        }

        if (localMaxima.isEmpty() || nums[end] > localMaxima.peek()) {
            localMaxima.push(nums[end])
        }

        var localMax = localMaxima.peek()
        var localMin = localMinima.peek()

        while (abs(localMax - localMin) > limit) {
            val candidate = nums[start++]

            if (localMaxima.peek() == candidate) {
                localMaxima.pop()
            }

            if (localMinima.peek() == candidate) {
                localMinima.pop()
            }

            localMax = if (localMaxima.isNotEmpty()) localMaxima.peek() else nums[end]
            localMin = if (localMinima.isNotEmpty()) localMinima.peek() else nums[end]
        }

        numSubarrays++
    }

    return numSubarrays
}

fun longestSubarrayB(nums: IntArray, limit: Int): Int {
    val maxHeap = PriorityQueue { a: IntArray, b: IntArray -> b[0] - a[0] }
    val minHeap: PriorityQueue<IntArray> = PriorityQueue<IntArray>(
        Comparator.comparingInt { a -> a[0] }
    )

    var left = 0
    var maxLength = 0

    for (right in nums.indices) {
        maxHeap.offer(intArrayOf(nums[right], right))
        minHeap.offer(intArrayOf(nums[right], right))

        while (maxHeap.peek()[0] - minHeap.peek()!![0] > limit) {
            left = (min(maxHeap.peek()[1].toDouble(), minHeap.peek()!![1].toDouble()) + 1).toInt()

            while (maxHeap.peek()[1] < left) {
                maxHeap.poll()
            }
            while (minHeap.peek()!![1] < left) {
                minHeap.poll()
            }
        }

        maxLength = max(maxLength.toDouble(), (right - left + 1).toDouble()).toInt()
    }

    return maxLength
}

fun minKBitFlips(nums: IntArray, k: Int): Int {
    val n = nums.size
    val flipQueue = ArrayDeque<Int>()

    var flipped = 0
    var result = 0

    for (i in nums.indices) {
        if (i >= k) {
            flipped = flipped xor flipQueue.poll()!!
        }

        if (flipped == nums[i]) {
            if (i + k > n) {
                return -1
            }

            flipQueue.offer(1)
            flipped = flipped xor 1
            result += 1
        } else {
            flipQueue.offer(0)
        }
    }

    return result
}

fun intersect(nums1: IntArray, nums2: IntArray): IntArray {
    val countsByValue1 = nums1.asIterable().groupingBy { it }.eachCount()
    val countsByValue2 = nums2.asIterable().groupingBy { it }.eachCount()

    val sharedValues = countsByValue1.keys.intersect(countsByValue2.keys)

    val rawIntersection = mutableListOf<Int>()

    for (value in sharedValues) {
        val occurrences = minOf(countsByValue1[value]!!, countsByValue2[value]!!)

        for (i in 0 until occurrences) {
            rawIntersection.add(value)
        }
    }

    return rawIntersection.toIntArray()
}

fun minDifference(nums: IntArray): Int {
    val n = nums.size

    if (n <= 4) return 0

    nums.sort()

    var minDifference = Int.MAX_VALUE

    var left = 0
    var right = n - 4

    while (left < 4) {
        minDifference = minOf(minDifference, nums[right] - nums[left])
        left++
        right++
    }

    return minDifference
}

fun chalkReplacer(chalk: IntArray, k: Int): Int {
    var chalkAvailable = k
    var chalkUsedPerRound = 0

    for (c in chalk) {
        chalkUsedPerRound += c

        if (chalkUsedPerRound > k) {
            break
        }
    }

    chalkAvailable %= chalkUsedPerRound

    for (i in chalk.indices) {
        if (chalkAvailable < chalk[i]) {
            return i
        }

        chalkAvailable -= chalk[i]
    }

    return Int.MIN_VALUE
}

data class Friend(val arrival: Int, val departure: Int, val id: Int)

fun smallestChair(times: Array<IntArray>, targetFriend: Int): Int {
    val friends = times.mapIndexed { index, it -> Friend(it[0], it[1], index) }
    val friendsById = friends.associateBy { it.id }
    val target = friendsById[targetFriend]!!

    var friendsArrivingBeforeTarget = friends.filter { it.arrival < target.arrival }
    var friendsDepartingBeforeTargetArrives = friends.filter { it.departure <= target.arrival }

    friendsArrivingBeforeTarget = friendsArrivingBeforeTarget.sortedBy { it.arrival }
    friendsDepartingBeforeTargetArrives = friendsDepartingBeforeTargetArrives.sortedBy { it.departure }

    val seats = mutableSetOf<Int>()
    val emptySeats = mutableSetOf<Int>()
    val seatAssignments = mutableMapOf<Int, Int>()

    var currentSeat = 0

    for (friend in friendsArrivingBeforeTarget) {
        if (seats.isEmpty()) {
            seatAssignments[friend.id] = currentSeat
            seats.add(currentSeat++)
        } else if (emptySeats.isNotEmpty()) {
            val seat = emptySeats.min()
            emptySeats.remove(seat)
            seatAssignments[friend.id] = seat
        } else {
            seatAssignments[friend.id] = currentSeat
            seats.add(currentSeat++)
        }

        val departingFriends =
            friendsDepartingBeforeTargetArrives.filter { it.departure <= friend.arrival }

        val freshlyEmptySeats =
            departingFriends.filter { seatAssignments.containsKey(it.id) }.map { seatAssignments.remove(it.id)!! }
                .toMutableSet()

        if (freshlyEmptySeats.isNotEmpty()) {
            emptySeats.addAll(freshlyEmptySeats)
        }
    }

    val occupiedSeats = seatAssignments.values.toSet()
    seats.addAll(emptySeats)
    seats.removeAll(occupiedSeats)

    if (seats.isNotEmpty()) {
        return seats.min()
    }

    return occupiedSeats.max() + 1
}

fun maxKelements(nums: IntArray, k: Int): Long {
    val maxHeap = PriorityQueue<Int>(Collections.reverseOrder())

    for (n in nums) {
        maxHeap.add(n)
    }

    var k = k

    var score = 0L

    while (k-- > 0) {
        val max = maxHeap.remove()!!
        score += max
        maxHeap.add(ceil(max / 3.0).toInt())
    }

    return score
}

// https://leetcode.com/problems/find-the-highest-altitude/editorial/?envType=study-plan-v2&envId=leetcode-75
fun largestAltitude(gains: IntArray): Int {
    val altitudes = mutableListOf(0)

    for (gain in gains) {
        altitudes.add(altitudes.last() + gain)
    }

    return altitudes.max()
}

fun minimumMountainRemovals(nums: IntArray): Int {
    val N = nums.size

    val lisLength = IntArray(N)
    val ldsLength = IntArray(N)

    Arrays.fill(lisLength, 1)
    Arrays.fill(ldsLength, 1)

    for (i in 0 until N) {
        for (j in i - 1 downTo 0) {
            if (nums[i] > nums[j]) {
                lisLength[i] = max(lisLength[i].toDouble(), (lisLength[j] + 1).toDouble()).toInt()
            }
        }
    }

    for (i in N - 1 downTo 0) {
        for (j in i + 1 until N) {
            if (nums[i] > nums[j]) {
                ldsLength[i] = max(ldsLength[i].toDouble(), (ldsLength[j] + 1).toDouble()).toInt()
            }
        }
    }

    var minRemovals = Int.MAX_VALUE
    for (i in 0 until N) {
        if (lisLength[i] > 1 && ldsLength[i] > 1) {
            minRemovals = min(
                minRemovals.toDouble(),
                (N - lisLength[i] - ldsLength[i] + 1).toDouble()
            ).toInt()
        }
    }

    return minRemovals
}

fun longestOnes(nums: IntArray, k: Int): Int {
    var k = k
    var left = 0
    var right = 0
    while (right < nums.size) {
        if (nums[right] == 0) {
            k--
        }
        if (k < 0) {
            k += 1 - nums[left]
            left++
        }
        right++
    }
    return right - left
}

fun longestSubarray(nums: IntArray): Int {
    var zeroes = 0
    var longestWindow = 0

    var start = 0

    for (end in 0..nums.lastIndex) {
        zeroes += if (nums[end] == 0) 1 else 0

        while (zeroes > 1) {
            zeroes -= if (nums[start++] == 0) 1 else 0
        }

        longestWindow = maxOf(longestWindow, end - start)
    }

    return longestWindow
}

fun pivotIndex(nums: IntArray): Int {
    val leftSums = IntArray(nums.size)
    val rightSums = IntArray(nums.size)
    leftSums[0] = 0
    rightSums[rightSums.size - 1] = 0

    for ((i, n) in nums.withIndex()) {
        for (j in leftSums.indices) {
            if (i < j) {
                leftSums[j] += n
            } else if (i > j) {
                rightSums[j] += n
            }
        }
    }

    return leftSums.indices.firstOrNull { leftSums[it] == rightSums[it] } ?: -1
}

fun findDifference(nums: IntArray, nums2: IntArray): List<List<Int>> {
    val left = nums.toSet()
    val right = nums2.toSet()
    val leftDifferences = mutableListOf<Int>()
    val rightDifferences = mutableListOf<Int>()

    for (num in left) {
        if (!right.contains(num)) {
            leftDifferences.add(num)
        }
    }

    for (num in right) {
        if (!left.contains(num)) {
            rightDifferences.add(num)
        }
    }



    return listOf(leftDifferences, rightDifferences)
}

data class Item(val index: Int, val price: Int, val beauty: Int)

fun maximumBeauty(rawItems: Array<IntArray>, queries: IntArray): IntArray {
    val answers = IntArray(queries.size) { 0 }

    val beautyComparator: Comparator<Item> = compareBy<Item> { it.beauty }.reversed()

    var items = mutableListOf<Item>()

    for ((i, rawItem) in rawItems.withIndex()) {
        items.add(Item(i, rawItem[0], rawItem[1]))
    }

    items = items.sortedWith(beautyComparator).toMutableList()

    for ((i, query) in queries.withIndex()) {
        val needle = items.firstOrNull { it.price <= query }

        if (needle != null) {
            answers[i] = needle.beauty
        }
    }

    return answers
}

fun decrypt(code: IntArray, k: Int): IntArray {
    return when {
        k == 0 -> IntArray(code.size) { 0 }
        k < 0 -> {
            IntArray(code.size) {
                val start = it - 1
                val end = it + k
                val range = if (start > end) end..start else start..end
                val values = mutableListOf<Int>()
                for (i in range) {
                    values.add(
                        if (i >= 0) {
                            code[i]
                        } else {
                            code[code.size + i]
                        }
                    )
                }
                values.sum()
            }
        }

        else -> {
            IntArray(code.size) {
                val start = it + 1
                val end = it + k
                val range = start..end
                val values = mutableListOf<Int>()
                for (i in range) {
                    values.add(
                        if (i < code.size) {
                            code[i]
                        } else {
                            code[i % code.size]
                        }
                    )
                }
                values.sum()
            }
        }
    }
}

data class Coordinate(val row: Int, val col: Int)

fun countUnguarded(rows: Int, columns: Int, guards: Array<IntArray>, walls: Array<IntArray>): Int {
    val guardCoordinates = guards.map { Coordinate(it[0], it[1]) }.toSet()
    val wallCoordinate = walls.map { Coordinate(it[0], it[1]) }.toSet()

    val guardedCells = mutableSetOf<Coordinate>()

    for (guard in guardCoordinates) {
        // North
        for (row in guard.row - 1 downTo 0) {
            val c = Coordinate(row, guard.col)

            val occupied = wallCoordinate.contains(c) || guardCoordinates.contains(c)

            if (occupied) {
                break
            }

            guardedCells.add(c)
        }

        // East
        for (col in guard.col + 1 until columns) {
            val c = Coordinate(guard.row, col)

            val occupied = wallCoordinate.contains(c) || guardCoordinates.contains(c)

            if (occupied) {
                break
            }

            guardedCells.add(c)
        }

        // South
        for (row in guard.row + 1 until rows) {
            val c = Coordinate(row, guard.col)

            val occupied = wallCoordinate.contains(c) || guardCoordinates.contains(c)

            if (occupied) {
                break
            }

            guardedCells.add(c)
        }

        // West
        for (col in guard.col - 1 downTo 0) {
            val c = Coordinate(guard.row, col)

            val occupied = wallCoordinate.contains(c) || guardCoordinates.contains(c)

            if (occupied) {
                break
            }

            guardedCells.add(c)
        }
    }

    var unguardedCells = 0

    for (row in 0 until rows) {
        for (col in 0 until columns) {
            val c = Coordinate(row, col)

            val occupied = wallCoordinate.contains(c) || guardCoordinates.contains(c)

            if (!occupied && !guardedCells.contains(c)) {
                unguardedCells++
            }
        }
    }

    return unguardedCells
}


fun maxEqualRowsAfterFlips(matrix: Array<IntArray>): Int {
    val patternFrequency = mutableMapOf<String, Int>()

    for (row in matrix) {
        val patternSb = StringBuilder()

        for (col in row) {
            if (col == row[0]) patternSb.append('T') else patternSb.append('F')
        }

        val pattern = patternSb.toString()
        val count = patternFrequency.getOrPut(pattern) { 0 }
        patternFrequency[pattern] = count + 1
    }

    return patternFrequency.entries.maxOfOrNull { it.value } ?: 0
}

fun checkIfExist(arr: IntArray): Boolean {
    val indicesByValue = mutableMapOf<Int, MutableSet<Int>>()

    for ((i, value) in arr.withIndex()) {
        val indices = indicesByValue.getOrPut(value) { mutableSetOf(i) }
        indices.add(i)
        indicesByValue[value] = indices
    }

    for ((i, value) in arr.withIndex()) {
        val double = value*2

        val indices = indicesByValue[double]

        if (indices != null) {
            indices.remove(i)

            if (indices.isNotEmpty()) {
                return true
            }

            indices.add(i)
        }
    }

    return false
}

fun isArraySpecial(nums: IntArray): Boolean {
    for (i in 0..<nums.lastIndex) {
        if (nums[i] % 2 == 0) {
            if (nums[i+1] % 2 == 0) {
                return false
            }
        } else {
            if (nums[i+1] % 2 != 0) {
                return false
            }
        }
    }

    return true
}