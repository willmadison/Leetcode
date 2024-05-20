package com.willmadison.leetcode

import kotlin.math.abs

data class Location(val row: Int, val col: Int) {
    fun neighbors() = setOf(
        Location(this.row - 1, this.col),
        Location(this.row, this.col + 1),
        Location(this.row + 1, this.col),
        Location(this.row, this.col - 1),
    )

    fun isValid(rows: Int, columns: Int) = this.row in 0..<rows && this.col in 0..<columns

    fun manhattanDistance(other: Location) = abs(this.row - other.row) + abs(this.col - other.col)
}