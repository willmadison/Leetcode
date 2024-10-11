package com.willmadison.leetcode

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class MyCalendarTwoTest {

    companion object {

        @JvmStatic
        fun bookingsProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    listOf(
                        10..20 to true,
                        50..60 to true,
                        10..40 to true,
                        5..15 to false,
                        5..10 to true,
                        25..55 to true,
                    ),
                ),
            )
        }
    }

    private lateinit var calendar: MyCalendarTwo

    @BeforeEach
    fun setUp() {
        calendar = MyCalendarTwo()
    }

    @ParameterizedTest(name = "itAllowsDoubleButNotTripleBookings({0})")
    @MethodSource("bookingsProvider")
    fun itAllowsDoubleButNotTripleBookings(bookings: Collection<Pair<IntRange, Boolean>>) {
        for (booking in bookings) {
            val (range, expected) = booking
            val actual = calendar.book(range.first, range.last)
            assertThat(actual).isEqualTo(expected)
        }
    }
}