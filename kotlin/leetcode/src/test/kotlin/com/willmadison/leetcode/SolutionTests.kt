package com.willmadison.leetcode

import com.willmadison.leetcode.extensions.*
import org.assertj.core.api.Assertions.assertThat
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
        fun differenceProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "abcd",
                    "abcde",
                    'e'
                ),
                Arguments.of(
                    "",
                    "y",
                    'y'
                ),
                Arguments.of(
                    "a",
                    "aa",
                    'a'
                ),
            )
        }

        @JvmStatic
        fun needleProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "sadbutsad",
                    "sad",
                    0,
                ),
                Arguments.of(
                    "leetcode",
                    "leeto",
                    -1,
                ),
            )
        }

        @JvmStatic
        fun mergeProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "abc",
                    "pqr",
                    "apbqcr",
                ),
                Arguments.of(
                    "ab",
                    "pqrs",
                    "apbqrs",
                ),
                Arguments.of(
                    "abcd",
                    "pq",
                    "apbqcd",
                ),
            )
        }

        @JvmStatic
        fun repeatedSubstringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "abab",
                    true,
                ),
                Arguments.of(
                    "aba",
                    false,
                ),
                Arguments.of(
                    "abcabcabcabc",
                    true,
                ),
                Arguments.of(
                    "bb",
                    true,
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
        fun vowelStringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        "aba",
                        "bcb",
                        "ece",
                        "aa",
                        "e"
                    ),
                    arrayOf(
                        intArrayOf(0, 2),
                        intArrayOf(1, 4),
                        intArrayOf(1, 1),
                    ),
                    intArrayOf(2, 3, 0),
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
        fun progressionProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(3, 5, 1),
                    true,
                ),
                Arguments.of(
                    intArrayOf(1, 2, 4),
                    false,
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

        @JvmStatic
        fun insertProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 3, 5, 6),
                    5,
                    2,
                ),
                Arguments.of(
                    intArrayOf(1, 3, 5, 6),
                    2,
                    1,
                ),
                Arguments.of(
                    intArrayOf(1, 3, 5, 6),
                    7,
                    4,
                ),
                Arguments.of(
                    intArrayOf(1, 3, 5, 6),
                    0,
                    0,
                ),
                Arguments.of(
                    intArrayOf(1, 3, 5),
                    4,
                    2,
                ),
            )
        }

        @JvmStatic
        fun partitionProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 15, 7, 9, 2, 5, 10),
                    3,
                    84,
                ),
                Arguments.of(
                    intArrayOf(1, 4, 1, 5, 7, 3, 6, 1, 9, 9, 3),
                    4,
                    83,
                ),
                Arguments.of(
                    intArrayOf(1),
                    1,
                    1,
                ),
            )
        }

        @JvmStatic
        fun boundaryProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(2, 3, -5),
                    1,
                ),
                Arguments.of(
                    intArrayOf(3, 2, -3, -4),
                    0,
                ),
            )
        }

        @JvmStatic
        fun wordProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "Hello World",
                    5,
                ),
                Arguments.of(
                    "   fly me   to   the moon  ",
                    4,
                ),
                Arguments.of(
                    "luffy is still joyboy",
                    6,
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
        fun moveProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "UD",
                    true,
                ),
                Arguments.of(
                    "LL",
                    false,
                ),
            )
        }

        @JvmStatic
        fun ticTacToeProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        intArrayOf(0, 0),
                        intArrayOf(2, 0),
                        intArrayOf(1, 1),
                        intArrayOf(2, 1),
                        intArrayOf(2, 2)
                    ),
                    "A"
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(0, 0),
                        intArrayOf(1, 1),
                        intArrayOf(0, 1),
                        intArrayOf(0, 2),
                        intArrayOf(1, 0),
                        intArrayOf(2, 0)
                    ),
                    "B"
                ),
            )
        }

        @JvmStatic
        fun wealthProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        intArrayOf(1, 2, 3),
                        intArrayOf(3, 2, 1),
                    ),
                    6
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(1, 5),
                        intArrayOf(7, 3),
                        intArrayOf(3, 5),
                    ),
                    10
                ),
            )
        }

        @JvmStatic
        fun uniqueCharProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "leetcode",
                    0,
                ),
                Arguments.of(
                    "loveleetcode",
                    2,
                ),
                Arguments.of(
                    "aabb",
                    -1,
                ),
            )
        }

        @JvmStatic
        fun anagramGroupProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf("eat", "tea", "tan", "ate", "nat", "bat"),
                    listOf(
                        listOf("bat"),
                        listOf("tan", "nat"),
                        listOf("eat", "tea", "ate"),
                    )
                ),
                Arguments.of(
                    arrayOf(""),
                    listOf(
                        listOf(""),
                    )
                ),
            )
        }

        @JvmStatic
        fun palindromeListProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf("abc", "car", "ada", "racecar", "cool"),
                    "ada",
                ),
                Arguments.of(
                    arrayOf("def", "ghi"),
                    "",
                ),
            )
        }

        @JvmStatic
        fun arrangeableArrayProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(3, 1, -2, -5, 2, -4),
                    intArrayOf(3, -2, 1, -5, 2, -4),
                ),
                Arguments.of(
                    intArrayOf(-1, 1),
                    intArrayOf(1, -1),
                ),
            )
        }

        @JvmStatic
        fun frequencySortProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "leetcode",
                    "eeeltcod",
                ),
                Arguments.of(
                    "tree",
                    "eetr",
                ),
                Arguments.of(
                    "aaaccc",
                    "aaaccc",
                ),
            )
        }

        @JvmStatic
        fun intArrayProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(5, 5, 4),
                    1,
                    1,
                ),
                Arguments.of(
                    intArrayOf(4, 3, 1, 1, 3, 3, 2),
                    3,
                    2,
                ),
            )
        }

        @JvmStatic
        fun buildingProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(4, 2, 7, 6, 9, 14, 12),
                    5,
                    1,
                    4,
                ),
                Arguments.of(
                    intArrayOf(4, 12, 2, 7, 3, 18, 20, 3, 19),
                    10,
                    2,
                    7,
                ),
                Arguments.of(
                    intArrayOf(14, 3, 19, 3),
                    17,
                    0,
                    3,
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
        fun missingNumberProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(0, 1, 3),
                    2,
                ),
                Arguments.of(
                    intArrayOf(0, 1),
                    2,
                ),
                Arguments.of(
                    intArrayOf(9, 6, 4, 2, 3, 5, 7, 0, 1),
                    8,
                ),
            )
        }

        @JvmStatic
        fun maxProductProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(3, 4, 5, 2),
                    12,
                ),
                Arguments.of(
                    intArrayOf(1, 5, 4, 5),
                    16,
                ),
                Arguments.of(
                    intArrayOf(3, 7),
                    12,
                ),
            )
        }

        @JvmStatic
        fun judgeProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    2,
                    arrayOf(
                        intArrayOf(1, 2),
                    ),
                    2,
                ),
                Arguments.of(
                    3,
                    arrayOf(
                        intArrayOf(1, 3),
                        intArrayOf(2, 3),
                    ),
                    3,
                ),
                Arguments.of(
                    3,
                    arrayOf(
                        intArrayOf(1, 3),
                        intArrayOf(2, 3),
                        intArrayOf(3, 1),
                    ),
                    -1,
                ),
                Arguments.of(
                    1,
                    emptyArray<IntArray>(),
                    1,
                ),
            )
        }

        @JvmStatic
        fun kthLargestProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(3, 2, 1, 5, 6, 4),
                    2,
                    5,
                ),
                Arguments.of(
                    intArrayOf(3, 2, 3, 1, 2, 4, 5, 5, 6),
                    4,
                    4,
                ),
            )
        }

        @JvmStatic
        fun topKFrequentProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 1, 1, 2, 2, 3),
                    2,
                    intArrayOf(1, 2),
                ),
                Arguments.of(
                    intArrayOf(1),
                    1,
                    intArrayOf(1),
                ),
            )
        }

        @JvmStatic
        fun wordSquareProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    listOf(
                        "abcd", "bnrt", "crmy", "dtye"
                    ),
                    true,
                ),
                Arguments.of(
                    listOf(
                        "ball", "area", "read", "lady"
                    ),
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
        fun peopleProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    6,
                    arrayOf(
                        intArrayOf(1, 2, 5),
                        intArrayOf(2, 3, 8),
                        intArrayOf(1, 5, 10),
                    ),
                    1,
                    listOf(0, 1, 2, 3, 5),
                ),
            )
        }

        @JvmStatic
        fun binaryNumberProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "010",
                    "001",
                ),
                Arguments.of(
                    "0101",
                    "1001",
                ),
            )
        }

        @JvmStatic
        fun islandProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        charArrayOf('1', '1', '1', '1', '0'),
                        charArrayOf('1', '1', '0', '1', '0'),
                        charArrayOf('1', '1', '0', '0', '0'),
                        charArrayOf('0', '0', '0', '0', '0'),
                    ),
                    1,
                ),
                Arguments.of(
                    arrayOf(
                        charArrayOf('1', '1', '0', '0', '0'),
                        charArrayOf('1', '1', '0', '0', '0'),
                        charArrayOf('0', '0', '1', '0', '0'),
                        charArrayOf('0', '0', '0', '1', '1'),
                    ),
                    3,
                ),
            )
        }

        @JvmStatic
        fun squareProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(-4, -1, 0, 3, 10),
                    intArrayOf(0, 1, 9, 16, 100),
                ),
                Arguments.of(
                    intArrayOf(-7, -3, 2, 3, 11),
                    intArrayOf(4, 9, 9, 49, 121),
                ),
            )
        }

        @JvmStatic
        fun buddyStringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "ab",
                    "ba",
                    true,
                ),
                Arguments.of(
                    "ab",
                    "ab",
                    false,
                ),
                Arguments.of(
                    "aa",
                    "aa",
                    true,
                ),
            )
        }

        @JvmStatic
        fun tokenBagProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(100),
                    50,
                    0,
                ),
                Arguments.of(
                    intArrayOf(200, 100),
                    150,
                    1,
                ),
                Arguments.of(
                    intArrayOf(100, 200, 300, 400),
                    200,
                    2,
                ),
            )
        }

        @JvmStatic
        fun minimumLengthStringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "ca",
                    2,
                ),
                Arguments.of(
                    "cabaabac",
                    0,
                ),
                Arguments.of(
                    "aabccabba",
                    3,
                ),
            )
        }

        @JvmStatic
        fun missingRangeProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(0,1,3,50,75),
                    0,
                    99,
                    listOf(
                        listOf(2,2),
                        listOf(4,49),
                        listOf(51,74),
                        listOf(76,99),
                    ),
                ),
                Arguments.of(
                    intArrayOf(-1),
                    -1,
                    -1,
                    emptyList<List<Int>>(),
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
        fun intersectionProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1,2,2,1),
                    intArrayOf(2,2),
                    intArrayOf(2),
                ),
                Arguments.of(
                    intArrayOf(4,9,5),
                    intArrayOf(9,4,9,8,4),
                    intArrayOf(4,9),
                ),
            )
        }

        @JvmStatic
        fun customStringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "cba",
                    "abcd",
                    "cbad",
                ),
                Arguments.of(
                    "bcafg",
                    "abcd",
                    "bcad",
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
        fun sumSubarrayProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1,0,1,0,1),
                    2,
                    4,
                ),
                Arguments.of(
                    intArrayOf(0,0,0,0,0),
                    0,
                    15,
                ),
            )
        }

        @JvmStatic
        fun duplicatesArrayProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(4,3,2,7,8,2,3,1),
                    listOf(2, 3),
                ),
                Arguments.of(
                    intArrayOf(1,1,2),
                    listOf(1),
                ),
                Arguments.of(
                    intArrayOf(1),
                    emptyList<Int>(),
                ),
            )
        }

        @JvmStatic
        fun missingPositiveProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1,2,0),
                    3,
                ),
                Arguments.of(
                    intArrayOf(3,4,-1,1),
                    2,
                ),
                Arguments.of(
                    intArrayOf(7,8,9,11,12),
                    1,
                ),
            )
        }

        @JvmStatic
        fun subarrayProductProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(10,5,2,6),
                    100,
                    8,
                ),
                Arguments.of(
                    intArrayOf(1,2,3),
                    0,
                    0,
                ),
            )
        }

        @JvmStatic
        fun subarrayLengthConstraintProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1,2,3,1,2,3,1,2),
                    2,
                    6,
                ),
                Arguments.of(
                    intArrayOf(1,2,1,2,1,2,1,2),
                    1,
                    2,
                ),
                Arguments.of(
                    intArrayOf(5,5,5,5,5,5,5),
                    4,
                    4,
                ),
            )
        }

        @JvmStatic
        fun isomorphicStringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "egg",
                    "add",
                    true,
                ),
                Arguments.of(
                    "foo",
                    "bar",
                    false,
                ),
                Arguments.of(
                    "paper",
                    "title",
                    true,
                ),
            )
        }

        @JvmStatic
        fun subarrayWithKDistinctProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1,2,1,2,3),
                    2,
                    7,
                ),
                Arguments.of(
                    intArrayOf(1,2,1,3,4),
                    3,
                    3,
                ),
            )
        }

        @JvmStatic
        fun constrainedSubarrayProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1,3,5,2,7,5),
                    1,
                    5,
                    2,
                ),
                Arguments.of(
                    intArrayOf(1,1,1,1),
                    1,
                    1,
                    10,
                ),
            )
        }

        @JvmStatic
        fun crosswordProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        charArrayOf('A', 'B', 'C', 'E'),
                        charArrayOf('S', 'F', 'C', 'S'),
                        charArrayOf('A', 'D', 'E', 'E'),
                    ),
                    "ABCCED",
                    true,
                ),
                Arguments.of(
                    arrayOf(
                        charArrayOf('A', 'B', 'C', 'E'),
                        charArrayOf('S', 'F', 'C', 'S'),
                        charArrayOf('A', 'D', 'E', 'E'),
                    ),
                    "SEE",
                    true,
                ),
                Arguments.of(
                    arrayOf(
                        charArrayOf('A', 'B', 'C', 'E'),
                        charArrayOf('S', 'F', 'C', 'S'),
                        charArrayOf('A', 'D', 'E', 'E'),
                    ),
                    "ABCB",
                    false,
                )
            )
        }

        @JvmStatic
        fun maxDepthProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "(1+(2*3)+((8)/4))+1",
                    3,
                ),
                Arguments.of(
                    "(1)+((2))+(((3)))",
                    3,
                ),
            )
        }

        @JvmStatic
        fun goodStringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "leEeetcode",
                    "leetcode",
                ),
                Arguments.of(
                    "abBAcC",
                    "",
                ),
            )
        }

        @JvmStatic
        fun removeToMakeValidProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "lee(t(c)o)de)",
                    "lee(t(c)o)de",
                ),
                Arguments.of(
                    "a)b(c)d",
                    "ab(c)d",
                ),
                Arguments.of(
                    "))((",
                    "",
                ),
            )
        }

        @JvmStatic
        fun wildcardValidStringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "()",
                    true,
                ),
                Arguments.of(
                    "(*)",
                    true,
                ),
                Arguments.of(
                    "(*))",
                    true,
                ),
            )
        }

        @JvmStatic
        fun studentSandwichProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1,1,0,0),
                    intArrayOf(0,1,0,1),
                    0,
                ),
                Arguments.of(
                    intArrayOf(1,1,1,0,0,1),
                    intArrayOf(1,0,0,0,1,1),
                    3,
                ),
            )
        }

        @JvmStatic
        fun ticketConstraintProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(2,3,2),
                    2,
                    6,
                ),
                Arguments.of(
                    intArrayOf(5,1,1,1),
                    0,
                    8,
                ),
            )
        }

        @JvmStatic
        fun deckProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(17,13,11,2,3,5,7),
                    intArrayOf(2,13,3,11,5,17,7),
                ),
                Arguments.of(
                    intArrayOf(1,1000),
                    intArrayOf(1,1000),
                ),
            )
        }

        @JvmStatic
        fun kdigitsProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "1432219",
                    3,
                    "1219",
                ),
                Arguments.of(
                    "10200",
                    1,
                    "200",
                ),
                Arguments.of(
                    "10",
                    2,
                    "0",
                ),
            )
        }

        @JvmStatic
        fun waterTrapProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(0,1,0,2,1,0,1,3,2,1,2,1),
                    6,
                ),
                Arguments.of(
                    intArrayOf(4,2,0,3,2,5),
                    9,
                ),
            )
        }

        @JvmStatic
        fun rectangleProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        charArrayOf('0'),
                    ),
                    0,
                ),
                Arguments.of(
                    arrayOf(
                        charArrayOf('1'),
                    ),
                    1,
                ),
                Arguments.of(
                    arrayOf(
                        charArrayOf('1', '0', '1', '0', '0'),
                        charArrayOf('1', '0', '1', '1', '1'),
                        charArrayOf('1', '1', '1', '1', '1'),
                        charArrayOf('1', '0', '0', '1', '0'),
                    ),
                    6,
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
        val actual = canConstruct(note, magazine)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "countTestedDevices({0}) = {1}")
    @MethodSource("testedDeviceProvider")
    fun countTestedDevices(percentages: IntArray, expected: Int) {
        val actual = countTestedDevices(percentages)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "getGoodIndices({0}, {1}) = {2}")
    @MethodSource("variablesProvider")
    fun getGoodIndicies(variables: Array<IntArray>, target: Int, expected: List<Int>) {
        val actual = getGoodIndices(variables, target)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "countSubarrays({0}, {1}) = {2}")
    @MethodSource("subarraysProvider")
    fun countSubarrays(nums: IntArray, k: Int, expected: Long) {
        val actual = countSubarrays(nums, k)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "climbStairs({0}) = {1}")
    @MethodSource("stairProvider")
    fun climbStairs(numStairs: Int, expected: Int) {
        val actual = climbStairs(numStairs)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "longestPalindrome({0}) = {1}")
    @MethodSource("palindromeProvider")
    fun longestPalindrome(value: String, expected: Int) {
        val actual = longestPalindrome(value)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "twoSum({0}, {1}) = {2}")
    @MethodSource("addendProvider")
    fun twoSum(nums: IntArray, target: Int, expected: IntArray) {
        val actual = twoSum(nums, target)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "majorityElement({0}, {1})")
    @MethodSource("elementProvider")
    fun majorityElement(nums: IntArray, expected: Int) {
        val actual = majorityElement(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "addBinary({0}, {1}) = {2}")
    @MethodSource("binaryAddendProvider")
    fun addBinary(a: String, b: String, expected: String) {
        val actual = addBinary(a, b)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "containsDuplicate({0}) = {1}")
    @MethodSource("duplicatesProvider")
    fun containsDuplicate(nums: IntArray, expected: Boolean) {
        val actual = containsDuplicate(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "maxSubArray({0}) = {1}")
    @MethodSource("subArrayProvider")
    fun maxSubArray(nums: IntArray, expected: Int) {
        val actual = maxSubArray(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "insert({0}, {1}) = {2}")
    @MethodSource("intervalProvider")
    fun insert(intervals: Array<IntArray>, newInterval: IntArray, expected: Array<IntArray>) {
        val actual = insert(intervals, newInterval)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "updateMatrix({0}) = {1}")
    @MethodSource("matrixProvider")
    fun updateMatrix(matrix: Array<IntArray>, expected: Array<IntArray>) {
        val actual = updateMatrix(matrix)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "kClosest({0}, {1}) = {2}")
    @MethodSource("pointProvider")
    fun kClosest(points: Array<IntArray>, k: Int, expected: Array<IntArray>) {
        val actual = kClosest(points, k)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "lengthOfLongestSubstring({0}) = {1}")
    @MethodSource("substringProvider")
    fun lengthOfLongestSubstring(s: String, expected: Int) {
        val actual = lengthOfLongestSubstring(s)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "evalRPN({0}) = {1}")
    @MethodSource("tokenProvider")
    fun evalRPN(tokens: Array<String>, expected: Int) {
        val actual = evalRPN(tokens)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "lengthOfLIS({0}) = {1}")
    @MethodSource("subsequenceProvider")
    fun lengthOfLIS(nums: IntArray, expected: Int) {
        val actual = lengthOfLIS(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "isPalindrome({0}) = {1}")
    @MethodSource("palindromeNumProvider")
    fun isPalindrome(x: Int, expected: Boolean) {
        val actual = isPalindrome(x)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "longestCommonPrefix({0}) = {1}")
    @MethodSource("prefixProvider")
    fun longestCommonPrefix(words: Array<String>, expected: String) {
        val actual = longestCommonPrefix(words)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "jobScheduling({0}, {1}, {2}) = {3}")
    @MethodSource("jobProvider")
    @Disabled
    fun jobScheduling(startTimes: IntArray, endTimes: IntArray, profits: IntArray, expected: Int) {
        val actual = jobScheduling(startTimes, endTimes, profits)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "calculateTime({0}, {1}) = {2}")
    @MethodSource("keyboardProvider")
    fun calculateTime(keyboard: String, word: String, expected: Int) {
        val actual = calculateTime(keyboard, word)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "canJump({0}) = {1}")
    @MethodSource("jumpProvider")
    fun canJump(nums: IntArray, expected: Boolean) {
        val actual = canJump(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "isValid({0}) = {1}")
    @MethodSource("parenthesisProvider")
    fun isValid(given: String, expected: Boolean) {
        val actual = isValid(given)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "removeDuplicates({0}) = ({1}, {2})")
    @MethodSource("duplicateProvider")
    fun removeDuplicates(nums: IntArray, expectedLength: Int, expectedArray: IntArray) {
        val actual = removeDuplicates(nums)
        assertEquals(expectedLength, actual)
        assertArrayEquals(expectedArray, nums.take(expectedLength).toIntArray())
    }

    @ParameterizedTest(name = "findContentChildren({0}, {1}) = {2}")
    @MethodSource("childrenProvider")
    fun findContentChildren(greeds: IntArray, cookies: IntArray, expected: Int) {
        val actual = findContentChildren(greeds, cookies)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "findMatrix({0}) = {1}")
    @MethodSource("valueProvider")
    fun findMatrix(nums: IntArray, expected: List<List<Int>>) {
        val actual = findMatrix(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "numberOfBeams({0}) = {1}")
    @MethodSource("beamProvider")
    fun numberOfBeams(bank: Array<String>, expected: Int) {
        val actual = numberOfBeams(bank)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "minOperations({0}) = {1}")
    @MethodSource("operandProvider")
    fun minOperations(nums: IntArray, expected: Int) {
        val actual = minOperations(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "threeSum({0}) = {1}")
    @MethodSource("threeSumProvider")
    fun threeSum(nums: IntArray, expected: List<List<Int>>) {
        val actual = threeSum(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "longestPalindromicSubstring({0}) = {1}")
    @MethodSource("palindromicSubstringProvider")
    fun longestPalindromicSubstring(s: String, expected: String) {
        val actual = longestPalindromicSubstring(s)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "isAnagram({0}, {1}) = {2}")
    @MethodSource("anagramProvider")
    fun isAnagram(s: String, t: String, expected: Boolean) {
        val actual = isAnagram(s, t)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "productExceptSelf({0}) = {1}")
    @MethodSource("factorProvider")
    fun productExceptSelf(nums: IntArray, expected: IntArray) {
        val actual = productExceptSelf(nums)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "titleToNumber({0}) = {1}")
    @MethodSource("titleProvider")
    fun titleToNumber(columnTitle: String, expected: Int) {
        val actual = titleToNumber(columnTitle)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "sortTheStudents({0}, {1}) = {2}")
    @MethodSource("studentProvider")
    fun sortTheStudents(scores: Array<IntArray>, k: Int, expected: Array<IntArray>) {
        val actual = sortTheStudents(scores, k)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "coinChange({0}, {1}) = {2}")
    @MethodSource("coinProvider")
    fun coinChange(coins: IntArray, changeDue: Int, expected: Int) {
        val actual = coinChange(coins, changeDue)
        assertEquals(expected, actual)
    }

    @Test
    fun amountOfTime() {
        var root = TreeNode(
            1,
            TreeNode(5, right = TreeNode(4, TreeNode(9), TreeNode(2))), TreeNode(3, TreeNode(10), TreeNode(6))
        )

        var actual = amountOfTime(root, 3)

        assertEquals(4, actual)

        root = TreeNode(
            1,
            left = TreeNode(2, left = TreeNode(3, left = TreeNode(4, left = TreeNode(5))))
        )

        actual = amountOfTime(root, 3)

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
        val actual = minWindow(source, target)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "onesMinusZeros({0}) = {1}")
    @MethodSource("binaryMatrixProvider")
    fun onesMinusZeros(grid: Array<IntArray>, expected: Array<IntArray>) {
        val actual = onesMinusZeros(grid)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "havlesAreAlike({0}) = {1}")
    @MethodSource("halveProvider")
    fun halvesAreAlike(s: String, expected: Boolean) {
        val actual = halvesAreAlike(s)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "minSteps({0}, {1}) = {2}")
    @MethodSource("stepProvider")
    fun minSteps(s: String, t: String, expected: Int) {
        val actual = minSteps(s, t)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "maxFrequencyElements({0}) = {1}")
    @MethodSource("elementsProvider")
    fun maxFrequencyElements(nums: IntArray, expected: Int) {
        val actual = maxFrequencyElements(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "beautifulIndices({0}, {1}, {2}, {3}) = {4}")
    @MethodSource("beautyProvider")
    fun beautifulIndices(s: String, a: String, b: String, k: Int, expected: List<Int>) {
        val actual = beautifulIndices(s, a, b, k)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "sum({0}, {1}) = {2}")
    @MethodSource("sumProvider")
    fun sum(num1: Int, num2: Int, expected: Int) {
        val actual = sum(num1, num2)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "findWinners({0}) = {1}")
    @MethodSource("winnerProvider")
    fun findWinners(matches: Array<IntArray>, expected: List<List<Int>>) {
        val actual = findWinners(matches)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "findTheDifference({0}, {1}) = {2}")
    @MethodSource("differenceProvider")
    fun findTheDifference(s: String, t: String, expected: Char) {
        val actual = findTheDifference(s, t)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "strStr({0}, {1}) = {2}")
    @MethodSource("needleProvider")
    fun strStr(haystack: String, needle: String, expected: Int) {
        val actual = strStr(haystack, needle)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "mergeAlternately({0}, {1}) = {2}")
    @MethodSource("mergeProvider")
    fun mergeAlternately(word1: String, word2: String, expected: String) {
        val actual = mergeAlternately(word1, word2)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "repeatedSubstringPattern({0}) = {1}")
    @MethodSource("repeatedSubstringProvider")
    fun repeatedSubstringPattern(s: String, expected: Boolean) {
        val actual = repeatedSubstringPattern(s)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "moveZeroes({0}) = {1}")
    @MethodSource("zeroProvider")
    fun moveZeroes(nums: IntArray, expected: IntArray) {
        moveZeroes(nums)
        assertArrayEquals(expected, nums)
    }

    @ParameterizedTest(name = "uniqueOccurrences({0}) = {1}")
    @MethodSource("occurrenceProvider")
    fun uniqueOccurrences(nums: IntArray, expected: Boolean) {
        val actual = uniqueOccurrences(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "findMin({0}) = {1}")
    @MethodSource("minProvider")
    fun findMin(nums: IntArray, expected: Int) {
        val actual = findMin(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "findMin({0}, {1}) = {2}")
    @MethodSource("vowelStringProvider")
    fun vowelStrings(words: Array<String>, queries: Array<IntArray>, expected: IntArray) {
        val actual = vowelStrings(words, queries)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "plusOne({0}) = {1}")
    @MethodSource("plusOneProvider")
    fun plusOne(digits: IntArray, expected: IntArray) {
        val actual = plusOne(digits)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "arraySign({0}) = {1}")
    @MethodSource("arrayProvider")
    fun arraySign(nums: IntArray, expected: Int) {
        val actual = arraySign(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "canMakeArithmeticProgression({0}) = {1}")
    @MethodSource("progressionProvider")
    fun canMakeArithmeticProgression(nums: IntArray, expected: Boolean) {
        val actual = canMakeArithmeticProgression(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "isMonotonic({0}) = {1}")
    @MethodSource("monotonicProvider")
    fun isMonotonic(nums: IntArray, expected: Boolean) {
        val actual = isMonotonic(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "romanToInt({0}) = {1}")
    @MethodSource("romanProvider")
    fun romanToInt(s: String, expected: Int) {
        val actual = romanToInt(s)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "minFallingPathSum({0}) = {1}")
    @MethodSource("fallingPathProvider")
    fun minFallingPathSum(matrix: Array<IntArray>, expected: Int) {
        val actual = minFallingPathSum(matrix)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "findErrorNums({0}) = {1}")
    @MethodSource("errorNumProvider")
    fun findErrorNums(nums: IntArray, expected: IntArray) {
        val actual = findErrorNums(nums)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "searchInsert({0}, {1}) = {2}")
    @MethodSource("insertProvider")
    fun searchInsert(nums: IntArray, target: Int, expected: Int) {
        val actual = searchInsert(nums, target)
        assertEquals(expected, actual)
    }

    @Test
    fun divideArray() {
        val actual = divideArray(intArrayOf(1, 3, 4, 8, 7, 9, 3, 5, 1), 2)
        val expected = arrayOf(intArrayOf(1, 1, 3), intArrayOf(3, 4, 5), intArrayOf(7, 8, 9))
        assertArrayEquals(expected, actual)
    }

    @Test
    fun detectCycle() {
        val negativeFour = ListNode(-4, null)
        val two = ListNode(2, ListNode(0, negativeFour))
        negativeFour.next = two

        val head = ListNode(3, two)

        val actual = detectCycle(head)

        assertEquals(two, actual)
    }

    @ParameterizedTest(name = "maxSumAfterPartitioning({0}, {1}) = {2}")
    @MethodSource("partitionProvider")
    fun maxSumAfterPartitioning(nums: IntArray, k: Int, expected: Int) {
        val actual = maxSumAfterPartitioning(nums, k)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "returnToBoundaryCount({0}) = {1}")
    @MethodSource("boundaryProvider")
    fun returnToBoundaryCount(nums: IntArray, expected: Int) {
        val actual = returnToBoundaryCount(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "lengthOfLastWord({0}) = {1}")
    @MethodSource("wordProvider")
    fun lengthOfLastWord(word: String, expected: Int) {
        val actual = lengthOfLastWord(word)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "calPoints({0}) = {1}")
    @MethodSource("operationProvider")
    fun calPoints(operations: Array<String>, expected: Int) {
        val actual = calPoints(operations)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "judgeCircle({0}) = {1}")
    @MethodSource("moveProvider")
    fun judgeCircle(moves: String, expected: Boolean) {
        val actual = judgeCircle(moves)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "tictactoe({0}) = {1}")
    @MethodSource("ticTacToeProvider")
    fun tictactoe(moves: Array<IntArray>, expected: String) {
        val actual = tictactoe(moves)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "maximumWealth({0}) = {1}")
    @MethodSource("wealthProvider")
    fun maximumWealth(accounts: Array<IntArray>, expected: Int) {
        val actual = maximumWealth(accounts)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "firstUniqChr({0}) = {1}")
    @MethodSource("uniqueCharProvider")
    fun firstUniqChar(word: String, expected: Int) {
        val actual = firstUniqChar(word)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "groupAnagrams({0}) = {1}")
    @MethodSource("anagramGroupProvider")
    fun groupAnagrams(words: Array<String>, expected: List<List<String>>) {
        val actual = groupAnagrams(words)
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected)
    }

    @ParameterizedTest(name = "firstPalindrome({0}) = {1}")
    @MethodSource("palindromeListProvider")
    fun firstPalindrome(words: Array<String>, expected: String) {
        val actual = firstPalindrome(words)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "rearrangeArray({0}) = {1}")
    @MethodSource("arrangeableArrayProvider")
    fun rearrangeArray(nums: IntArray, expected: IntArray) {
        val actual = rearrangeArray(nums)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "frequencySort({0}) = {1}")
    @MethodSource("frequencySortProvider")
    @Disabled
    fun frequencySort(string: String, expected: String) {
        val actual = frequencySort(string)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "findLeastNumOfUniqueInts({0}, {1}) = {2}")
    @MethodSource("intArrayProvider")
    fun findLeastNumOfUniqueInts(array: IntArray, k: Int, expected: Int) {
        val actual = findLeastNumOfUniqueInts(array, k)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "furthestBuilding({0}, {1}, {2}) = {3}")
    @MethodSource("buildingProvider")
    fun furthestBuilding(heights: IntArray, bricks: Int, ladders: Int, expected: Int) {
        val actual = furthestBuilding(heights, bricks, ladders)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "isPowerOfTwo({0}) = {1}")
    @MethodSource("powerOfTwoProvider")
    fun isPowerOfTwo(n: Int, expected: Boolean) {
        val actual = isPowerOfTwo(n)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "missingNumber({0}) = {1}")
    @MethodSource("missingNumberProvider")
    fun missingNumber(nums: IntArray, expected: Int) {
        val actual = missingNumber(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "maxProduct({0}) = {1}")
    @MethodSource("maxProductProvider")
    fun maxProduct(nums: IntArray, expected: Int) {
        val actual = maxProduct(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "findJudge({0}, {1}) = {2}")
    @MethodSource("judgeProvider")
    fun findJudge(n: Int, trusts: Array<IntArray>, expected: Int) {
        val actual = findJudge(n, trusts)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "findKthLargest({0}, {1}) = {2}")
    @MethodSource("kthLargestProvider")
    fun findKthLargest(nums: IntArray, k: Int, expected: Int) {
        val actual = findKthLargest(nums, k)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "topKFrequent({0}, {1}) = {2}")
    @MethodSource("topKFrequentProvider")
    fun topKFrequent(nums: IntArray, k: Int, expected: IntArray) {
        val actual = topKFrequent(nums, k)
        assertThat(actual).containsExactlyInAnyOrder(expected.toTypedArray())
    }

    @ParameterizedTest(name = "validWordSquare({0}) = {1}")
    @MethodSource("wordSquareProvider")
    fun validWordSquare(words: List<String>, expected: Boolean) {
        val actual = validWordSquare(words)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "findCheapestPrice({0}, {1}, {2}, {3}, {4}) = {5}")
    @MethodSource("cheapestPriceProvider")
    fun findCheapestPrice(n: Int, flights: Array<IntArray>, src: Int, dst: Int, k: Int, expected: Int) {
        val actual = findCheapestPrice(n, flights, src, dst, k)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "findAllPeople({0}, {1}, {2}) = {3}")
    @MethodSource("peopleProvider")
    fun findAllPeople(n: Int, meetings: Array<IntArray>, firstPerson: Int, expected: List<Int>) {
        val actual = findAllPeople(n, meetings, firstPerson)
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected)
    }

    @ParameterizedTest(name = "maximumOddBinaryNumber({0}) = {1}")
    @MethodSource("binaryNumberProvider")
    fun maximumOddBinaryNumber(s: String, expected: String) {
        val actual = maximumOddBinaryNumber(s)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "numIslands({0}) = {1}")
    @MethodSource("islandProvider")
    fun numIslands(grid: Array<CharArray>, expected: Int) {
        val actual = numIslands(grid)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "sortedSquares({0}) = {1}")
    @MethodSource("squareProvider")
    fun sortedSquares(nums: IntArray, expected: IntArray) {
        val actual = sortedSquares(nums)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "buddyStrings({0}, {1}) = {2}")
    @MethodSource("buddyStringProvider")
    fun buddyStrings(s: String, goal: String, expected: Boolean) {
        val actual = buddyStrings(s, goal)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "bagOfTokensScore({0}, {1}) = {2}")
    @MethodSource("tokenBagProvider")
    fun bagOfTokensScore(tokens: IntArray, power: Int, expected: Int) {
        val actual = bagOfTokensScore(tokens, power)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "minimumLength({0}) = {1}")
    @MethodSource("minimumLengthStringProvider")
    fun minimumLength(s: String, expected: Int) {
        val actual = minimumLength(s)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "findMissingRanges({0}, {1}, {2}) = {3}")
    @MethodSource("missingRangeProvider")
    fun findMissingRanges(nums: IntArray, lower: Int, upper: Int, expected: List<List<Int>>) {
        val actual = findMissingRanges(nums, lower, upper)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "confusingNumber({0}) = {1}")
    @MethodSource("confusingNumberProvider")
    fun confusingNumber(n: Int, expected: Boolean) {
        val actual = confusingNumber(n)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "intersection({0}, {1}) = {2}")
    @MethodSource("intersectionProvider")
    fun intersection(nums1: IntArray, nums2: IntArray, expected: IntArray) {
        val actual = intersection(nums1, nums2)
        assertThat(actual.toList()).containsExactlyInAnyOrderElementsOf(expected.toList())
    }

    @ParameterizedTest(name = "customSortString({0}, {1}) = {2}")
    @MethodSource("customStringProvider")
    fun customSortString(order: String, s: String, expected: String) {
        val actual = customSortString(order, s)
        val range = 0..minOf(order.length-1, s.length-1)
        assertThat(actual.substring(range)).isEqualTo(expected.substring(range))
    }

    @ParameterizedTest(name = "pivotInteger({0}) = {1}")
    @MethodSource("pivotIntegerProvider")
    fun pivotInteger(n: Int, expected: Int) {
        val actual = pivotInteger(n)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "numSubarraysWithSum({0}, {1}) = {2}")
    @MethodSource("sumSubarrayProvider")
    fun numSubarraysWithSum(nums: IntArray, goal: Int, expected: Int) {
        val actual = numSubarraysWithSum(nums, goal)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "findDuplicate({0}) = {1}")
    @MethodSource("duplicatesArrayProvider")
    fun findDuplicates(nums: IntArray, expected: List<Int>) {
        val actual = findDuplicates(nums)
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected)
    }

    @ParameterizedTest(name = "firstMissingPositive({0}) = {1}")
    @MethodSource("missingPositiveProvider")
    fun firstMissingPositive(nums: IntArray, expected: Int) {
        val actual = firstMissingPositive(nums)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "numSubarrayProductLessThanK({0}, {1}) = {2}")
    @MethodSource("subarrayProductProvider")
    fun numSubarrayProductLessThanK(nums: IntArray, k: Int, expected: Int) {
        val actual = numSubarrayProductLessThanK(nums, k)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "maxSubarrayLength({0}, {1}) = {2}")
    @MethodSource("subarrayLengthConstraintProvider")
    fun maxSubarrayLength(nums: IntArray, k: Int, expected: Int) {
        val actual = maxSubarrayLength(nums, k)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "isIsomorphic({0}, {1}) = {2}")
    @MethodSource("isomorphicStringProvider")
    fun isIsomorphic(s: String, t: String, expected: Boolean) {
        val actual = isIsomorphic(s, t)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "subarraysWithKDistinct({0}) = {1}")
    @MethodSource("subarrayWithKDistinctProvider")
    fun subarraysWithKDistinct(nums: IntArray, k: Int, expected: Int) {
        val actual = subarraysWithKDistinct(nums, k)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "countSubarrays({0}, {1}, {2}) = {3}")
    @MethodSource("constrainedSubarrayProvider")
    fun countSubarrays(nums: IntArray, minK: Int, maxK: Int, expected: Long) {
        val actual = countSubarrays(nums, minK, maxK)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "exist({0}, {1}) = {2}")
    @MethodSource("crosswordProvider")
    fun exist(board: Array<CharArray>, word: String, expected: Boolean) {
        val actual = exist(board, word)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "maxDepth({0}) = {1}")
    @MethodSource("maxDepthProvider")
    fun maxDepth(s: String, expected: Int) {
        val actual = maxDepth(s)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "makeGood({0}) = {1}")
    @MethodSource("goodStringProvider")
    fun makeGood(s: String, expected: String) {
        val actual = makeGood(s)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "minRemoveToMakeValid({0}) = {1}")
    @MethodSource("removeToMakeValidProvider")
    fun minRemoveToMakeValid(s: String, expected: String) {
        val actual = minRemoveToMakeValid(s)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "checkValidString({0}) = {1}")
    @MethodSource("wildcardValidStringProvider")
    fun checkValidString(s: String, expected: Boolean) {
        val actual = checkValidString(s)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "countStudents({0}, {1}) = {2}")
    @MethodSource("studentSandwichProvider")
    fun countStudents(students: IntArray, sandwiches: IntArray, expected: Int) {
        val actual = countStudents(students, sandwiches)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "timeRequiredToBuy({0}, {1}) = {2}")
    @MethodSource("ticketConstraintProvider")
    fun timeRequiredToBuy(tickets: IntArray, k: Int, expected: Int) {
        val actual = timeRequiredToBuy(tickets, k)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "deckRevealedIncreasing({0}) = {1}")
    @MethodSource("deckProvider")
    fun deckRevealedIncreasing(deck: IntArray, expected: IntArray) {
        val actual = deckRevealedIncreasing(deck)
        assertThat(actual.toList()).isEqualTo(expected.toList())
    }


    @ParameterizedTest(name = "removeKdigits({0}, {1}) = {2}")
    @MethodSource("kdigitsProvider")
    fun removeKdigits(num: String, k: Int, expected: String) {
        val actual = removeKdigits(num, k)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "trap({0}) = {1}")
    @MethodSource("waterTrapProvider")
    fun trap(heights: IntArray, expected: Int) {
        val actual = trap(heights)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "maximalRectangle{0}) = {1}")
    @MethodSource("rectangleProvider")
    fun maximalRectangle(matrix: Array<CharArray>, expected: Int) {
        val actual = maximalRectangle(matrix)
        assertThat(actual).isEqualTo(expected)
    }

    @AfterEach
    fun tearDown() {
        System.clearProperty("FIRST_BAD_VERSION")
    }
}