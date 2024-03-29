package com.willmadison.leetcode.extensions

import com.willmadison.leetcode.Location
import com.willmadison.leetcode.Point
import java.util.*
import kotlin.math.abs

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
        if (nums[abs(i) -1] < 0) {
            dupes.add(abs(i))
            continue
        }

        nums[abs(i) -1] *= -1
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
            nums[0] = -1* abs(nums[0])
        } else {
            nums[v] = -1* abs(nums[v])
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

    return n+1
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

