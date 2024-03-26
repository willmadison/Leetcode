package com.willmadison.leetcode

import kotlin.math.sqrt

data class Point(var x: Int, var y: Int) {
    fun distanceFrom(other: Point = Point(0, 0)) =
        sqrt(((other.x - this.x) * (other.x - this.x) + (other.y - this.y) * (other.y - this.y)).toDouble())
}