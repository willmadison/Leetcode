package com.willmadison.leetcode

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

class SolutionTests {

    private lateinit var solution: Solution

    @BeforeEach
    fun setUp() {
        solution = Solution()
    }

    @AfterEach
    fun tearDown() {
        System.clearProperty("FIRST_BAD_VERSION")
    }
}