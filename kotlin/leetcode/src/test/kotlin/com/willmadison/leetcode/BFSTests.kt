package com.willmadison.leetcode

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class BFSTests {

    companion object {
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
                        "8887", "8889", "8878", "8898", "8788", "8988", "7888", "9888"
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
                        intArrayOf(3, 0),
                        intArrayOf(3, 1),
                        intArrayOf(3, 2),
                        intArrayOf(3, 4),
                        intArrayOf(5, 4),
                    ),
                    listOf(3, 4),
                ),
                Arguments.of(
                    4,
                    arrayOf(
                        intArrayOf(1, 0),
                        intArrayOf(1, 2),
                        intArrayOf(1, 3),
                    ),
                    listOf(1),
                ),
            )
        }

        @JvmStatic
        fun maxGoldProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        intArrayOf(0, 6, 0),
                        intArrayOf(5, 8, 7),
                        intArrayOf(0, 9, 0),
                    ),
                    24,
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(1, 0, 7),
                        intArrayOf(2, 0, 6),
                        intArrayOf(3, 4, 5),
                        intArrayOf(0, 3, 0),
                        intArrayOf(9, 0, 20),
                    ),
                    28,
                ),
            )
        }
    }

    @ParameterizedTest(name = "numIslands({0}) = {1}")
    @MethodSource("islandProvider")
    fun numIslands(grid: Array<CharArray>, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.numIslands(grid)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "findFarmland({0}) = {1}")
    @MethodSource("farmlandProvider")
    fun findFarmland(land: Array<IntArray>, expected: Array<IntArray>) {
        val actual = com.willmadison.leetcode.extensions.findFarmland(land)
        assertThat(actual.toList()).containsExactlyInAnyOrderElementsOf(expected.toList())
    }

    @ParameterizedTest(name = "openLock({0}, {1}) = {2}")
    @MethodSource("lockCombinationProvider")
    fun openLock(deadends: Array<String>, combination: String, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.openLock(deadends, combination)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "findMinHeightTrees({0}, {1}) = {2}")
    @MethodSource("minHeightTreeProvider")
    fun findMinHeightTrees(n: Int, edges: Array<IntArray>, expected: List<Int>) {
        val actual = com.willmadison.leetcode.extensions.findMinHeightTrees(n, edges)
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected)
    }

    @ParameterizedTest(name = "getMaximumGold({0}) = {1}")
    @MethodSource("maxGoldProvider")
    fun getMaximumGold(grid: Array<IntArray>, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.getMaximumGold(grid)
        assertThat(actual).isEqualTo(expected)
    }
}