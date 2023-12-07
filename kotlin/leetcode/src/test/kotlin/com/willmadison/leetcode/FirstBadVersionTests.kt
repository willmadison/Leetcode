package com.willmadison.leetcode

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FirstBadVersionTests {

    private lateinit var solution: Solution

    @BeforeEach
    fun setUp() {
        solution = Solution()
    }

    @Test
    fun firstBadVersion() {
        System.setProperty("FIRST_BAD_VERSION", "4")

        val expected = 4

        val actual = solution.firstBadVersion(10)

        assertEquals(expected, actual)
    }

    @AfterEach
    fun tearDown() {
        System.clearProperty("FIRST_BAD_VERSION")
    }
}