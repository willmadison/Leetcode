package com.willmadison.leetcode

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class ArraysTests {

    companion object {

        @JvmStatic
        fun testedDeviceProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(intArrayOf(1, 1, 2, 1, 3), 3),
                Arguments.of(intArrayOf(0, 1, 2), 2),
            )
        }

        @JvmStatic
        fun variablesProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(intArrayOf(2, 3, 3, 10), intArrayOf(3, 3, 3, 1), intArrayOf(6, 1, 1, 4)),
                    2,
                    listOf<Int>(0, 2)
                ),
                Arguments.of(arrayOf(intArrayOf(39, 3, 1000, 1000)), 17, listOf<Int>()),
            )
        }

        @JvmStatic
        fun subarraysProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(intArrayOf(1, 3, 2, 3, 3), 2, 6L),
                Arguments.of(intArrayOf(1, 4, 2, 1), 3, 0L),
            )
        }

        @JvmStatic
        fun elementProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(intArrayOf(3, 2, 3), 3),
                Arguments.of(intArrayOf(2, 2, 1, 1, 1, 2, 2), 2),
            )
        }

        @JvmStatic
        fun addendProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(intArrayOf(2, 7, 11, 15), 9, intArrayOf(0, 1)),
                Arguments.of(intArrayOf(3, 2, 4), 6, intArrayOf(1, 2)),
            )
        }

        @JvmStatic
        fun duplicatesProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(intArrayOf(1, 2, 3, 1), true),
                Arguments.of(intArrayOf(1, 2, 3, 4), false),
            )
        }

        @JvmStatic
        fun subArrayProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(intArrayOf(-2, 1, -3, 4, -1, 2, 1, -5, 4), 6),
                Arguments.of(intArrayOf(1), 1),
                Arguments.of(intArrayOf(5, 4, -1, 7, 8), 23),
            )
        }

        @JvmStatic
        fun intervalProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(intArrayOf(1, 3), intArrayOf(6, 9)),
                    intArrayOf(2, 5),
                    arrayOf(intArrayOf(1, 5), intArrayOf(6, 9))
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(1, 2),
                        intArrayOf(3, 5),
                        intArrayOf(6, 7),
                        intArrayOf(8, 10),
                        intArrayOf(12, 16)
                    ), intArrayOf(4, 8), arrayOf(intArrayOf(1, 2), intArrayOf(3, 10), intArrayOf(12, 16))
                ),
            )
        }

        @JvmStatic
        fun matrixProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        intArrayOf(0, 0, 0),
                        intArrayOf(0, 1, 0),
                        intArrayOf(0, 0, 0),
                    ),
                    arrayOf(
                        intArrayOf(0, 0, 0),
                        intArrayOf(0, 1, 0),
                        intArrayOf(0, 0, 0),
                    )
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(0, 0, 0),
                        intArrayOf(0, 1, 0),
                        intArrayOf(1, 1, 1),
                    ),
                    arrayOf(
                        intArrayOf(0, 0, 0),
                        intArrayOf(0, 1, 0),
                        intArrayOf(1, 2, 1),
                    )
                ),
            )
        }

        @JvmStatic
        fun pointProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        intArrayOf(1, 3),
                        intArrayOf(-2, 2),
                    ),
                    1,
                    arrayOf(
                        intArrayOf(-2, 2),
                    )
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(3, 3),
                        intArrayOf(5, -1),
                        intArrayOf(-2, 4),
                    ),
                    2,
                    arrayOf(
                        intArrayOf(3, 3),
                        intArrayOf(-2, 4),
                    )
                ),
            )
        }

        @JvmStatic
        fun subsequenceProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(10, 9, 2, 5, 3, 7, 101, 18),
                    4,
                ),
                Arguments.of(
                    intArrayOf(0, 1, 0, 3, 2, 3),
                    4,
                ),
                Arguments.of(
                    intArrayOf(7, 7, 7, 7, 7, 7, 7),
                    1,
                ),
            )
        }

        @JvmStatic
        fun jobProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 2, 3, 3),
                    intArrayOf(3, 4, 5, 6),
                    intArrayOf(50, 10, 40, 70),
                    120
                ),
                Arguments.of(
                    intArrayOf(1, 2, 3, 4, 6),
                    intArrayOf(3, 5, 10, 6, 9),
                    intArrayOf(20, 20, 100, 70, 60),
                    150
                ),
                Arguments.of(
                    intArrayOf(1, 1, 1),
                    intArrayOf(2, 3, 4),
                    intArrayOf(5, 6, 4),
                    6
                ),
            )
        }

        @JvmStatic
        fun jumpProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(2, 3, 1, 1, 4),
                    true,
                ),
                Arguments.of(
                    intArrayOf(3, 2, 1, 0, 4),
                    false,
                ),
            )
        }

        @JvmStatic
        fun duplicateProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 1, 2),
                    2,
                    intArrayOf(1, 2),
                ),
                Arguments.of(
                    intArrayOf(0, 0, 1, 1, 1, 2, 2, 3, 3, 4),
                    5,
                    intArrayOf(0, 1, 2, 3, 4),
                ),
            )
        }

        @JvmStatic
        fun childrenProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 2, 3),
                    intArrayOf(1, 1),
                    1,
                ),
                Arguments.of(
                    intArrayOf(1, 2, 3),
                    intArrayOf(1, 2),
                    2,
                ),
            )
        }

        @JvmStatic
        fun valueProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 3, 4, 1, 2, 3, 1),
                    listOf(
                        listOf(1, 2, 3, 4),
                        listOf(1, 3),
                        listOf(1),
                    )
                ),
                Arguments.of(
                    intArrayOf(1, 2, 3, 4),
                    listOf(
                        listOf(1, 2, 3, 4),
                    )
                ),
            )
        }

        @JvmStatic
        fun beamProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        "011001",
                        "000000",
                        "010100",
                        "001000"
                    ),
                    8,
                ),
                Arguments.of(
                    arrayOf(
                        "000",
                        "111",
                        "000"
                    ),
                    0,
                ),
            )
        }

        @JvmStatic
        fun operandProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(2, 3, 3, 2, 2, 4, 2, 3, 4),
                    4,
                ),
                Arguments.of(
                    intArrayOf(2, 1, 2, 2, 3, 3),
                    -1,
                ),
                Arguments.of(
                    intArrayOf(3, 3),
                    1,
                ),
                Arguments.of(
                    intArrayOf(14, 12, 14, 14, 12, 14, 14, 12, 12, 12, 12, 14, 14, 12, 14, 14, 14, 12, 12),
                    7,
                ),
            )
        }

        @JvmStatic
        fun studentProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        intArrayOf(10, 6, 9, 1),
                        intArrayOf(7, 5, 11, 2),
                        intArrayOf(4, 8, 3, 15),
                    ),
                    2,
                    arrayOf(
                        intArrayOf(7, 5, 11, 2),
                        intArrayOf(10, 6, 9, 1),
                        intArrayOf(4, 8, 3, 15),
                    ),
                ),
            )
        }

        @JvmStatic
        fun coinProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 2, 5),
                    11,
                    3,
                ),
                Arguments.of(
                    intArrayOf(2),
                    3,
                    -1,
                ),
                Arguments.of(
                    intArrayOf(1),
                    0,
                    0,
                ),
                Arguments.of(
                    intArrayOf(186, 419, 83, 408),
                    6249,
                    20,
                ),
            )
        }

        @JvmStatic
        fun binaryMatrixProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        intArrayOf(0, 1, 1),
                        intArrayOf(1, 0, 1),
                        intArrayOf(0, 0, 1),
                    ),
                    arrayOf(
                        intArrayOf(0, 0, 4),
                        intArrayOf(0, 0, 4),
                        intArrayOf(-2, -2, 2),
                    ),
                ),
            )
        }

        @JvmStatic
        fun elementsProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 2, 2, 3, 1, 4),
                    4,
                ),
                Arguments.of(
                    intArrayOf(1, 2, 3, 4, 5),
                    5,
                ),
            )
        }

        @JvmStatic
        fun winnerProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        intArrayOf(1, 3),
                        intArrayOf(2, 3),
                        intArrayOf(3, 6),
                        intArrayOf(5, 6),
                        intArrayOf(5, 7),
                        intArrayOf(4, 5),
                        intArrayOf(4, 8),
                        intArrayOf(4, 9),
                        intArrayOf(10, 4),
                        intArrayOf(10, 9),
                    ),
                    listOf(
                        listOf(1, 2, 10),
                        listOf(4, 5, 7, 8),
                    ),
                ),
            )
        }

        @JvmStatic
        fun zeroProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(0, 1, 0, 3, 12),
                    intArrayOf(1, 3, 12, 0, 0),
                ),
                Arguments.of(
                    intArrayOf(0),
                    intArrayOf(0),
                ),
            )
        }

        @JvmStatic
        fun occurrenceProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 2, 2, 1, 1, 3),
                    true,
                ),
                Arguments.of(
                    intArrayOf(1, 2),
                    false,
                ),
                Arguments.of(
                    intArrayOf(-3, 0, 1, -3, 1, 1, 1, -3, 10, 0),
                    true,
                ),
            )
        }

        @JvmStatic
        fun minProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(3, 4, 5, 1, 2),
                    1,
                ),
                Arguments.of(
                    intArrayOf(4, 5, 6, 7, 0, 1, 2),
                    0,
                ),
                Arguments.of(
                    intArrayOf(11, 13, 15, 17),
                    11,
                ),
            )
        }

        @JvmStatic
        fun plusOneProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 2, 3),
                    intArrayOf(1, 2, 4),
                ),
                Arguments.of(
                    intArrayOf(4, 3, 2, 1),
                    intArrayOf(4, 3, 2, 2),
                ),
                Arguments.of(
                    intArrayOf(9),
                    intArrayOf(1, 0),
                ),
            )
        }

        @JvmStatic
        fun arrayProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(-1, -2, -3, -4, 3, 2, 1),
                    1,
                ),
                Arguments.of(
                    intArrayOf(1, 5, 0, 2, -3),
                    0,
                ),
                Arguments.of(
                    intArrayOf(-1, 1, -1, 1, -1),
                    -1,
                ),
                Arguments.of(
                    intArrayOf(
                        41,
                        65,
                        14,
                        80,
                        20,
                        10,
                        55,
                        58,
                        24,
                        56,
                        28,
                        86,
                        96,
                        10,
                        3,
                        84,
                        4,
                        41,
                        13,
                        32,
                        42,
                        43,
                        83,
                        78,
                        82,
                        70,
                        15,
                        -41
                    ),
                    -1,
                ),
            )
        }

        @JvmStatic
        fun monotonicProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 2, 2, 3),
                    true,
                ),
                Arguments.of(
                    intArrayOf(6, 5, 4, 4),
                    true,
                ),
                Arguments.of(
                    intArrayOf(1, 3, 2),
                    false,
                ),
            )
        }

        @JvmStatic
        fun fallingPathProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        intArrayOf(2, 1, 3),
                        intArrayOf(6, 5, 4),
                        intArrayOf(7, 8, 9),
                    ),
                    13,
                ),
            )
        }

        @JvmStatic
        fun fallingPathProvider2(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        intArrayOf(1, 2, 3),
                        intArrayOf(4, 5, 6),
                        intArrayOf(7, 8, 9),
                    ),
                    13,
                ),
            )
        }

        @JvmStatic
        fun errorNumProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 2, 2, 4),
                    intArrayOf(2, 3),
                ),
                Arguments.of(
                    intArrayOf(1, 1),
                    intArrayOf(1, 2),
                ),
            )
        }
    }

    @ParameterizedTest(name = "countSubarrays({0}, {1}) = {2}")
    @MethodSource("subarraysProvider")
    fun countSubarrays(nums: IntArray, k: Int, expected: Long) {
        val actual = com.willmadison.leetcode.extensions.countSubarrays(nums, k)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "majorityElement({0}, {1})")
    @MethodSource("elementProvider")
    fun majorityElement(nums: IntArray, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.majorityElement(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "twoSum({0}, {1}) = {2}")
    @MethodSource("addendProvider")
    fun twoSum(nums: IntArray, target: Int, expected: IntArray) {
        val actual = com.willmadison.leetcode.extensions.twoSum(nums, target)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "containsDuplicate({0}) = {1}")
    @MethodSource("duplicatesProvider")
    fun containsDuplicate(nums: IntArray, expected: Boolean) {
        val actual = com.willmadison.leetcode.extensions.containsDuplicate(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "maxSubArray({0}) = {1}")
    @MethodSource("subArrayProvider")
    fun maxSubArray(nums: IntArray, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.maxSubArray(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "insert({0}, {1}) = {2}")
    @MethodSource("intervalProvider")
    fun insert(intervals: Array<IntArray>, newInterval: IntArray, expected: Array<IntArray>) {
        val actual = com.willmadison.leetcode.extensions.insert(intervals, newInterval)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "updateMatrix({0}) = {1}")
    @MethodSource("matrixProvider")
    fun updateMatrix(matrix: Array<IntArray>, expected: Array<IntArray>) {
        val actual = com.willmadison.leetcode.extensions.updateMatrix(matrix)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "kClosest({0}, {1}) = {2}")
    @MethodSource("pointProvider")
    fun kClosest(points: Array<IntArray>, k: Int, expected: Array<IntArray>) {
        val actual = com.willmadison.leetcode.extensions.kClosest(points, k)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "lengthOfLIS({0}) = {1}")
    @MethodSource("subsequenceProvider")
    fun lengthOfLIS(nums: IntArray, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.lengthOfLIS(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "jobScheduling({0}, {1}, {2}) = {3}")
    @MethodSource("jobProvider")
    @Disabled
    fun jobScheduling(startTimes: IntArray, endTimes: IntArray, profits: IntArray, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.jobScheduling(startTimes, endTimes, profits)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "canJump({0}) = {1}")
    @MethodSource("jumpProvider")
    fun canJump(nums: IntArray, expected: Boolean) {
        val actual = com.willmadison.leetcode.extensions.canJump(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "removeDuplicates({0}) = ({1}, {2})")
    @MethodSource("duplicateProvider")
    fun removeDuplicates(nums: IntArray, expectedLength: Int, expectedArray: IntArray) {
        val actual = com.willmadison.leetcode.extensions.removeDuplicates(nums)
        assertEquals(expectedLength, actual)
        assertArrayEquals(expectedArray, nums.take(expectedLength).toIntArray())
    }

    @ParameterizedTest(name = "findContentChildren({0}, {1}) = {2}")
    @MethodSource("childrenProvider")
    fun findContentChildren(greeds: IntArray, cookies: IntArray, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.findContentChildren(greeds, cookies)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "findMatrix({0}) = {1}")
    @MethodSource("valueProvider")
    fun findMatrix(nums: IntArray, expected: List<List<Int>>) {
        val actual = com.willmadison.leetcode.extensions.findMatrix(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "numberOfBeams({0}) = {1}")
    @MethodSource("beamProvider")
    fun numberOfBeams(bank: Array<String>, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.numberOfBeams(bank)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "minOperations({0}) = {1}")
    @MethodSource("operandProvider")
    fun minOperations(nums: IntArray, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.minOperations(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "sortTheStudents({0}, {1}) = {2}")
    @MethodSource("studentProvider")
    fun sortTheStudents(scores: Array<IntArray>, k: Int, expected: Array<IntArray>) {
        val actual = com.willmadison.leetcode.extensions.sortTheStudents(scores, k)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "coinChange({0}, {1}) = {2}")
    @MethodSource("coinProvider")
    fun coinChange(coins: IntArray, changeDue: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.coinChange(coins, changeDue)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "onesMinusZeros({0}) = {1}")
    @MethodSource("binaryMatrixProvider")
    fun onesMinusZeros(grid: Array<IntArray>, expected: Array<IntArray>) {
        val actual = com.willmadison.leetcode.extensions.onesMinusZeros(grid)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "maxFrequencyElements({0}) = {1}")
    @MethodSource("elementsProvider")
    fun maxFrequencyElements(nums: IntArray, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.maxFrequencyElements(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "findWinners({0}) = {1}")
    @MethodSource("winnerProvider")
    fun findWinners(matches: Array<IntArray>, expected: List<List<Int>>) {
        val actual = com.willmadison.leetcode.extensions.findWinners(matches)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "moveZeroes({0}) = {1}")
    @MethodSource("zeroProvider")
    fun moveZeroes(nums: IntArray, expected: IntArray) {
        com.willmadison.leetcode.extensions.moveZeroes(nums)
        assertArrayEquals(expected, nums)
    }

    @ParameterizedTest(name = "uniqueOccurrences({0}) = {1}")
    @MethodSource("occurrenceProvider")
    fun uniqueOccurrences(nums: IntArray, expected: Boolean) {
        val actual = com.willmadison.leetcode.extensions.uniqueOccurrences(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "findMin({0}) = {1}")
    @MethodSource("minProvider")
    fun findMin(nums: IntArray, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.findMin(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "plusOne({0}) = {1}")
    @MethodSource("plusOneProvider")
    fun plusOne(digits: IntArray, expected: IntArray) {
        val actual = com.willmadison.leetcode.extensions.plusOne(digits)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "arraySign({0}) = {1}")
    @MethodSource("arrayProvider")
    fun arraySign(nums: IntArray, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.arraySign(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "isMonotonic({0}) = {1}")
    @MethodSource("monotonicProvider")
    fun isMonotonic(nums: IntArray, expected: Boolean) {
        val actual = com.willmadison.leetcode.extensions.isMonotonic(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "minFallingPathSum({0}) = {1}")
    @MethodSource("fallingPathProvider")
    fun minFallingPathSum(matrix: Array<IntArray>, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.minFallingPathSum(matrix)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "minFallingPathSum2({0}) = {1}")
    @MethodSource("fallingPathProvider2")
    fun minFallingPathSum2(grid: Array<IntArray>, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.minFallingPathSum2(grid)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "findErrorNums({0}) = {1}")
    @MethodSource("errorNumProvider")
    fun findErrorNums(nums: IntArray, expected: IntArray) {
        val actual = com.willmadison.leetcode.extensions.findErrorNums(nums)
        assertArrayEquals(expected, actual)
    }

}