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
                    Arguments.of(arrayOf(intArrayOf(2, 3, 3, 10), intArrayOf(3, 3, 3, 1), intArrayOf(6, 1, 1, 4)), 2, listOf<Int>(0, 2)),
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
                    Arguments.of(intArrayOf(3,2,4), 6, intArrayOf(1, 2)),
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

    @AfterEach
    fun tearDown() {
        System.clearProperty("FIRST_BAD_VERSION")
    }
}