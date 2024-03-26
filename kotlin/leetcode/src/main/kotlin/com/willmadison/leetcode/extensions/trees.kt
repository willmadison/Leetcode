package com.willmadison.leetcode.extensions

import com.willmadison.leetcode.Node
import com.willmadison.leetcode.Solution
import com.willmadison.leetcode.TreeNode
import java.util.ArrayDeque
import kotlin.math.max

private var maxHeight = 0

// https://leetcode.com/problems/diameter-of-binary-tree/
fun diameterOfBinaryTree(root: TreeNode?): Int {
    maxHeight = 1
    calculateHeight(root)
    return maxHeight - 1
}

private fun calculateHeight(node: TreeNode?): Int {
    if (node == null) {
        return 0
    }

    val leftHeight = calculateHeight(node.left)
    val rightHeight = calculateHeight(node.right)
    maxHeight = Integer.max(maxHeight, leftHeight + rightHeight + 1)

    return Integer.max(leftHeight, rightHeight) + 1
}

// https://leetcode.com/problems/maximum-depth-of-binary-tree/
fun Solution.maxDepth(root: TreeNode?): Int {
    if (root == null) {
        return 0
    }

    return max(maxDepth(root.left), maxDepth(root.right)) + 1
}

// https://leetcode.com/problems/binary-tree-level-order-traversal/description/
fun levelOrder(root: TreeNode?): List<List<Int>> {
    val result = mutableListOf<List<Int>>()

    var level: List<TreeNode> = if (root != null) mutableListOf(root) else emptyList()

    while (level.isNotEmpty()) {
        result.add(level.map { it.`val` })
        level = level.flatMap { listOf(it.left, it.right) }.filterNotNull()
    }

    return result
}

// https://leetcode.com/problems/range-sum-of-bst
fun Solution.rangeSumBST(root: TreeNode?, low: Int, high: Int): Int {
    if (root == null) {
        return 0
    }

    val range = IntRange(low, high)

    if (range.contains(root.`val`)) {
        return root.`val` + rangeSumBST(root.left, low, high) + rangeSumBST(root.right, low, high)
    }

    return rangeSumBST(root.left, low, high) + rangeSumBST(root.right, low, high)
}


// https://leetcode.com/problems/path-sum/description/
fun Solution.hasPathSum(root: TreeNode?, targetSum: Int): Boolean {
    if (root == null) {
        return false
    }

    if (root.left == null && root.right == null) {
        return targetSum == root.`val`
    }

    var leftHasPathSum = false
    var rightHasPathSum = false

    if (root.left != null) {
        leftHasPathSum = hasPathSum(root.left, targetSum - root.`val`)
    }

    if (!leftHasPathSum && root.right != null) {
        rightHasPathSum = hasPathSum(root.right, targetSum - root.`val`)
    }

    return leftHasPathSum || rightHasPathSum
}

// https://leetcode.com/problems/leaf-similar-trees/
fun leafSimilar(root1: TreeNode?, root2: TreeNode?): Boolean {
    val leftLeaves = root1?.leaves()
    val rightLeaves = root2?.leaves()

    return leftLeaves?.size == rightLeaves?.size && leftLeaves?.filterIndexed { i, n -> rightLeaves?.elementAt(i)?.`val` != n.`val` }
        ?.isEmpty() == true
}

private fun TreeNode?.leaves(): Collection<TreeNode> {
    val leaves = mutableListOf<TreeNode>()

    fun doFindLeaves(node: TreeNode) {
        if (node.isLeaf()) {
            leaves.add(node)
        } else {
            if (node.left != null) {
                doFindLeaves(node.left!!)
            }

            if (node.right != null) {
                doFindLeaves(node.right!!)
            }
        }
    }

    if (this != null) {
        doFindLeaves(this)
    }

    return leaves
}

private fun TreeNode?.isLeaf() = this != null && this.left == null && this.right == null


data class NodeLevel(val node: TreeNode, val level: Int = 0)

fun isEvenOddTree(root: TreeNode?): Boolean {
    if (root == null) {
        return false
    }

    val nodeValuesByLevel = mutableMapOf<Int, MutableCollection<Int>>()

    val q = ArrayDeque<NodeLevel>()

    q.add(NodeLevel(root))

    while (q.isNotEmpty()) {
        val nodeLevel = q.remove()
        val node = nodeLevel.node

        val values = nodeValuesByLevel.getOrPut(nodeLevel.level) { emptyList<Int>().toMutableList() }
        values.add(node.`val`)

        if (node.left != null) {
            q.add(NodeLevel(node.left!!, nodeLevel.level + 1))
        }

        if (node.right != null) {
            q.add(NodeLevel(node.right!!, nodeLevel.level + 1))
        }
    }

    for ((level, values) in nodeValuesByLevel) {
        if (level % 2 == 0) {
            if (!values.isSortedAscending() || !values.areAllOdd()) {
                return false
            }
        } else {
            if (!values.isSortedDescending() || !values.areAllEven()) {
                return false
            }

        }
    }


    return true
}

