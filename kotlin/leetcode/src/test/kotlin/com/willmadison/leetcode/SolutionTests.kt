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
        fun stairProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(2, 2),
                Arguments.of(3, 3),
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
        fun farmlandProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        intArrayOf(1, 0, 0),
                        intArrayOf(0, 1, 1),
                        intArrayOf(0, 1, 1),
                    ),
                    arrayOf(
                        intArrayOf(0, 0, 0, 0),
                        intArrayOf(1, 1, 2, 2),
                    ),
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(1, 1),
                        intArrayOf(1, 1),
                    ),
                    arrayOf(
                        intArrayOf(0, 0, 1, 1),
                    ),
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(0),
                    ),
                    emptyArray<IntArray>()
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
                    intArrayOf(0, 1, 3, 50, 75),
                    0,
                    99,
                    listOf(
                        listOf(2, 2),
                        listOf(4, 49),
                        listOf(51, 74),
                        listOf(76, 99),
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
                    intArrayOf(1, 2, 2, 1),
                    intArrayOf(2, 2),
                    intArrayOf(2),
                ),
                Arguments.of(
                    intArrayOf(4, 9, 5),
                    intArrayOf(9, 4, 9, 8, 4),
                    intArrayOf(4, 9),
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
                    intArrayOf(1, 0, 1, 0, 1),
                    2,
                    4,
                ),
                Arguments.of(
                    intArrayOf(0, 0, 0, 0, 0),
                    0,
                    15,
                ),
            )
        }

        @JvmStatic
        fun duplicatesArrayProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(4, 3, 2, 7, 8, 2, 3, 1),
                    listOf(2, 3),
                ),
                Arguments.of(
                    intArrayOf(1, 1, 2),
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
                    intArrayOf(1, 2, 0),
                    3,
                ),
                Arguments.of(
                    intArrayOf(3, 4, -1, 1),
                    2,
                ),
                Arguments.of(
                    intArrayOf(7, 8, 9, 11, 12),
                    1,
                ),
            )
        }

        @JvmStatic
        fun subarrayProductProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(10, 5, 2, 6),
                    100,
                    8,
                ),
                Arguments.of(
                    intArrayOf(1, 2, 3),
                    0,
                    0,
                ),
            )
        }

        @JvmStatic
        fun subarrayLengthConstraintProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 2, 3, 1, 2, 3, 1, 2),
                    2,
                    6,
                ),
                Arguments.of(
                    intArrayOf(1, 2, 1, 2, 1, 2, 1, 2),
                    1,
                    2,
                ),
                Arguments.of(
                    intArrayOf(5, 5, 5, 5, 5, 5, 5),
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
                    intArrayOf(1, 2, 1, 2, 3),
                    2,
                    7,
                ),
                Arguments.of(
                    intArrayOf(1, 2, 1, 3, 4),
                    3,
                    3,
                ),
            )
        }

        @JvmStatic
        fun constrainedSubarrayProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 3, 5, 2, 7, 5),
                    1,
                    5,
                    2,
                ),
                Arguments.of(
                    intArrayOf(1, 1, 1, 1),
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
                    intArrayOf(1, 1, 0, 0),
                    intArrayOf(0, 1, 0, 1),
                    0,
                ),
                Arguments.of(
                    intArrayOf(1, 1, 1, 0, 0, 1),
                    intArrayOf(1, 0, 0, 0, 1, 1),
                    3,
                ),
            )
        }

        @JvmStatic
        fun ticketConstraintProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(2, 3, 2),
                    2,
                    6,
                ),
                Arguments.of(
                    intArrayOf(5, 1, 1, 1),
                    0,
                    8,
                ),
            )
        }

        @JvmStatic
        fun deckProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(17, 13, 11, 2, 3, 5, 7),
                    intArrayOf(2, 13, 3, 11, 5, 17, 7),
                ),
                Arguments.of(
                    intArrayOf(1, 1000),
                    intArrayOf(1, 1000),
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
                    intArrayOf(0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1),
                    6,
                ),
                Arguments.of(
                    intArrayOf(4, 2, 0, 3, 2, 5),
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

        @JvmStatic
        fun numberProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "0",
                    true,
                ),
                Arguments.of(
                    "e",
                    false,
                ),
                Arguments.of(
                    ".",
                    false,
                ),
            )
        }

        @JvmStatic
        fun islandPerimeterProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        intArrayOf(0, 1, 0, 0),
                        intArrayOf(1, 1, 1, 0),
                        intArrayOf(0, 1, 0, 0),
                        intArrayOf(1, 1, 0, 0),
                    ),
                    16,
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(1),
                    ),
                    4,
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(1, 0),
                    ),
                    4,
                ),
            )
        }

        @JvmStatic
        fun lockCombinationProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        "0201", "0101", "0102", "1212", "2002",
                    ),
                    "0202",
                    6,
                ),
                Arguments.of(
                    arrayOf(
                        "8888",
                    ),
                    "0009",
                    1,
                ),
                Arguments.of(
                    arrayOf(
                        "8887","8889","8878","8898","8788","8988","7888","9888"
                    ),
                    "8888",
                    -1,
                ),
            )
        }

        @JvmStatic
        fun minHeightTreeProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    6,
                    arrayOf(
                        intArrayOf(3,0),
                        intArrayOf(3,1),
                        intArrayOf(3,2),
                        intArrayOf(3,4),
                        intArrayOf(5,4),
                    ),
                    listOf(3,4),
                ),
                Arguments.of(
                    4,
                    arrayOf(
                        intArrayOf(1,0),
                        intArrayOf(1,2),
                        intArrayOf(1,3),
                    ),
                    listOf(1),
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
        fun idealStringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "acfgbd",
                    2,
                    4,
                ),
                Arguments.of(
                    "abcd",
                    3,
                    4,
                ),
            )
        }

        @JvmStatic
        fun rotateStepProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "godding",
                    "gd",
                    4,
                ),
                Arguments.of(
                    "godding",
                    "godding",
                    13,
                ),
            )
        }

        @JvmStatic
        fun minOperationProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(2,1,3,4),
                    1,
                    2,
                ),
                Arguments.of(
                    intArrayOf(2,0,2,0),
                    0,
                    0,
                ),
            )
        }

        @JvmStatic
        fun wonderfulSubstringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "aba",
                    4L,
                ),
                Arguments.of(
                    "aabb",
                    9L,
                ),
                Arguments.of(
                    "he",
                    2L,
                ),
            )
        }
    }

    @BeforeEach
    fun setUp() {
        solution = Solution()
    }

