package com.willmadison.leetcode

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SolutionTests {

    @Test
    fun `It should fetch a copy of a target node from a cloned tree`() {
        val three = TreeNode(3)
        val two = TreeNode(2, left = three)
        val original = TreeNode(1, left = two)
        val cloned = original.copy()
        val target = TreeNode(2)
        target.left = TreeNode(3)

        val solution = Solution()

        val found = solution.getTargetCopy(original, cloned, target)

        assertThat(found).isEqualTo(target)
    }
}