private fun MutableCollection<Int>.isSortedAscending(): Boolean {
    if (this.isEmpty()) {
        return false
    }

    var last = this.first()

    for ((i, value) in this.withIndex()) {
        if (i == 0) {
            continue
        }

        if (value <= last) {
            return false
        }

        last = value
    }

    return true
}

private fun MutableCollection<Int>.isSortedDescending(): Boolean {
    if (this.isEmpty()) {
        return false
    }

    var last = this.first()

    for ((i, value) in this.withIndex()) {
        if (i == 0) {
            continue
        }

        if (value >= last) {
            return false
        }

        last = value
    }

    return true
}

private fun MutableCollection<Int>.areAllEven() = this.all { it % 2 == 0 }

private fun MutableCollection<Int>.areAllOdd() = this.all { it % 2 != 0 }

private var maxDepth = -1
private var bottomMostValue = Int.MIN_VALUE
fun Solution.findBottomLeftValue(root: TreeNode?, depth: Int = 0): Int {
    if (depth > maxDepth) {
        bottomMostValue = root?.`val`!!
        maxDepth = depth
    }

    if (root?.left != null) {
        findBottomLeftValue(root.left, depth + 1)
    }

    if (root?.right != null) {
        findBottomLeftValue(root.right, depth + 1)
    }

    return bottomMostValue
}

fun Solution.isSameTree(p: TreeNode?, q: TreeNode?): Boolean {
    if (p == null && q == null) {
        return true
    }

    if (p?.`val` != q?.`val`) {
        return false
    }

    return isSameTree(p?.left, q?.left) && isSameTree(p?.right, q?.right)
}

fun Solution.preorder(root: Node?): List<Int> {
    if (root == null) {
        return emptyList()
    }

    val values = mutableListOf<Int>()

    values.add(root.`val`)

    for (child in root.children) {
        values.addAll(preorder(child))
    }

    return values
}

// https://leetcode.com/problems/amount-of-time-for-binary-tree-to-be-infected
@Suppress("DuplicatedCode")
fun amountOfTime(root: TreeNode?, start: Int): Int {
    var initialInfectedNode: TreeNode? = null

    val adjacentNodesByNode = mutableMapOf<TreeNode, MutableCollection<TreeNode>>()

    val queue = ArrayDeque<TreeNode>()

    if (root != null) {
        queue.add(root)
    }

    var totalNumberOfNodes = 0

    while (queue.isNotEmpty()) {
        val node = queue.pop()

        totalNumberOfNodes++

        if (node.`val` == start) {
            initialInfectedNode = node
        }

        val adjacents = adjacentNodesByNode.getOrDefault(node, mutableListOf())

        if (node.left != null) {
            queue.add(node.left!!)
            adjacents.add(node.left!!)

            val leftAdjacents = adjacentNodesByNode.getOrDefault(node.left, mutableListOf())
            leftAdjacents.add(node)
            adjacentNodesByNode[node.left!!] = leftAdjacents
        }

        if (node.right != null) {
            queue.add(node.right!!)
            adjacents.add(node.right!!)

            val rightAdjacents = adjacentNodesByNode.getOrDefault(node.right, mutableListOf())
            rightAdjacents.add(node)
            adjacentNodesByNode[node.right!!] = rightAdjacents
        }

        adjacentNodesByNode[node] = adjacents
    }

    val infectedNodes = mutableSetOf(initialInfectedNode!!)

    val toProcess = mutableListOf(initialInfectedNode)

    var minutesTranspired = 0

    while (infectedNodes.size != totalNumberOfNodes) {
        val nodes = toProcess.take(toProcess.size)
        toProcess.removeAll(nodes)

        for (infectedNode in nodes) {
            val adjacents = adjacentNodesByNode[infectedNode]!!

            for (adjacent in adjacents) {
                if (!infectedNodes.contains(adjacent)) {
                    infectedNodes.add(adjacent)

                    val hasNonInfectedAdjacents =
                        adjacentNodesByNode[adjacent]!!.any { !infectedNodes.contains(it) }

                    if (hasNonInfectedAdjacents) {
                        toProcess.add(adjacent)
                    }
                }
            }
        }

        minutesTranspired++
    }

    return minutesTranspired
}

fun Solution.maxAncestorDiff(root: TreeNode?, min: Int = root!!.`val`, max: Int = root!!.`val`): Int =
    if (root == null) max - min
    else listOf(root.left, root.right).maxOf {
        maxAncestorDiff(it, minOf(root.`val`, min), maxOf(root.`val`, max))
    }
