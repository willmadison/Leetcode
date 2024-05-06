package com.willmadison.leetcode.extensions

import com.willmadison.leetcode.ListNode
import java.util.*

// https://leetcode.com/problems/middle-of-the-linked-list/

fun middleNode(head: ListNode?): ListNode? {
    var slow = head
    var fast = head

    while (fast?.next != null) {
        fast = fast.next

        if (fast?.next != null) {
            fast = fast?.next
        }

        slow = slow?.next
    }

    return slow
}


// https://leetcode.com/problems/reverse-linked-list/
fun reverseList(head: ListNode?): ListNode? {
    val stack = Stack<ListNode?>()

    if (head?.next == null) {
        return head
    }

    var current = head

    while (current != null) {
        stack.push(current)
        current = current.next
    }

    val newHead = stack.pop()

    current = newHead

    while (!stack.isEmpty()) {
        current?.next = stack.pop()
        current = current?.next
    }

    current?.next = null

    return newHead
}


fun getIntersectionNode(headA: ListNode?, headB: ListNode?): ListNode? {
    val aNodes = ArrayDeque<ListNode?>()
    val bNodes = ArrayDeque<ListNode?>()

    var aHead = headA
    var bHead = headB

    while (aHead != null) {
        aNodes.add(aHead)
        aHead = aHead.next
    }

    while (bHead != null) {
        bNodes.add(bHead)
        bHead = bHead.next
    }

    var intersection: ListNode? = null

    while (aNodes.peekLast() == bNodes.peekLast()) {
        intersection = aNodes.pollLast()
        bNodes.pollLast()
    }

    return intersection
}

fun reorderList(head: ListNode?) {
    var current = head

    val deque = ArrayDeque<ListNode>()
    val reorderedNodes = mutableListOf<ListNode>()

    while (current != null) {
        deque.add(current)
        current = current.next
    }

    while (deque.isNotEmpty()) {
        reorderedNodes.add(deque.removeFirst())

        if (deque.isNotEmpty()) {
            reorderedNodes.add(deque.removeLast())
        }
    }

    for (i in reorderedNodes.indices) {
        if (i == reorderedNodes.size - 1) {
            reorderedNodes[i].next = null
        } else {
            reorderedNodes[i].next = reorderedNodes[i + 1]
        }
    }
}

fun removeZeroSumSublists(head: ListNode?): ListNode? {
    val dummy = ListNode(0, head)
    var current: ListNode? = dummy

    var prefixSum = 0

    val prefixSumsByNode = mutableMapOf<Int, ListNode>()
    prefixSumsByNode[0] = dummy

    while (current != null) {
        prefixSum += current.`val`
        prefixSumsByNode[prefixSum] = current
        current = current.next
    }

    prefixSum = 0
    current = dummy

    while (current != null) {
        prefixSum += current.`val`
        current.next = prefixSumsByNode[prefixSum]!!.next
        current = current.next
    }

    return dummy.next
}

fun mergeKLists(lists: Array<ListNode?>): ListNode? {
    var head: ListNode? = null
    var last: ListNode? = null

    val valueComparator = compareBy<ListNode?> { it?.`val` }
    val pq = PriorityQueue<ListNode?>(valueComparator)

    for (list in lists) {
        pq.add(list)
    }

    while (pq.isNotEmpty()) {
        var current = pq.remove()

        if (head == null) {
            head = current
        }

        if (last != null) {
            last.next = current
        }

        last = current

        current = current?.next

        if (current != null) {
            pq.add(current)
        }
    }

    return head
}


// https://leetcode.com/problems/remove-nth-node-from-end-of-list/
fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
    val length = head.length()

    if (length == 0) {
        return null
    }

    val removalIndex = length - n

    if (removalIndex == 0) {
        return head?.next
    }

    val previousNode = head.getNodeAt(removalIndex - 1)
    previousNode?.next = previousNode?.next?.next

    return head
}

// https://leetcode.com/problems/linked-list-cycle/description/
fun hasCycle(head: ListNode?): Boolean {
    var slow = head
    var fast = head

    while (fast?.next != null) {
        fast = fast.next?.next
        slow = slow?.next

        if (fast == slow) {
            return true
        }
    }

    return false
}

// https://leetcode.com/problems/linked-list-cycle-ii
fun detectCycle(head: ListNode?): ListNode? {
    val cycleNode = findCycleNode(head) ?: return null

    var slow = head
    var other = cycleNode

    while (slow != other) {
        slow = slow!!.next
        other = other.next!!
    }

    return slow
}

private fun findCycleNode(head: ListNode?): ListNode? {
    var slow = head
    var fast = head

    while (fast?.next != null) {
        fast = fast.next?.next
        slow = slow?.next

        if (fast == slow) {
            return fast
        }
    }

    return null
}

private fun ListNode?.length(): Int {
    if (this == null) {
        return 0
    }

    var length = 0

    var current = this

    while (current != null) {
        current = current.next
        length++
    }

    return length
}

private fun ListNode?.getNodeAt(index: Int): ListNode? {
    if (index == 0) {
        return this
    }

    return this?.next.getNodeAt(index - 1)
}

fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
    var a = list1
    var b = list2

    val dummy = ListNode(Int.MIN_VALUE)
    var current: ListNode? = dummy

    while (a != null || b != null) {
        var next: ListNode?
        if (a == null) {
            next = b
            b = b?.next
        } else if (b == null) {
            next = a
            a = a.next
        } else {
            var smaller: ListNode?

            if (a.`val` < b.`val`) {
                smaller = a
                a = a.next
            } else {
                smaller = b
                b = b.next
            }

            next = smaller
        }

        current?.next = next
        current = next
    }

    return dummy.next
}

fun removeNodes(head: ListNode?): ListNode? {
    val stack = ArrayDeque<ListNode>()
    var current = head

    if (current == null) return current

    while (current != null) {
        if (stack.isEmpty()) {
            stack.push(current)
        } else {
            while (stack.isNotEmpty() && stack.peek().`val` < current.`val`) {
                stack.pop()
            }
            stack.push(current)
        }

        current = current.next
    }

    var dummy = ListNode(Int.MIN_VALUE)

    while (stack.isNotEmpty()) {
        val node = stack.pop()

        if (dummy.next == null) {
            node.next = null
            dummy.next = node
        } else {
            node.next = dummy.next
            dummy.next = node
        }
    }

    return dummy.next
}

fun main(args: Array<String>) {
    val head = ListNode(1, ListNode(1, ListNode(1, ListNode(1))))
    removeNodes(head)
}