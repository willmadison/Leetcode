package com.willmadison.leetcode

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
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
                    intArrayOf(-1, 2, -3, 3),
                    3,
                ),
                Arguments.of(
                    intArrayOf(-1, 10, 6, 7, -7, 1),
                    7,
                ),
                Arguments.of(
                    intArrayOf(-10, 8, 6, 7, -2, -3),
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

        @JvmStatic
        fun operationProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf("5", "2", "C", "D", "+"),
                    30,
                ),
                Arguments.of(
                    arrayOf("5", "-2", "4", "C", "D", "9", "+", "+"),
                    27,
                ),
                Arguments.of(
                    arrayOf("1", "C"),
                    0,
                ),
            )
        }

        @JvmStatic
        fun powerOfTwoProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    1,
                    true,
                ),
                Arguments.of(
                    16,
                    true,
                ),
                Arguments.of(
                    3,
                    false,
                ),
            )
        }

        @JvmStatic
        fun cheapestPriceProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    4,
                    arrayOf(
                        intArrayOf(0, 1, 100),
                        intArrayOf(1, 2, 100),
                        intArrayOf(2, 0, 100),
                        intArrayOf(1, 3, 600),
                        intArrayOf(2, 3, 200),
                    ),
                    0,
                    3,
                    1,
                    700,
                ),
                Arguments.of(
                    3,
                    arrayOf(
                        intArrayOf(0, 1, 100),
                        intArrayOf(1, 2, 100),
                        intArrayOf(0, 2, 500),
                    ),
                    0,
                    2,
                    1,
                    200,
                ),
                Arguments.of(
                    3,
                    arrayOf(
                        intArrayOf(0, 1, 100),
                        intArrayOf(1, 2, 100),
                        intArrayOf(0, 2, 500),
                    ),
                    0,
                    2,
                    0,
                    500,
                ),
            )
        }

        @JvmStatic
        fun confusingNumberProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    6,
                    true,
                ),
                Arguments.of(
                    89,
                    true,
                ),
                Arguments.of(
                    11,
                    false,
                ),
                Arguments.of(
                    916,
                    false,
                ),
            )
        }

        @JvmStatic
        fun pivotIntegerProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    8,
                    6,
                ),
                Arguments.of(
                    1,
                    1,
                ),
                Arguments.of(
                    4,
                    -1,
                ),
            )
        }

        @JvmStatic
        fun minOperationProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(2, 1, 3, 4),
                    1,
                    2,
                ),
                Arguments.of(
                    intArrayOf(2, 0, 2, 0),
                    0,
                    0,
                ),
            )
        }

        @JvmStatic
        fun tribonacciProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    4,
                    4,
                ),
                Arguments.of(
                    25,
                    1389537,
                ),
            )
        }

        @JvmStatic
        fun binaryReductionProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "1101",
                    6,
                ),
                Arguments.of(
                    "10",
                    1,
                ),
                Arguments.of(
                    "1",
                    0,
                ),
            )
        }

        @JvmStatic
        fun judgeSquareSumProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    3,
                    false,
                ),
                Arguments.of(
                    5,
                    true,
                ),
            )
        }

        @JvmStatic
        fun consecutiveOddProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(2, 6, 4, 1),
                    false,
                ),
                Arguments.of(
                    intArrayOf(1, 2, 34, 3, 4, 5, 7, 23, 12),
                    true,
                ),
            )
        }

        @JvmStatic
        fun waterBottleProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    9,
                    3,
                    13,
                ),
                Arguments.of(
                    15,
                    4,
                    19,
                ),
            )
        }

        @JvmStatic
        fun winnerProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    5,
                    2,
                    3,
                ),
                Arguments.of(
                    6,
                    5,
                    1,
                ),
            )
        }

        @JvmStatic
        fun luckyProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "zbax",
                    2,
                    8,
                ),
                Arguments.of(
                    "iiii",
                    1,
                    36,
                ),
                Arguments.of(
                    "leetcode",
                    2,
                    6,
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

    @ParameterizedTest(name = "calPoints({0}) = {1}")
    @MethodSource("operationProvider")
    fun calPoints(operations: Array<String>, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.calPoints(operations)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "isPowerOfTwo({0}) = {1}")
    @MethodSource("powerOfTwoProvider")
    fun isPowerOfTwo(n: Int, expected: Boolean) {
        val actual = com.willmadison.leetcode.extensions.isPowerOfTwo(n)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "findCheapestPrice({0}, {1}, {2}, {3}, {4}) = {5}")
    @MethodSource("cheapestPriceProvider")
    fun findCheapestPrice(n: Int, flights: Array<IntArray>, src: Int, dst: Int, k: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.findCheapestPrice(n, flights, src, dst, k)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "confusingNumber({0}) = {1}")
    @MethodSource("confusingNumberProvider")
    fun confusingNumber(n: Int, expected: Boolean) {
        val actual = com.willmadison.leetcode.extensions.confusingNumber(n)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "pivotInteger({0}) = {1}")
    @MethodSource("pivotIntegerProvider")
    fun pivotInteger(n: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.pivotInteger(n)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "minXOROperations({0}, {1}) = {2}")
    @MethodSource("minOperationProvider")
    fun minXOROperations(nums: IntArray, k: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.minXOROperations(nums, k)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "tribonacci({0}) = {1}")
    @MethodSource("tribonacciProvider")
    fun tribonacci(n: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.tribonacci(n)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "numSteps({0}) = {1}")
    @MethodSource("binaryReductionProvider")
    fun numSteps(s: String, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.numSteps(s)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "judgeSquareSum({0}) = {1}")
    @MethodSource("judgeSquareSumProvider")
    fun judgeSquareSum(c: Int, expected: Boolean) {
        val actual = com.willmadison.leetcode.extensions.judgeSquareSum(c)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "threeConsecutiveOdds({0}) = {1}")
    @MethodSource("consecutiveOddProvider")
    fun threeConsecutiveOdds(nums: IntArray, expected: Boolean) {
        val actual = com.willmadison.leetcode.extensions.threeConsecutiveOdds(nums)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "numWaterBottles({0}, {1}) = {2}")
    @MethodSource("waterBottleProvider")
    fun numWaterBottles(numBottles: Int, numExchange: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.numWaterBottles(numBottles, numExchange)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "findTheWinner({0}, {1}) = {2}")
    @MethodSource("winnerProvider")
    fun findTheWinner(n: Int, k: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.findTheWinner(n, k)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "getLucky({0}, {1}) = {2}")
    @MethodSource("luckyProvider")
    fun getLucky(s: String, k: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.getLucky(s, k)
        assertThat(actual).isEqualTo(expected)
    }


}