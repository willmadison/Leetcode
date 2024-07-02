package com.willmadison.leetcode

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Offset
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
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
        fun intersectProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 2, 2, 1),
                    intArrayOf(2, 2),
                    intArrayOf(2, 2),
                ),
                Arguments.of(
                    intArrayOf(4, 9, 5),
                    intArrayOf(9, 4, 9, 8, 4),
                    intArrayOf(4, 9),
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
        fun rescueBoatProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 2),
                    3,
                    1,
                ),
                Arguments.of(
                    intArrayOf(3, 2, 2, 1),
                    3,
                    3,
                ),
                Arguments.of(
                    intArrayOf(3, 5, 3, 4),
                    5,
                    4,
                ),
            )
        }

        @JvmStatic
        fun relativeRankProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(5, 4, 3, 2, 1),
                    arrayOf("Gold Medal", "Silver Medal", "Bronze Medal", "4", "5")
                ),
                Arguments.of(
                    intArrayOf(10, 3, 8, 9, 4),
                    arrayOf("Gold Medal", "5", "Bronze Medal", "Silver Medal", "4")
                ),
            )
        }

        @JvmStatic
        fun happinessProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 2, 3),
                    2,
                    4L,
                ),
                Arguments.of(
                    intArrayOf(1, 1, 1, 1),
                    2,
                    1L,
                ),
                Arguments.of(
                    intArrayOf(2, 3, 4, 5),
                    1,
                    5L,
                ),

                )
        }

        @JvmStatic
        fun numeratorDenominatorProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 2, 3, 5),
                    3,
                    intArrayOf(2, 5),
                ),
                Arguments.of(
                    intArrayOf(1, 7),
                    1,
                    intArrayOf(1, 7),
                ),
                Arguments.of(
                    intArrayOf(1, 29, 47),
                    1,
                    intArrayOf(1, 47),
                ),

                )
        }

        @JvmStatic
        fun candyman(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(2, 3, 5, 1, 3),
                    3,
                    listOf(true, true, true, false, true),
                ),
                Arguments.of(
                    intArrayOf(4, 2, 1, 1, 2),
                    1,
                    listOf(true, false, false, false, false),
                ),
                Arguments.of(
                    intArrayOf(12, 1, 12),
                    10,
                    listOf(true, false, true),
                ),
            )
        }

        @JvmStatic
        fun workerCostProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(10, 20, 5),
                    intArrayOf(70, 50, 30),
                    2,
                    105.0
                ),
                Arguments.of(
                    intArrayOf(3, 1, 10, 10, 1),
                    intArrayOf(4, 8, 2, 2, 7),
                    3,
                    30.66667
                ),
            )
        }

        @JvmStatic
        fun largestLocalProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        intArrayOf(9, 9, 8, 1),
                        intArrayOf(5, 6, 2, 6),
                        intArrayOf(8, 2, 6, 4),
                        intArrayOf(6, 2, 2, 2),
                    ),
                    arrayOf(
                        intArrayOf(9, 9),
                        intArrayOf(8, 6),
                    ),
                ),
            )
        }

        @JvmStatic
        fun matrixFlipScoreProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        intArrayOf(0, 0, 1, 1),
                        intArrayOf(1, 0, 1, 0),
                        intArrayOf(1, 1, 0, 0),
                    ),
                    39,
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(0)
                    ),
                    1,
                ),
            )
        }

        @JvmStatic
        fun beautifulSubsetProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(2, 4, 6),
                    2,
                    4,
                ),
                Arguments.of(
                    intArrayOf(1),
                    1,
                    1,
                ),
            )
        }

        @JvmStatic
        fun specialArrayProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(3, 5),
                    2,
                ),
                Arguments.of(
                    intArrayOf(0, 0),
                    -1,
                ),
                Arguments.of(
                    intArrayOf(0, 4, 3, 0, 4),
                    3,
                ),
                Arguments.of(
                    intArrayOf(3, 9, 7, 8, 3, 8, 6, 6),
                    6,
                ),
            )
        }

        @JvmStatic
        fun tripletProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(2, 3, 1, 6, 7),
                    4,
                ),
                Arguments.of(
                    intArrayOf(1, 1, 1, 1, 1),
                    10,
                ),
            )
        }

        @JvmStatic
        fun singleNumberProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 2, 1, 3, 2, 5),
                    intArrayOf(3, 5),
                ),
                Arguments.of(
                    intArrayOf(-1, 0),
                    intArrayOf(-1, 0),
                ),
                Arguments.of(
                    intArrayOf(1, 0),
                    intArrayOf(1, 0),
                ),
            )
        }

        @JvmStatic
        fun scoreStringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "hello",
                    13,
                ),
                Arguments.of(
                    "zaz",
                    50,
                ),
            )
        }

        @JvmStatic
        fun straightHandProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 2, 3, 6, 2, 3, 4, 7, 8),
                    3,
                    true,
                ),
                Arguments.of(
                    intArrayOf(1, 2, 3, 4, 5),
                    4,
                    false,
                ),
                Arguments.of(
                    intArrayOf(1, 2, 3, 4, 5, 6),
                    2,
                    true,
                ),
            )
        }

        @JvmStatic
        fun subarraySumProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(23, 2, 4, 6, 7),
                    6,
                    true,
                ),
                Arguments.of(
                    intArrayOf(23, 2, 6, 4, 7),
                    6,
                    true,
                ),
                Arguments.of(
                    intArrayOf(23, 2, 6, 4, 7),
                    13,
                    false,
                ),
            )
        }

        @JvmStatic
        fun subarrayDivByKProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(4, 5, 0, -2, -3, 1),
                    5,
                    7,
                ),
                Arguments.of(
                    intArrayOf(5),
                    9,
                    0,
                ),
            )
        }

        @JvmStatic
        fun heightProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 1, 4, 2, 1, 3),
                    3,
                ),
                Arguments.of(
                    intArrayOf(5, 1, 2, 3, 4),
                    5,
                ),
                Arguments.of(
                    intArrayOf(1, 2, 3, 4, 5),
                    0,
                ),
            )
        }

        @JvmStatic
        fun relativeSortArrayProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(2, 3, 1, 3, 2, 4, 6, 7, 9, 2, 19),
                    intArrayOf(2, 1, 4, 3, 9, 6),
                    intArrayOf(2, 2, 2, 1, 4, 3, 3, 9, 6, 7, 19),
                ),
                Arguments.of(
                    intArrayOf(28, 6, 22, 8, 44, 17),
                    intArrayOf(22, 28, 8, 6),
                    intArrayOf(22, 28, 8, 6, 17, 44),
                ),
            )
        }

        @JvmStatic
        fun studentSeatProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(3, 1, 5),
                    intArrayOf(2, 7, 4),
                    4,
                ),
                Arguments.of(
                    intArrayOf(4, 1, 5, 9),
                    intArrayOf(1, 3, 2, 6),
                    7,
                ),
                Arguments.of(
                    intArrayOf(2, 2, 6, 6),
                    intArrayOf(1, 3, 2, 6),
                    4,
                ),
            )
        }

        @JvmStatic
        fun minIncrementForUniqueProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 2, 2),
                    1,
                ),
                Arguments.of(
                    intArrayOf(3, 2, 1, 2, 1, 7),
                    6,
                ),
            )
        }

        @JvmStatic
        fun maximizedCapitalProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    2,
                    0,
                    intArrayOf(1, 2, 3),
                    intArrayOf(0, 1, 1),
                    4,
                ),
                Arguments.of(
                    3,
                    0,
                    intArrayOf(1, 2, 3),
                    intArrayOf(0, 1, 2),
                    6,
                ),
            )
        }

        @JvmStatic
        fun minPatchesProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 3),
                    6,
                    1,
                ),
                Arguments.of(
                    intArrayOf(1, 5, 10),
                    20,
                    2,
                ),
                Arguments.of(
                    intArrayOf(1, 2, 2),
                    5,
                    0,
                ),
            )
        }

        @JvmStatic
        fun profitAssignmentProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(2, 4, 6, 8, 10),
                    intArrayOf(10, 20, 30, 40, 50),
                    intArrayOf(4, 5, 6, 7),
                    100,
                ),
                Arguments.of(
                    intArrayOf(85, 47, 57),
                    intArrayOf(24, 66, 99),
                    intArrayOf(40, 25, 25),
                    0,
                ),
                Arguments.of(
                    intArrayOf(68, 35, 52, 47, 86),
                    intArrayOf(67, 17, 1, 81, 3),
                    intArrayOf(92, 10, 85, 84, 82),
                    324,
                ),
            )
        }

        @JvmStatic
        fun minDaysProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 10, 3, 10, 2),
                    3,
                    1,
                    3,
                ),
                Arguments.of(
                    intArrayOf(1, 10, 3, 10, 2),
                    3,
                    2,
                    -1,
                ),
                Arguments.of(
                    intArrayOf(7, 7, 7, 7, 12, 7, 7),
                    2,
                    3,
                    12,
                ),
            )
        }

        @JvmStatic
        fun maxDistanceProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 2, 3, 4, 7),
                    3,
                    3,
                ),
                Arguments.of(
                    intArrayOf(5, 4, 3, 2, 1, 1000000000),
                    2,
                    999999999,
                ),
            )
        }

        @JvmStatic
        fun maxSatisfactionProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 0, 1, 2, 1, 1, 7, 5),
                    intArrayOf(0, 1, 0, 1, 0, 1, 0, 1),
                    3,
                    16,
                ),
                Arguments.of(
                    intArrayOf(1),
                    intArrayOf(0),
                    1,
                    1,
                ),
            )
        }

        @JvmStatic
        fun niceSubarrayProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(1, 1, 2, 1, 1),
                    3,
                    2,
                ),
                Arguments.of(
                    intArrayOf(2, 4, 6),
                    1,
                    0,
                ),
                Arguments.of(
                    intArrayOf(2, 2, 2, 1, 2, 2, 1, 2, 2, 2),
                    2,
                    16,
                ),
            )
        }

        @JvmStatic
        fun longestSubarrayProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(8, 2, 4, 7),
                    4,
                    2,
                ),
                Arguments.of(
                    intArrayOf(10, 1, 2, 4, 7, 2),
                    5,
                    4,
                ),
                Arguments.of(
                    intArrayOf(4, 2, 2, 2, 4, 4, 2, 2),
                    0,
                    3,
                ),
            )
        }

        @JvmStatic
        fun minKBitFlipProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    intArrayOf(0, 1, 0),
                    1,
                    2,
                ),
                Arguments.of(
                    intArrayOf(1, 1, 0),
                    2,
                    -1,
                ),
                Arguments.of(
                    intArrayOf(0,0,0,1,0,1,1,0),
                    3,
                    3,
                ),
            )
        }

        @JvmStatic
        fun centerStarProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        intArrayOf(1, 2),
                        intArrayOf(2, 3),
                        intArrayOf(4, 2),
                    ),
                    2,
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(1, 2),
                        intArrayOf(5, 1),
                        intArrayOf(1, 3),
                        intArrayOf(1, 4),
                    ),
                    1,
                ),
            )
        }

        @JvmStatic
        fun importanceProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    5,
                    arrayOf(
                        intArrayOf(0, 1),
                        intArrayOf(1, 2),
                        intArrayOf(2, 3),
                        intArrayOf(0, 2),
                        intArrayOf(1, 3),
                        intArrayOf(2, 4),
                    ),
                    43L,
                ),
                Arguments.of(
                    5,
                    arrayOf(
                        intArrayOf(0, 3),
                        intArrayOf(2, 4),
                        intArrayOf(1, 3)
                    ),
                    20L,
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

    @ParameterizedTest(name = "searchInsert({0}, {1}) = {2}")
    @MethodSource("insertProvider")
    fun searchInsert(nums: IntArray, target: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.searchInsert(nums, target)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "maxSumAfterPartitioning({0}, {1}) = {2}")
    @MethodSource("partitionProvider")
    fun maxSumAfterPartitioning(nums: IntArray, k: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.maxSumAfterPartitioning(nums, k)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "returnToBoundaryCount({0}) = {1}")
    @MethodSource("boundaryProvider")
    fun returnToBoundaryCount(nums: IntArray, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.returnToBoundaryCount(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "maximumWealth({0}) = {1}")
    @MethodSource("wealthProvider")
    fun maximumWealth(accounts: Array<IntArray>, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.maximumWealth(accounts)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "rearrangeArray({0}) = {1}")
    @MethodSource("arrangeableArrayProvider")
    fun rearrangeArray(nums: IntArray, expected: IntArray) {
        val actual = com.willmadison.leetcode.extensions.rearrangeArray(nums)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "findLeastNumOfUniqueInts({0}, {1}) = {2}")
    @MethodSource("intArrayProvider")
    fun findLeastNumOfUniqueInts(array: IntArray, k: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.findLeastNumOfUniqueInts(array, k)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "furthestBuilding({0}, {1}, {2}) = {3}")
    @MethodSource("buildingProvider")
    fun furthestBuilding(heights: IntArray, bricks: Int, ladders: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.furthestBuilding(heights, bricks, ladders)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "missingNumber({0}) = {1}")
    @MethodSource("missingNumberProvider")
    fun missingNumber(nums: IntArray, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.missingNumber(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "maxProduct({0}) = {1}")
    @MethodSource("maxProductProvider")
    fun maxProduct(nums: IntArray, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.maxProduct(nums)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "findJudge({0}, {1}) = {2}")
    @MethodSource("judgeProvider")
    fun findJudge(n: Int, trusts: Array<IntArray>, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.findJudge(n, trusts)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "findKthLargest({0}, {1}) = {2}")
    @MethodSource("kthLargestProvider")
    fun findKthLargest(nums: IntArray, k: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.findKthLargest(nums, k)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "topKFrequent({0}, {1}) = {2}")
    @MethodSource("topKFrequentProvider")
    fun topKFrequent(nums: IntArray, k: Int, expected: IntArray) {
        val actual = com.willmadison.leetcode.extensions.topKFrequent(nums, k)
        assertThat(actual).containsExactlyInAnyOrder(expected.toTypedArray())
    }

    @ParameterizedTest(name = "findAllPeople({0}, {1}, {2}) = {3}")
    @MethodSource("peopleProvider")
    fun findAllPeople(n: Int, meetings: Array<IntArray>, firstPerson: Int, expected: List<Int>) {
        val actual = com.willmadison.leetcode.extensions.findAllPeople(n, meetings, firstPerson)
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected)
    }

    @ParameterizedTest(name = "sortedSquares({0}) = {1}")
    @MethodSource("squareProvider")
    fun sortedSquares(nums: IntArray, expected: IntArray) {
        val actual = com.willmadison.leetcode.extensions.sortedSquares(nums)
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "bagOfTokensScore({0}, {1}) = {2}")
    @MethodSource("tokenBagProvider")
    fun bagOfTokensScore(tokens: IntArray, power: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.bagOfTokensScore(tokens, power)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "findMissingRanges({0}, {1}, {2}) = {3}")
    @MethodSource("missingRangeProvider")
    fun findMissingRanges(nums: IntArray, lower: Int, upper: Int, expected: List<List<Int>>) {
        val actual = com.willmadison.leetcode.extensions.findMissingRanges(nums, lower, upper)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "intersection({0}, {1}) = {2}")
    @MethodSource("intersectionProvider")
    fun intersection(nums1: IntArray, nums2: IntArray, expected: IntArray) {
        val actual = com.willmadison.leetcode.extensions.intersection(nums1, nums2)
        assertThat(actual.toList()).containsExactlyInAnyOrderElementsOf(expected.toList())
    }

    @ParameterizedTest(name = "numSubarraysWithSum({0}, {1}) = {2}")
    @MethodSource("sumSubarrayProvider")
    fun numSubarraysWithSum(nums: IntArray, goal: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.numSubarraysWithSum(nums, goal)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "findDuplicate({0}) = {1}")
    @MethodSource("duplicatesArrayProvider")
    fun findDuplicates(nums: IntArray, expected: List<Int>) {
        val actual = com.willmadison.leetcode.extensions.findDuplicates(nums)
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected)
    }

    @ParameterizedTest(name = "firstMissingPositive({0}) = {1}")
    @MethodSource("missingPositiveProvider")
    fun firstMissingPositive(nums: IntArray, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.firstMissingPositive(nums)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "numSubarrayProductLessThanK({0}, {1}) = {2}")
    @MethodSource("subarrayProductProvider")
    fun numSubarrayProductLessThanK(nums: IntArray, k: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.numSubarrayProductLessThanK(nums, k)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "maxSubarrayLength({0}, {1}) = {2}")
    @MethodSource("subarrayLengthConstraintProvider")
    fun maxSubarrayLength(nums: IntArray, k: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.maxSubarrayLength(nums, k)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "subarraysWithKDistinct({0}) = {1}")
    @MethodSource("subarrayWithKDistinctProvider")
    fun subarraysWithKDistinct(nums: IntArray, k: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.subarraysWithKDistinct(nums, k)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "countSubarrays({0}, {1}, {2}) = {3}")
    @MethodSource("constrainedSubarrayProvider")
    fun countSubarrays(nums: IntArray, minK: Int, maxK: Int, expected: Long) {
        val actual = com.willmadison.leetcode.extensions.countSubarrays(nums, minK, maxK)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "countStudents({0}, {1}) = {2}")
    @MethodSource("studentSandwichProvider")
    fun countStudents(students: IntArray, sandwiches: IntArray, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.countStudents(students, sandwiches)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "timeRequiredToBuy({0}, {1}) = {2}")
    @MethodSource("ticketConstraintProvider")
    fun timeRequiredToBuy(tickets: IntArray, k: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.timeRequiredToBuy(tickets, k)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "deckRevealedIncreasing({0}) = {1}")
    @MethodSource("deckProvider")
    fun deckRevealedIncreasing(deck: IntArray, expected: IntArray) {
        val actual = com.willmadison.leetcode.extensions.deckRevealedIncreasing(deck)
        assertThat(actual.toList()).isEqualTo(expected.toList())
    }

    @ParameterizedTest(name = "trap({0}) = {1}")
    @MethodSource("waterTrapProvider")
    fun trap(heights: IntArray, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.trap(heights)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "maximalRectangle{0}) = {1}")
    @MethodSource("rectangleProvider")
    fun maximalRectangle(matrix: Array<CharArray>, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.maximalRectangle(matrix)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "islandPerimeter({0}) = {1}")
    @MethodSource("islandPerimeterProvider")
    fun islandPerimeter(grid: Array<IntArray>, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.islandPerimeter(grid)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun divideArray() {
        val actual = com.willmadison.leetcode.extensions.divideArray(intArrayOf(1, 3, 4, 8, 7, 9, 3, 5, 1), 2)
        val expected = arrayOf(intArrayOf(1, 1, 3), intArrayOf(3, 4, 5), intArrayOf(7, 8, 9))
        assertArrayEquals(expected, actual)
    }

    @ParameterizedTest(name = "numRescueBoats({0}, {1}) = {2}")
    @MethodSource("rescueBoatProvider")
    fun numRescueBoats(people: IntArray, maxWeight: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.numRescueBoats(people, maxWeight)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "findRelativeRanks({0}) = {1}")
    @MethodSource("relativeRankProvider")
    fun findRelativeRanks(scores: IntArray, expected: Array<String>) {
        val actual = com.willmadison.leetcode.extensions.findRelativeRanks(scores)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "maximumHappinessSum({0}, {1}) = {2}")
    @MethodSource("happinessProvider")
    fun maximumHappinessSum(happinesses: IntArray, k: Int, expected: Long) {
        val actual = com.willmadison.leetcode.extensions.maximumHappinessSum(happinesses, k)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "kthSmallestPrimeFaction({0}, {1}) = {2}")
    @MethodSource("numeratorDenominatorProvider")
    fun kthSmallestPrimeFaction(numbers: IntArray, k: Int, expected: IntArray) {
        val actual = com.willmadison.leetcode.extensions.kthSmallestPrimeFraction(numbers, k)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "kidsWithCandies({0}, {1}) = {2}")
    @MethodSource("candyman")
    fun kidsWithCandies(candies: IntArray, extraCandies: Int, expected: List<Boolean>) {
        val actual = com.willmadison.leetcode.extensions.kidsWithCandies(candies, extraCandies)
        assertThat(actual).isEqualTo(expected)
    }


    @ParameterizedTest(name = "mincostToHireWorkers({0}, {1}, {2}) = {3}")
    @MethodSource("workerCostProvider")
    fun mincostToHireWorkers(qualities: IntArray, minWages: IntArray, numWorkers: Int, expected: Double) {
        val actual = com.willmadison.leetcode.extensions.mincostToHireWorkers(qualities, minWages, numWorkers)
        assertThat(actual).isCloseTo(expected, Offset.offset(.00001))
    }

    @ParameterizedTest(name = "largestLocal({0}) = {1}")
    @MethodSource("largestLocalProvider")
    fun largestLocal(grid: Array<IntArray>, expected: Array<IntArray>) {
        val actual = com.willmadison.leetcode.extensions.largestLocal(grid)
        assertThat(actual.asIterable()).containsExactlyElementsOf(expected.asIterable())
    }

    @ParameterizedTest(name = "matrixScore({0}) = {1}")
    @MethodSource("matrixFlipScoreProvider")
    fun matrixScore(grid: Array<IntArray>, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.matrixScore(grid)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "beautifulSubsets({0}, {1}) = {2}")
    @MethodSource("beautifulSubsetProvider")
    fun beautifulSubsets(nums: IntArray, k: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.beautifulSubsets(nums, k)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "specialArray({0}) = {1}")
    @MethodSource("specialArrayProvider")
    fun specialArray(nums: IntArray, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.specialArray(nums)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "countTriplets({0}) = {1}")
    @MethodSource("tripletProvider")
    fun countTriplets(a: IntArray, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.countTriplets(a)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "singleNumber({0}) = {1}")
    @MethodSource("singleNumberProvider")
    fun singleNumber(nums: IntArray, expected: IntArray) {
        val actual = com.willmadison.leetcode.extensions.singleNumber(nums)
        assertThat(actual.asIterable()).containsExactlyInAnyOrderElementsOf(expected.asIterable())
    }

    @ParameterizedTest(name = "scoreOfString({0}) = {1}")
    @MethodSource("scoreStringProvider")
    fun scoreOfString(s: String, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.scoreOfString(s)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "isNStraightHand({0}, {1}) = {2}")
    @MethodSource("straightHandProvider")
    fun isNStraightHand(hand: IntArray, groupSize: Int, expected: Boolean) {
        val actual = com.willmadison.leetcode.extensions.isNStraightHand(hand, groupSize)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "checkSubarraySum({0}, {1}) = {2}")
    @MethodSource("subarraySumProvider")
    fun checkSubarraySum(nums: IntArray, k: Int, expected: Boolean) {
        val actual = com.willmadison.leetcode.extensions.checkSubarraySum(nums, k)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "subarraysDivByK({0}, {1}) = {2}")
    @MethodSource("subarrayDivByKProvider")
    fun subarraysDivByK(nums: IntArray, k: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.subarraysDivByK(nums, k)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "subarraysDivByK({0}, {1}) = {2}")
    @MethodSource("heightProvider")
    fun heightChecker(heights: IntArray, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.heightChecker(heights)
        assertThat(actual).isEqualTo(expected)
    }


    @ParameterizedTest(name = "relativeSortArray({0}, {1}) = {2}")
    @MethodSource("relativeSortArrayProvider")
    fun relativeSortArray(arr1: IntArray, arr2: IntArray, expected: IntArray) {
        val actual = com.willmadison.leetcode.extensions.relativeSortArray(arr1, arr2)
        assertThat(actual.asIterable()).containsExactlyElementsOf(expected.asIterable())
    }

    @ParameterizedTest(name = "minMovesToSeat({0}, {1}) = {2}")
    @MethodSource("studentSeatProvider")
    fun minMovesToSeat(seats: IntArray, students: IntArray, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.minMovesToSeat(seats, students)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "minIncrementForUnique({0}) = {1}")
    @MethodSource("minIncrementForUniqueProvider")
    fun minIncrementForUnique(nums: IntArray, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.minIncrementForUnique(nums)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "findMaximizedCapital({0}, {1}, {2}, {3}) = {4}")
    @MethodSource("maximizedCapitalProvider")
    fun findMaximizedCapital(k: Int, w: Int, profits: IntArray, capital: IntArray, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.findMaximizedCapital(k, w, profits, capital)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "minPatches({0}, {1}) = {2}")
    @MethodSource("minPatchesProvider")
    fun minPatches(nums: IntArray, n: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.minPatches(nums, n)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "maxProfitAssignment({0}, {1}, {2}) = {3}")
    @MethodSource("profitAssignmentProvider")
    fun maxProfitAssignment(difficulties: IntArray, profits: IntArray, workers: IntArray, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.maxProfitAssignment(difficulties, profits, workers)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "minDays({0}, {1}, {2}) = {3}")
    @MethodSource("minDaysProvider")
    fun minDays(bloomDay: IntArray, m: Int, k: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.minDays(bloomDay, m, k)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "maxDistance({0}, {1}) = {2}")
    @MethodSource("maxDistanceProvider")
    fun maxDistance(basketPositions: IntArray, numBalls: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.maxDistance(basketPositions, numBalls)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "maxSatisfied({0}, {1}, {2}) = {3}")
    @MethodSource("maxSatisfactionProvider")
    fun maxSatisfied(customers: IntArray, grumpy: IntArray, minutes: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.maxSatisfied(customers, grumpy, minutes)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "numberOfSubarrays({0}, {1}) = {2}")
    @MethodSource("niceSubarrayProvider")
    fun numberOfSubarrays(nums: IntArray, k: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.numberOfSubarrays(nums, k)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "longestSubarray({0}, {1}) = {2}")
    @MethodSource("longestSubarrayProvider")
    fun longestSubarray(nums: IntArray, limit: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.longestSubarrayB(nums, limit)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "minKBitFlips({0}, {1}) = {2}")
    @MethodSource("minKBitFlipProvider")
    fun minKBitFlips(nums: IntArray, k: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.minKBitFlips(nums, k)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "findCenter({0}) = {1}")
    @MethodSource("centerStarProvider")
    fun findCenter(edges: Array<IntArray>, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.findCenter(edges)
        assertThat(actual).isEqualTo(expected)
    }

   @ParameterizedTest(name = "intersect({0}, {1}) = {2}")
    @MethodSource("intersectProvider")
    fun intersect(nums1: IntArray, nums2: IntArray, expected: IntArray) {
        val actual = com.willmadison.leetcode.extensions.intersect(nums1, nums2)
        assertThat(actual.asIterable()).containsExactlyInAnyOrderElementsOf(expected.asIterable())
    }

}