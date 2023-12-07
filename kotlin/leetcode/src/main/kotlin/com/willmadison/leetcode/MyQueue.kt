package com.willmadison.leetcode

import java.util.*

// https://leetcode.com/problems/implement-queue-using-stacks
class MyQueue {

    private var primary  = Stack<Int>()
    private var secondary  = Stack<Int>()

    fun push(value: Int) {
        if (!empty()) {
            while (!primary.isEmpty()) {
                secondary.push(primary.pop())
            }
        }

        primary.push(value)

        if (!secondary.isEmpty()) {
            while (!secondary.isEmpty()) {
                primary.push(secondary.pop())
            }
        }
    }

    fun pop(): Int {
        return primary.pop()
    }

    fun empty(): Boolean {
        return primary.isEmpty() && secondary.isEmpty()
    }

    fun peek(): Int {
        return primary.peek()
    }

}
