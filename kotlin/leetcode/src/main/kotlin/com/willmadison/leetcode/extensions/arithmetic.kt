package com.willmadison.leetcode.extensions

import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

// https://leetcode.com/problems/add-binary/
fun addBinary(a: String, b: String): String {
    var carry = 0

    val aDigits = ArrayDeque<Char>()
    val bDigits = ArrayDeque<Char>()
    val resultDigits = ArrayDeque<Char>()

    for (c in a) {
        aDigits.add(c)
    }

    for (c in b) {
        bDigits.add(c)
    }

    val zero = '0'
    val one = '1'

    while (!aDigits.isEmpty() || !bDigits.isEmpty()) {
        var aDigit = '0'
        var bDigit = '0'

        if (!aDigits.isEmpty()) {
            aDigit = aDigits.removeLast()
        }

        if (!bDigits.isEmpty()) {
            bDigit = bDigits.removeLast()
        }

        var resultDigit: Int

        val addends = Pair(aDigit, bDigit)

        when (addends) {
            '1' to '1' -> {
                resultDigit = 0

                if (carry == 1) {
                    resultDigit = 1
                }

                carry = 1
            }

            '0' to '0' -> {
                resultDigit = 0

                if (carry == 1) {
                    resultDigit = 1
                }

                carry = 0
            }

            else -> {
                resultDigit = 1

                if (carry == 1) {
                    resultDigit = 0
                }
            }
        }

        when (resultDigit) {
            1 -> resultDigits.addFirst(one)
            0 -> resultDigits.addFirst(zero)
        }
    }

    if (carry == 1) {
        resultDigits.addFirst(one)
    }

    val resultSb = StringBuilder()

    for (c in resultDigits) {
        resultSb.append(c)
    }

    return resultSb.toString()
}

// https://leetcode.com/problems/evaluate-reverse-polish-notation/
fun evalRPN(tokens: Array<String>): Int {
    val stack = Stack<Int>()

    for (token in tokens) {
        when (token) {
            "+" -> {
                val a = stack.pop()
                val b = stack.pop()
                stack.push(a + b)
            }

            "-" -> {
                val a = stack.pop()
                val b = stack.pop()
                stack.push(b - a)
            }

            "*" -> {
                val a = stack.pop()
                val b = stack.pop()
                stack.push(a * b)
            }

            "/" -> {
                val a = stack.pop()
                val b = stack.pop()
                stack.push(b / a)
            }

            else -> stack.push(token.toInt())
        }
    }

    return stack.pop()
}

// https://leetcode.com/problems/palindrome-number/
fun isPalindrome(x: Int): Boolean {
    if (x < 0) {
        return false
    }

    var value = x

    val digits = mutableListOf<Int>()

    while (value > 0) {
        val digit = value % 10
        digits.add(digit)
        value /= 10
    }

    var start = 0
    var end = digits.size - 1

    while (start <= end) {
        if (digits[start] != digits[end]) {
            return false
        }

        start++
        end--
    }

    return true
}

fun pivotInteger(n: Int): Int {
    val sum = n * (n + 1) / 2
    val pivot = sqrt(sum.toDouble()).toInt()

    return if (pivot * pivot == sum) pivot else -1
}

fun confusingNumber(n: Int): Boolean {
    val invalidDigits = setOf(2, 3, 4, 5, 7)
    val newDigitsBySource = mapOf(0 to 0, 1 to 1, 6 to 9, 8 to 8, 9 to 6)

    val digits = n.digits()

    if (digits.intersect(invalidDigits).isNotEmpty()) {
        return false
    }

    val rotatedDigits = mutableListOf<Int>()

    for (d in digits.reversed()) {
        rotatedDigits.add(newDigitsBySource[d]!!)
    }

    return rotatedDigits != digits
}

private fun Int.digits(): Collection<Int> {
    val digits = mutableListOf<Int>()

    var v = this

    while (v > 0) {
        digits.add(v % 10)
        v /= 10
    }

    return digits.reversed()
}

