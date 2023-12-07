package com.willmadison.leetcode

import org.junit.jupiter.api.AfterEach
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
        fun magazineSourceProvider(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of("a", "b", false),
                    Arguments.of("aa", "ab", false),
                    Arguments.of("aa", "aab", true),
            )
        }
    }

    @BeforeEach
    fun setUp() {
        solution = Solution()
    }

    @Test
    @Disabled("disabled for now")
    fun firstBadVersion() {
        System.setProperty("FIRST_BAD_VERSION", "4")

        val expected = 4

        val actual = solution.firstBadVersion(10)

        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "canConstruct({0}, {1}) = {2}")
    @MethodSource("magazineSourceProvider")
    fun canConstruct(note: String, magazine: String, expected: Boolean) {
        val actual = solution.canConstruct(note, magazine)
        assertEquals(expected, actual)
    }

    @AfterEach
    fun tearDown() {
        System.clearProperty("FIRST_BAD_VERSION")
    }
}