package com.willmadison.leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class Solution {

    public final TreeNode getTargetCopy(final TreeNode original, final TreeNode cloned, final TreeNode target) {
        Queue<TreeNode> queue = new LinkedList<>();

        queue.add(cloned);

        while (!queue.isEmpty()) {
            TreeNode current = queue.remove();

            if (current.getValue() == target.getValue()) {
                return current;
            }

            if (current.getLeft() != null) {
                queue.add(current.getLeft());
            }

            if (current.getRight() != null) {
                queue.add(current.getRight());
            }
        }

        return null;
    }
}
