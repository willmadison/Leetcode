package com.willmadison.leetcode.extensions

import com.willmadison.leetcode.Node
import com.willmadison.leetcode.Solution
import java.util.*
import kotlin.math.max


// https://leetcode.com/problems/clone-graph
private val cache = mutableMapOf<Int, Node?>()

fun Solution.cloneGraph(node: Node?): Node? {
    if (node == null) {
        return null
    }

    if (cache.containsKey(node.`val`)) {
        return cache[node.`val`]
    }

    val copy = Node(node.`val`)
    cache[node.`val`] = copy

    for (neighbor in node.children) {
        copy.children.add(cloneGraph(neighbor))
    }

    return copy
}

fun maximumValueSum(nums: IntArray, k: Int, edges: Array<IntArray>): Long {
    val memo = Array(nums.size) { LongArray(2) }
    
    for (row in memo) {
        Arrays.fill(row, -1)
    }
    
    return maxSumOfNodes(0, 1, nums, k, memo)
}

fun maxSumOfNodes(index: Int, isEven: Int, nums: IntArray, k: Int, memo: Array<LongArray>): Long {
    if (index == nums.size) {
        // If the operation is performed on an odd number of elements return
        // INT_MIN
        return if (isEven == 1) 0 else Int.MIN_VALUE.toLong()
    }

    if (memo[index][isEven] != -1L) {
        return memo[index][isEven]
    }

    val noXorDone = nums[index] + maxSumOfNodes(index + 1, isEven, nums, k, memo)

    val xorDone = (nums[index] xor k) +
            maxSumOfNodes(index + 1, isEven xor 1, nums, k, memo)

    memo[index][isEven] = maxOf(xorDone, noXorDone)

    return memo[index][isEven]
}
