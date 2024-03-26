package com.willmadison.leetcode.extensions

import com.willmadison.leetcode.Node
import com.willmadison.leetcode.Solution

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