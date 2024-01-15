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

        @JvmStatic
        fun tokenProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf("2", "1", "+", "3", "*"),
                    9,
                ),
                Arguments.of(
                    arrayOf("4", "13", "5", "/", "+"),
                    6,
                ),
                Arguments.of(
                    arrayOf("10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"),
                    22,
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
        fun palindromeNumProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    121,
                    true,
                ),
                Arguments.of(
                    -121,
                    false,
                ),
            )
        }

        @JvmStatic
        fun prefixProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        "flower",
                        "flow",
                        "flight"
                    ),
                    "fl",
                ),
                Arguments.of(
                    arrayOf(
                        "dog",
                        "racecar",
                        "car"
                    ),
                    "",
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
        fun keyboardProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "abcdefghijklmnopqrstuvwxyz",
                    "cba",
                    4,
                ),
                Arguments.of(
                    "pqrstuvwxyzabcdefghijklmno",
                    "leetcode",
                    73,
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
        fun parenthesisProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "()",
                    true,
                ),
                Arguments.of(
                    "()[]{}",
                    true,
                ),
                Arguments.of(
                    "(]",
                    false,
                ),
                Arguments.of(
                    "]",
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
        fun threeSumProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(-1, 0, 1, 2, -1, -4),
                    listOf(
                        listOf(-1, -1, 2),
                        listOf(-1, 0, 1),
                    ),
                ),
                Arguments.of(
                    intArrayOf(0, 1, 1),
                    emptyList<List<Int>>(),
                ),
                Arguments.of(
                    intArrayOf(0, 0, 0),
                    listOf(
                        listOf(0, 0, 0),
                    ),
                ),
            )
        }

        @JvmStatic
        fun palindromicSubstringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "babad",
                    "aba",
                ),
                Arguments.of(
                    "",
                    "",
                ),
            )
        }

        @JvmStatic
        fun anagramProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "anagram",
                    "nagaram",
                    true,
                ),
                Arguments.of(
                    "rat",
                    "car",
                    false,
                ),
            )
        }

        @JvmStatic
        fun factorProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 2, 3, 4),
                    intArrayOf(24, 12, 8, 6),
                ),
                Arguments.of(
                    intArrayOf(-1, 1, 0, -3, 3),
                    intArrayOf(0, 0, 9, 0, 0),
                ),
            )
        }

        @JvmStatic
        fun titleProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "A",
                    1,
                ),
                Arguments.of(
                    "AB",
                    28,
                ),
                Arguments.of(
                    "ZY",
                    701,
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
        fun windowProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "ADOBECODEBANC",
                    "ABC",
                    "BANC",
                ),
                Arguments.of(
                    "a",
                    "a",
                    "a",
                ),
                Arguments.of(
                    "a",
                    "aa",
                    "",
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
        fun halveProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "book",
                    true,
                ),
                Arguments.of(
                    "textbook",
                    false,
                ),
            )
        }

        @JvmStatic
        fun stepProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "bab",
                    "aba",
                    1,
                ),
                Arguments.of(
                    "leetcode",
                    "practice",
                    5,
                ),
                Arguments.of(
                    "anagram",
                    "mangaar",
                    0,
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
        fun beautyProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "isawsquirrelnearmysquirrelhouseohmy",
                    "my",
                    "squirrel",
                    15,
                    listOf(16, 33),
                ),
                Arguments.of(
                    "abcd",
                    "a",
                    "a",
                    4,
                    listOf(0),
                ),
                Arguments.of(
                    "eueuau",
                    "u",
                    "e",
                    3,
                    listOf(1, 3, 5),
                ),
            )
        }

        @JvmStatic
        fun sumProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    9,
                    1,
                    10,
                ),
                Arguments.of(
                    7,
                    2,
                    9,
                ),
            )
        }

        @JvmStatic
        fun winnerProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        intArrayOf(1,3),
                        intArrayOf(2,3),
                        intArrayOf(3,6),
                        intArrayOf(5,6),
                        intArrayOf(5,7),
                        intArrayOf(4,5),
                        intArrayOf(4,8),
                        intArrayOf(4,9),
                        intArrayOf(10,4),
                        intArrayOf(10,9),
                    ),
                    listOf(
                        listOf(1,2,10),
                        listOf(4,5,7,8),
                    ),
                ),
            )
        }
    }

    @BeforeEach
    fun setUp() {
        solution = Solution()
    }

    @Test
    @Disabled
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
    @Disabled
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

    @ParameterizedTest(name = "evalRPN({0}) = {1}")
    @MethodSource("tokenProvider")
    fun evalRPN(tokens: Array<String>, expected: Int) {
        val actual = solution.evalRPN(tokens)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "lengthOfLIS({0}) = {1}")
    @MethodSource("subsequenceProvider")
    fun lengthOfLIS(nums: IntArray, expected: Int) {
        val actual = solution.lengthOfLIS(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "isPalindrome({0}) = {1}")
    @MethodSource("palindromeNumProvider")
    fun isPalindrome(x: Int, expected: Boolean) {
        val actual = solution.isPalindrome(x)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "longestCommonPrefix({0}) = {1}")
    @MethodSource("prefixProvider")
    fun longestCommonPrefix(words: Array<String>, expected: String) {
        val actual = solution.longestCommonPrefix(words)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "jobScheduling({0}, {1}, {2}) = {3}")
    @MethodSource("jobProvider")
    @Disabled
    fun jobScheduling(startTimes: IntArray, endTimes: IntArray, profits: IntArray, expected: Int) {
        val actual = solution.jobScheduling(startTimes, endTimes, profits)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "calculateTime({0}, {1}) = {2}")
    @MethodSource("keyboardProvider")
    fun calculateTime(keyboard: String, word: String, expected: Int) {
        val actual = solution.calculateTime(keyboard, word)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "canJump({0}) = {1}")
    @MethodSource("jumpProvider")
    fun canJump(nums: IntArray, expected: Boolean) {
        val actual = solution.canJump(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "isValid({0}) = {1}")
    @MethodSource("parenthesisProvider")
    fun isValid(given: String, expected: Boolean) {
        val actual = solution.isValid(given)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "removeDuplicates({0}) = ({1}, {2})")
    @MethodSource("duplicateProvider")
    fun removeDuplicates(nums: IntArray, expectedLength: Int, expectedArray: IntArray) {
        val actual = solution.removeDuplicates(nums)
        assertEquals(expectedLength, actual)
        assertArrayEquals(expectedArray, nums.take(expectedLength).toIntArray())
    }

    @ParameterizedTest(name = "findContentChildren({0}, {1}) = {2}")
    @MethodSource("childrenProvider")
    fun findContentChildren(greeds: IntArray, cookies: IntArray, expected: Int) {
        val actual = solution.findContentChildren(greeds, cookies)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "findMatrix({0}) = {1}")
    @MethodSource("valueProvider")
    fun findMatrix(nums: IntArray, expected: List<List<Int>>) {
        val actual = solution.findMatrix(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "numberOfBeams({0}) = {1}")
    @MethodSource("beamProvider")
    fun numberOfBeams(bank: Array<String>, expected: Int) {
        val actual = solution.numberOfBeams(bank)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "minOperations({0}) = {1}")
    @MethodSource("operandProvider")
    fun minOperations(nums: IntArray, expected: Int) {
        val actual = solution.minOperations(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "threeSum({0}) = {1}")
    @MethodSource("threeSumProvider")
    fun threeSum(nums: IntArray, expected: List<List<Int>>) {
        val actual = solution.threeSum(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "longestPalindromicSubstring({0}) = {1}")
    @MethodSource("palindromicSubstringProvider")
    fun longestPalindromicSubstring(s: String, expected: String) {
        val actual = solution.longestPalindromicSubstring(s)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "isAnagram({0}, {1}) = {2}")
    @MethodSource("anagramProvider")
    fun isAnagram(s: String, t: String, expected: Boolean) {
        val actual = solution.isAnagram(s, t)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "productExceptSelf({0}) = {1}")
    @MethodSource("factorProvider")
    fun productExceptSelf(nums: IntArray, expected: IntArray) {
        val actual = solution.productExceptSelf(nums)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "titleToNumber({0}) = {1}")
    @MethodSource("titleProvider")
    fun titleToNumber(columnTitle: String, expected: Int) {
        val actual = solution.titleToNumber(columnTitle)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "sortTheStudents({0}, {1}) = {2}")
    @MethodSource("studentProvider")
    fun sortTheStudents(scores: Array<IntArray>, k: Int, expected: Array<IntArray>) {
        val actual = solution.sortTheStudents(scores, k)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "coinChange({0}, {1}) = {2}")
    @MethodSource("coinProvider")
    fun coinChange(coins: IntArray, changeDue: Int, expected: Int) {
        val actual = solution.coinChange(coins, changeDue)
        assertEquals(expected, actual)
    }

    @Test
    fun amountOfTime() {
        var root = TreeNode(
            1,
            TreeNode(5, right = TreeNode(4, TreeNode(9), TreeNode(2))), TreeNode(3, TreeNode(10), TreeNode(6))
        )

        var actual = solution.amountOfTime(root, 3)

        assertEquals(4, actual)

        root = TreeNode(
            1,
            left = TreeNode(2, left = TreeNode(3, left = TreeNode(4, left = TreeNode(5))))
        )

        actual = solution.amountOfTime(root, 3)

        assertEquals(2, actual)
    }

    @Test
    fun maxAncestorDiff() {
        var root = TreeNode(
            8,
            left = TreeNode(3, left = TreeNode(1), right = TreeNode(6, left = TreeNode(4), right = TreeNode(7))),
            right = TreeNode(10, right = TreeNode(14, left = TreeNode(13)))
        )

        var actual = solution.maxAncestorDiff(root)

        assertEquals(7, actual)

        root = TreeNode(
            1,
            right = TreeNode(2, right = TreeNode(0, left = TreeNode(3)))
        )

        actual = solution.maxAncestorDiff(root)

        assertEquals(3, actual)
    }

    @ParameterizedTest(name = "minWindow({0}, {1}) = {2}")
    @MethodSource("windowProvider")
    fun minWindow(source: String, target: String, expected: String) {
        val actual = solution.minWindow(source, target)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "onesMinusZeros({0}) = {1}")
    @MethodSource("binaryMatrixProvider")
    fun onesMinusZeros(grid: Array<IntArray>, expected: Array<IntArray>) {
        val actual = solution.onesMinusZeros(grid)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "havlesAreAlike({0}) = {1}")
    @MethodSource("halveProvider")
    fun halvesAreAlike(s: String, expected: Boolean) {
        val actual = solution.halvesAreAlike(s)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "minSteps({0}, {1}) = {2}")
    @MethodSource("stepProvider")
    fun minSteps(s: String, t: String, expected: Int) {
        val actual = solution.minSteps(s, t)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "maxFrequencyElements({0}) = {1}")
    @MethodSource("elementsProvider")
    fun maxFrequencyElements(nums: IntArray, expected: Int) {
        val actual = solution.maxFrequencyElements(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "beautifulIndices({0}, {1}, {2}, {3}) = {4}")
    @MethodSource("beautyProvider")
    fun beautifulIndices(s: String, a: String, b: String, k: Int, expected: List<Int>) {
        val actual = solution.beautifulIndices(s, a, b, k)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "sum({0}, {1}) = {2}")
    @MethodSource("sumProvider")
    fun sum(num1: Int, num2: Int, expected: Int) {
        val actual = solution.sum(num1, num2)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "findWinners({0}) = {1}")
    @MethodSource("winnerProvider")
    fun findWinners(matches: Array<IntArray>, expected: List<List<Int>>) {
        val actual = solution.findWinners(matches)
        assertEquals(expected, actual)
    }

    @AfterEach
    fun tearDown() {
        System.clearProperty("FIRST_BAD_VERSION")
    }
}