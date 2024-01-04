package com.willmadison.leetcode

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class SolutionTests {

    private lateinit var solution: Solution

    companion object {
        @JvmStatic
        fun magazineSourceProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("a", "b", false),
                Arguments.of("aa", "ab", false),
                Arguments.of("aa", "aab", true),
            )
        }

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
        fun stairProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(2, 2),
                Arguments.of(3, 3),
            )
        }

        @JvmStatic
        fun palindromeProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("abccccdd", 7),
                Arguments.of("a", 1),
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
        fun elementProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(intArrayOf(3, 2, 3), 3),
                Arguments.of(intArrayOf(2, 2, 1, 1, 1, 2, 2), 2),
            )
        }

        @JvmStatic
        fun binaryAddendProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("11", "1", "100"),
                Arguments.of("1010", "1011", "10101"),
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
        fun substringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "abcabcbb",
                    3,
                ),
                Arguments.of(
                    "bbbbb",
                    1,
                ),
                Arguments.of(
                    "pwwkew",
                    3,
                ),
            )
        }
    }

    @BeforeEach
    fun setUp() {
        solution = Solution()
    }

    @Test
    @Disabled("disabled for now")
    fun firstBadVersion() {
        System.setProperty("FIRST_BAD_VERSION", "4")

        val expected = 4

        val actual = solution.firstBadVersion(10)

        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "canConstruct({0}, {1}) = {2}")
    @MethodSource("magazineSourceProvider")
    fun canConstruct(note: String, magazine: String, expected: Boolean) {
        val actual = solution.canConstruct(note, magazine)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "countTestedDevices({0}) = {1}")
    @MethodSource("testedDeviceProvider")
    fun countTestedDevices(percentages: IntArray, expected: Int) {
        val actual = solution.countTestedDevices(percentages)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "getGoodIndices({0}, {1}) = {2}")
    @MethodSource("variablesProvider")
    fun getGoodIndicies(variables: Array<IntArray>, target: Int, expected: List<Int>) {
        val actual = solution.getGoodIndices(variables, target)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "countSubarrays({0}, {1}) = {2}")
    @MethodSource("subarraysProvider")
    @Disabled("disabled for now")
    fun countSubarrays(nums: IntArray, k: Int, expected: Long) {
        val actual = solution.countSubarrays(nums, k)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "climbStairs({0}) = {1}")
    @MethodSource("stairProvider")
    fun climbStairs(numStairs: Int, expected: Int) {
        val actual = solution.climbStairs(numStairs)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "longestPalindrome({0}) = {1}")
    @MethodSource("palindromeProvider")
    fun longestPalindrome(value: String, expected: Int) {
        val actual = solution.longestPalindrome(value)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "twoSum({0}, {1}) = {2}")
    @MethodSource("addendProvider")
    fun twoSum(nums: IntArray, target: Int, expected: IntArray) {
        val actual = solution.twoSum(nums, target)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "majorityElement({0}, {1})")
    @MethodSource("elementProvider")
    fun majorityElement(nums: IntArray, expected: Int) {
        val actual = solution.majorityElement(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "addBinary({0}, {1}) = {2}")
    @MethodSource("binaryAddendProvider")
    fun addBinary(a: String, b: String, expected: String) {
        val actual = solution.addBinary(a, b)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "containsDuplicate({0}) = {1}")
    @MethodSource("duplicatesProvider")
    fun containsDuplicate(nums: IntArray, expected: Boolean) {
        val actual = solution.containsDuplicate(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "maxSubArray({0}) = {1}")
    @MethodSource("subArrayProvider")
    fun maxSubArray(nums: IntArray, expected: Int) {
        val actual = solution.maxSubArray(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "insert({0}, {1}) = {2}")
    @MethodSource("intervalProvider")
    fun insert(intervals: Array<IntArray>, newInterval: IntArray, expected: Array<IntArray>) {
        val actual = solution.insert(intervals, newInterval)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "updateMatrix({0}) = {1}")
    @MethodSource("matrixProvider")
    fun updateMatrix(matrix: Array<IntArray>, expected: Array<IntArray>) {
        val actual = solution.updateMatrix(matrix)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "kClosest({0}, {1}) = {2}")
    @MethodSource("pointProvider")
    fun kClosest(points: Array<IntArray>, k: Int, expected: Array<IntArray>) {
        val actual = solution.kClosest(points, k)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "lengthOfLongestSubstring({0}) = {1}")
    @MethodSource("substringProvider")
    fun lengthOfLongestSubstring(s: String, expected: Int) {
        val actual = solution.lengthOfLongestSubstring(s)
        assertEquals(expected, actual)
    }

    @AfterEach
    fun tearDown() {
        System.clearProperty("FIRST_BAD_VERSION")
    }
}