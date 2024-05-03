package com.willmadison.leetcode

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RandomizedSetTests {

    private lateinit var randomizedSet: RandomizedSet

    @BeforeEach
    fun setUp() {
        randomizedSet = RandomizedSet()
    }

    @Test
    fun insert() {
        assertTrue(randomizedSet.insert(1))
        assertFalse(randomizedSet.insert(1))
    }

    @Test
    fun remove() {
        randomizedSet.insert(1)
        assertTrue(randomizedSet.remove(1))
        assertFalse(randomizedSet.remove(2))
    }

    @Test
    fun getRandom() {
        randomizedSet.insert(1)
        randomizedSet.insert(2)

        assertThat(randomizedSet.getRandom()).isGreaterThan(0)

        randomizedSet.remove(2)

        assertEquals(1, randomizedSet.getRandom())
    }
}