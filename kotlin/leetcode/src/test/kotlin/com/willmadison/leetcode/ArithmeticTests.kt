package com.willmadison.leetcode

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class ArithmeticTests {

    companion object {
        @JvmStatic
        fun maxKProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(-1,2,-3,3),
                    3,
                ),
                Arguments.of(
                    intArrayOf(-1,10,6,7,-7,1),
                    7,
                ),
                Arguments.of(
                    intArrayOf(-10,8,6,7,-2,-3),
                    -1,
                ),
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
        fun romanProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "III",
                    3,
                ),
                Arguments.of(
                    "LVIII",
                    58,
                ),
                Arguments.of(
                    "MCMXCIV",
                    1994,
                ),
            )
        }
    }

    @ParameterizedTest(name = "findMaxK({0}) = {1}")
    @MethodSource("maxKProvider")
    fun findMaxK(nums: IntArray, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.findMaxK(nums)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "addBinary({0}, {1}) = {2}")
    @MethodSource("binaryAddendProvider")
    fun addBinary(a: String, b: String, expected: String) {
        val actual = com.willmadison.leetcode.extensions.addBinary(a, b)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "evalRPN({0}) = {1}")
    @MethodSource("tokenProvider")
    fun evalRPN(tokens: Array<String>, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.evalRPN(tokens)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "isPalindrome({0}) = {1}")
    @MethodSource("palindromeNumProvider")
    fun isPalindrome(x: Int, expected: Boolean) {
        val actual = com.willmadison.leetcode.extensions.isPalindrome(x)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "threeSum({0}) = {1}")
    @MethodSource("threeSumProvider")
    fun threeSum(nums: IntArray, expected: List<List<Int>>) {
        val actual = com.willmadison.leetcode.extensions.threeSum(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "productExceptSelf({0}) = {1}")
    @MethodSource("factorProvider")
    fun productExceptSelf(nums: IntArray, expected: IntArray) {
        val actual = com.willmadison.leetcode.extensions.productExceptSelf(nums)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "titleToNumber({0}) = {1}")
    @MethodSource("titleProvider")
    fun titleToNumber(columnTitle: String, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.titleToNumber(columnTitle)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "sum({0}, {1}) = {2}")
    @MethodSource("sumProvider")
    fun sum(num1: Int, num2: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.sum(num1, num2)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "romanToInt({0}) = {1}")
    @MethodSource("romanProvider")
    fun romanToInt(s: String, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.romanToInt(s)
        assertEquals(expected, actual)
    }


}