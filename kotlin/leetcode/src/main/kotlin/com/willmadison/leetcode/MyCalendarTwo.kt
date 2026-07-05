package com.willmadison.leetcode

class MyCalendarTwo {
    private val bookings = mutableListOf<IntRange>()
    private val doubleBookings = mutableListOf<IntRange>()

    fun book(start: Int, end: Int): Boolean {
        val range = start..<end

        // Rejecting only when the new booking lands on an already
        // double-booked region, which would create a triple booking.
        if (doubleBookings.any { it.overlaps(range) }) {
            return false
        }

        // Recording the overlap with every existing booking as a new
        // double-booked region.
        for (booking in bookings) {
            val overlap = booking.intersect(range)
            if (overlap != null) {
                doubleBookings.add(overlap)
            }
        }

        bookings.add(range)
        return true
    }

    private fun IntRange.overlaps(other: IntRange): Boolean {
        return this.first <= other.last && other.first <= this.last
    }

    private fun IntRange.intersect(other: IntRange): IntRange? {
        val start = maxOf(this.first, other.first)
        val end = minOf(this.last, other.last)
        return if (start <= end) start..end else null
    }
}