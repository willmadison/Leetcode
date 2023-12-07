package com.willmadison.leetcode

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MyQueueTests {

    lateinit var myQueue: MyQueue
    @BeforeEach
    fun setUp() {
        myQueue = MyQueue()
    }

    @Test
    fun empty() {
        assertTrue(myQueue.empty())

        val one = MyQueue()
        one.push(1)
        assertFalse(one.empty())
    }

    @Test
    fun pop() {
        myQueue.push(1)
        myQueue.push(2)

        var expected = 1

        assertEquals(expected, myQueue.pop())

        expected = 2
        assertEquals(expected, myQueue.pop())

        assertTrue(myQueue.empty())
    }

    @Test
    fun peek() {
        myQueue.push(1)
        myQueue.push(2)
        myQueue.push(3)

        val expected = 1

        assertEquals(expected, myQueue.peek())
    }
}