//    @Test
//    @Disabled
//    fun firstBadVersion() {
//        System.setProperty("FIRST_BAD_VERSION", "4")
//
//        val expected = 4
//
//        val actual = solution.firstBadVersion(10)
//
//        assertEquals(expected, actual)
//    }

//    @ParameterizedTest(name = "countTestedDevices({0}) = {1}")
//    @MethodSource("testedDeviceProvider")
//    fun countTestedDevices(percentages: IntArray, expected: Int) {
//        val actual = countTestedDevices(percentages)
//        assertEquals(expected, actual)
//    }

//    @ParameterizedTest(name = "getGoodIndices({0}, {1}) = {2}")
//    @MethodSource("variablesProvider")
//    fun getGoodIndices(variables: Array<IntArray>, target: Int, expected: List<Int>) {
//        val actual = getGoodIndices(variables, target)
//        assertEquals(expected, actual)
//    }

//    @ParameterizedTest(name = "climbStairs({0}) = {1}")
//    @MethodSource("stairProvider")
//    fun climbStairs(numStairs: Int, expected: Int) {
//        val actual = climbStairs(numStairs)
//        assertEquals(expected, actual)
//    }

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



    @ParameterizedTest(name = "searchInsert({0}, {1}) = {2}")
    @MethodSource("insertProvider")
    fun searchInsert(nums: IntArray, target: Int, expected: Int) {
        val actual = searchInsert(nums, target)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "findRotateSteps({0}, {1}) = {2}")
    @MethodSource("rotateStepProvider")
    fun findRotateSteps(ring: String, key: String, expected: Int) {
        val actual = findRotateSteps(ring, key)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun divideArray() {
        val actual = divideArray(intArrayOf(1, 3, 4, 8, 7, 9, 3, 5, 1), 2)
        val expected = arrayOf(intArrayOf(1, 1, 3), intArrayOf(3, 4, 5), intArrayOf(7, 8, 9))
        assertArrayEquals(expected, actual)
    }

//    @Test
//    fun detectCycle() {
//        val negativeFour = ListNode(-4, null)
//        val two = ListNode(2, ListNode(0, negativeFour))
//        negativeFour.next = two
//
//        val head = ListNode(3, two)
//
//        val actual = detectCycle(head)
//
//        assertEquals(two, actual)
//    }

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

    @ParameterizedTest(name = "findFarmland({0}) = {1}")
    @MethodSource("farmlandProvider")
    fun findFarmland(land: Array<IntArray>, expected: Array<IntArray>) {
        val actual = findFarmland(land)
        assertThat(actual.toList()).containsExactlyInAnyOrderElementsOf(expected.toList())
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
        val range = 0..minOf(order.length - 1, s.length - 1)
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

    @ParameterizedTest(name = "isNumber{0}) = {1}")
    @MethodSource("numberProvider")
    fun isNumber(s: String, expected: Boolean) {
        val actual = isNumber(s)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "islandPerimeter({0}) = {1}")
    @MethodSource("islandPerimeterProvider")
    fun islandPerimeter(grid: Array<IntArray>, expected: Int) {
        val actual = islandPerimeter(grid)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "openLock({0}, {1}) = {2}")
    @MethodSource("lockCombinationProvider")
    fun openLock(deadends: Array<String>, combination: String, expected: Int) {
        val actual = openLock(deadends, combination)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "findMinHeightTrees({0}, {1}) = {2}")
    @MethodSource("minHeightTreeProvider")
    fun findMinHeightTrees(n: Int, edges: Array<IntArray>, expected: List<Int>) {
        val actual = findMinHeightTrees(n, edges)
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected)
    }

    @ParameterizedTest(name = "tribonacci({0}) = {1}")
    @MethodSource("tribonacciProvider")
    fun tribonacci(n: Int, expected: Int) {
        val actual = tribonacci(n)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "longestIdealString({0}, {1}) = {2}")
    @MethodSource("idealStringProvider")
    fun longestIdealString(s: String, k: Int, expected: Int) {
        val actual = longestIdealString(s, k)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "minXOROperations({0}, {1}) = {2}")
    @MethodSource("minOperationProvider")
    fun minXOROperations(nums: IntArray, k: Int, expected: Int) {
        val actual = minXOROperations(nums, k)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "wonderfulSubstrings({0}) = {1}")
    @MethodSource("wonderfulSubstringProvider")
    fun wonderfulSubstrings(word: String, expected: Long) {
        val actual = wonderfulSubstrings(word)
        assertThat(actual).isEqualTo(expected)
    }




    @AfterEach
    fun tearDown() {
        System.clearProperty("FIRST_BAD_VERSION")
    }
}