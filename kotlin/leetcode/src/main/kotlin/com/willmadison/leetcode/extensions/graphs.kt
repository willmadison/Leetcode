package com.willmadison.leetcode.extensions

import com.willmadison.leetcode.Node
import com.willmadison.leetcode.Solution
import java.util.*


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

fun findCenter(edges: Array<IntArray>): Int {
    val adjacencyList = mutableMapOf<Int, MutableSet<Int>>()

    for (edge in edges) {
        var adjacents = adjacencyList.getOrPut(edge[0]) { mutableSetOf() }
        adjacents.add(edge[1])

        adjacents = adjacencyList.getOrPut(edge[1]) { mutableSetOf() }
        adjacents.add(edge[0])
    }

    val n = adjacencyList.keys.max()

    return adjacencyList.entries.first() { it.value.size == n-1 }.key
}

fun maximumImportance(n: Int, roads: Array<IntArray>): Long {
    val adjacencyList = mutableMapOf<Int, MutableSet<Int>>()

    for (road in roads) {
        var adjacents = adjacencyList.getOrPut(road[0]) { mutableSetOf() }
        adjacents.add(road[1])

        adjacents = adjacencyList.getOrPut(road[1]) { mutableSetOf() }
        adjacents.add(road[0])
    }

    val compareByDensity: Comparator<Map.Entry<Int, MutableSet<Int>>> = compareBy<Map.Entry<Int, MutableSet<Int>>> { it.value.size }.reversed()

    val pq = PriorityQueue(n, compareByDensity)

    for (entry in adjacencyList) {
        pq.add(entry)
    }

    var value = n

    val scoresByNode = mutableMapOf<Int, Int>()

    while (pq.isNotEmpty()) {
        val road = pq.remove()
        scoresByNode[road.key] = value--
    }

    var maxImportance = 0L

    for (road in roads) {
        maxImportance += scoresByNode[road[0]]!! + scoresByNode[road[1]]!!
    }

    return maxImportance
}

fun getAncestors(n: Int, edges: Array<IntArray>): List<List<Int>> {
    val adjacencyList = mutableListOf<MutableList<Int>>()

    for (i in 0 until n) {
        adjacencyList.add(mutableListOf())
    }

    for (edge in edges) {
        val from = edge[0]
        val to = edge[1]

        adjacencyList[to].add(from)
    }

    val ancestry = mutableListOf<MutableList<Int>>()

    for (i in 0 until n) {
        val ancestors = mutableListOf<Int>()
        val visited = mutableSetOf<Int>()

        findChildren(i, adjacencyList, visited)

        for (node in 0 until n) {
            if (node == i) continue
            if (visited.contains(node)) ancestors.add(node)
        }

        ancestry.add(ancestors)
    }

    return ancestry
}

fun findChildren(i: Int, adjacencyList: MutableList<MutableList<Int>>, visited: MutableSet<Int>) {
    visited.add(i)

    for (neighbor in adjacencyList[i]) {
        if (!visited.contains(neighbor)) {
            findChildren(neighbor, adjacencyList, visited)
        }
    }
}

fun maxNumEdgesToRemove(n: Int, edges: Array<IntArray>): Int {
    val Alice = UnionFind(n)
    val Bob = UnionFind(n)

    var edgesRequired = 0

    for (edge in edges) {
        if (edge[0] == 3) {
            edgesRequired += (Alice.performUnion(edge[1], edge[2]) or Bob.performUnion(edge[1], edge[2]))
        }
    }

    for (edge in edges) {
        if (edge[0] == 1) {
            edgesRequired += Alice.performUnion(edge[1], edge[2])
        } else if (edge[0] == 2) {
            edgesRequired += Bob.performUnion(edge[1], edge[2])
        }
    }

    if (Alice.isConnected && Bob.isConnected) {
        return edges.size - edgesRequired
    }

    return -1
}

private class UnionFind(
    var components: Int
) {
    var representative: IntArray
    var componentSize: IntArray

    init {
        components = components
        representative = IntArray(components + 1)
        componentSize = IntArray(components + 1)

        for (i in 0..components) {
            representative[i] = i
            componentSize[i] = 1
        }
    }

    fun findRepresentative(x: Int): Int {
        if (representative[x] == x) {
            return x
        }

        return findRepresentative(representative[x]).also { representative[x] = it }
    }

    fun performUnion(x: Int, y: Int): Int {
        var x = x
        var y = y
        x = findRepresentative(x)
        y = findRepresentative(y)

        if (x == y) {
            return 0
        }

        if (componentSize[x] > componentSize[y]) {
            componentSize[x] += componentSize[y]
            representative[y] = x
        } else {
            componentSize[y] += componentSize[x]
            representative[x] = y
        }

        components--
        return 1
    }

    val isConnected: Boolean
        get() = components == 1
}
