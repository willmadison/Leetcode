package com.willmadison.leetcode

class MyCalendarTwo {
    private val bookings = mutableSetOf<IntRange>()

    fun book(start: Int, end: Int): Boolean {
        val range = start..<end
        val overlappingBookings = bookings.count { it.overlaps(range) }

        if (overlappingBookings < 2) {
            bookings.add(range)
            return true
        }

        return false
    }

    private fun IntRange.overlaps(other: IntRange): Boolean {
        return this.first <= other.first && other.first <= this.last ||
                other.first <= this.first && this.first <= other.last
    }
}