// https://leetcode.com/problems/cheapest-flights-within-k-stops/description/
fun findCheapestPrice(n: Int, flights: Array<IntArray>, src: Int, dst: Int, k: Int): Int {
    // Bellman Ford Algorithm
    var distancesFromSource = IntArray(n) { Int.MAX_VALUE }
    distancesFromSource[src] = 0

    for (i in 0..k) {
        val temp = distancesFromSource.copyOf(n)

        for (flight in flights) {
            val from = flight[0]
            val to = flight[1]
            val cost = flight[2]

            if (distancesFromSource[from] != Int.MAX_VALUE) {
                temp[to] = Integer.min(temp[to], distancesFromSource[from] + cost)
            }
        }

        distancesFromSource = temp
    }

    return if (distancesFromSource[dst] == Int.MAX_VALUE) -1 else distancesFromSource[dst]
}

// https://leetcode.com/problems/power-of-two/description/
fun isPowerOfTwo(n: Int): Boolean {
    if (n == 0) return false

    var value = n

    while (value % 2 == 0) value /= 2

    return value == 1
}

// https://leetcode.com/problems/baseball-game/description/
fun calPoints(operations: Array<String>): Int {
    val record = mutableListOf<Int>()

    for (operation in operations) {
        when (operation) {
            "+" -> record.add(record[record.size - 1] + record[record.size - 2])
            "D" -> record.add(record[record.size - 1] * 2)
            "C" -> record.removeLast()
            else -> record.add(operation.toInt())
        }
    }

    return record.sum()
}

fun romanToInt(s: String): Int {
    val prefixedNumerals = mapOf(
        "IV" to 4,
        "IX" to 9,
        "XL" to 40,
        "XC" to 90,
        "CD" to 400,
        "CM" to 900,
    )

    val valuesByCharacter = mapOf(
        'I' to 1,
        'V' to 5,
        'X' to 10,
        'L' to 50,
        'C' to 100,
        'D' to 500,
        'M' to 1000,
    )

    var value = 0

    var romanRepresentation = s

    while (romanRepresentation.isNotEmpty()) {
        if (romanRepresentation.length >= 2) {
            val characters = romanRepresentation.take(2)

            if (prefixedNumerals.containsKey(characters)) {
                value += prefixedNumerals[characters]!!
                romanRepresentation = romanRepresentation.substring(2)
            } else {
                value += valuesByCharacter[characters[0]]!!
                romanRepresentation = romanRepresentation.substring(1)
            }
        } else {
            val characters = romanRepresentation.take(1)

            value += valuesByCharacter[characters[0]]!!

            romanRepresentation = romanRepresentation.substring(1)
        }
    }

    return value
}

// https://leetcode.com/problems/smallest-even-multiple/
fun smallestEvenMultiple(n: Int) = if (n % 2 == 0) n else 2 * n

// https://leetcode.com/problems/excel-sheet-column-number/
fun titleToNumber(columnTitle: String): Int {
    var answer = 0

    val deque = ArrayDeque<Char>()

    for (c in columnTitle) {
        deque.add(c)
    }

    var exponent = 0

    while (deque.isNotEmpty()) {
        val character = deque.removeLast()
        answer += (character - 'A' + 1) * 26.toDouble().pow(exponent).toInt()
        exponent++
    }

    return answer
}

fun sum(num1: Int, num2: Int) = num1 + num2

private val cache = mutableMapOf(
    0 to 0,
    1 to 1,
    2 to 1,
)

fun tribonacci(n: Int): Int {
    if (cache.containsKey(n)) return cache[n]!!

    val ans = tribonacci(n-1) + tribonacci(n-2) + tribonacci(n-3)

    cache[n] = ans

    return ans
}

fun minXOROperations(nums: IntArray, k: Int): Int {
    var result = 0

    for (n in nums) {
        result = result xor n
    }

    return Integer.bitCount(result xor k)
}

