package com.willmadison.leetcode.extensions

import com.willmadison.leetcode.Location
import com.willmadison.leetcode.Point
import java.util.*
import kotlin.math.abs

fun longestPalindrome(value: String): Int {
    var length = 0
    val occurrencesByCharacter = value.groupingBy { it }.eachCount().toMutableMap()

    var mostFrequentCharacterEntry =
        occurrencesByCharacter.filter { it.value == occurrencesByCharacter.values.max() }

    while (mostFrequentCharacterEntry.all { it.value > 1 }) {
        for (entry in mostFrequentCharacterEntry) {
            if (entry.value % 2 == 0) {
                length += entry.value
                occurrencesByCharacter[entry.key] = 0
            } else {
                length += entry.value - 1
                occurrencesByCharacter[entry.key] = 1
            }

            mostFrequentCharacterEntry =
                occurrencesByCharacter.filter { it.value == occurrencesByCharacter.values.max() }
        }
    }

    if (mostFrequentCharacterEntry.any { it.value == occurrencesByCharacter.values.max() && it.value == 1 }) {
        length++
    }

    return length
}

// https://leetcode.com/problems/longest-substring-without-repeating-characters/
fun lengthOfLongestSubstring(s: String): Int {
    var maxLength = 0

    val charactersSeen = mutableSetOf<Char>()
    var start = 0

    for ((end, character) in s.withIndex()) {
        while (charactersSeen.contains(character) && start < s.length) {
            charactersSeen.remove(s[start])
            start++
        }

        charactersSeen.add(character)

        maxLength = Integer.max(maxLength, end - start + 1)
    }

    return maxLength
}

// https://leetcode.com/problems/longest-common-prefix
fun longestCommonPrefix(words: Array<String>): String {
    var end = 0

    var sameCharacter = true

    do {
        if (words[0].isEmpty()) {
            break
        }

        val c = words[0][end]

        for (word in words) {
            if (end >= word.length || word[end] != c) {
                sameCharacter = false
                break
            }
        }

        if (sameCharacter) {
            end++
        }
    } while (sameCharacter && end < words[0].length)

    return words[0].substring(0..<end)
}

// https://leetcode.com/problems/single-row-keyboard/
fun calculateTime(keyboard: String, word: String): Int {
    var cost = 0

    val locationByKey = mutableMapOf<Char, Int>()

    for ((i, key) in keyboard.withIndex()) {
        locationByKey[key] = i
    }

    var lastLocation = 0

    for (char in word) {
        val location = locationByKey[char]
        cost += abs(lastLocation - location!!)
        lastLocation = location
    }

    return cost
}

// https://leetcode.com/problems/valid-parentheses/
fun isValid(s: String): Boolean {
    val openingParenByClosingParen = mapOf(
        ')' to '(',
        ']' to '[',
        '}' to '{',
    )

    val stack = Stack<Char>()

    for (c in s) {
        when (c) {
            '(', '[', '{' -> stack.push(c)
            else -> {
                val opening = openingParenByClosingParen[c]

                if (stack.isEmpty() || opening != stack.pop()) {
                    return false
                }
            }
        }
    }

    return stack.isEmpty()
}

// https://leetcode.com/problems/longest-palindromic-substring/
fun longestPalindromicSubstring(s: String): String {
    var start = 0
    var end = 0

    fun growPalindromicStringInRange(q: Int, p: Int) {
        var i = q
        var j = p
        while (i >= 0 && j < s.length && s[i] == s[j]) {
            i--
            j++
        }
        if (j - i > end - start) {
            end = j
            start = i + 1
        }
    }

    for (i in s.indices) {
        if (i != 0) growPalindromicStringInRange(i - 1, i)
        growPalindromicStringInRange(i, i)
    }

    return s.substring(start, end)
}

// https://leetcode.com/problems/valid-anagram
fun isAnagram(s: String, t: String) = s.length == t.length && key(s) == key(t)

private fun key(s: String): String {
    val characters = s.toCharArray()
    characters.sort()
    return characters.joinToString("")
}

fun groupAnagrams(words: Array<String>): List<List<String>> {
    val anagramsByKey = mutableMapOf<String, MutableList<String>>()

    for (word in words) {
        val k = key(word)
        val anagrams = anagramsByKey.getOrDefault(k, mutableListOf())
        anagrams.add(word)
        anagramsByKey[k] = anagrams
    }

    return anagramsByKey.values.toList()
}

fun customSortString(order: String, s: String): String {
    val indexByCharacter = mutableMapOf<Char, Int>()

    for ((i, c) in order.withIndex()) {
        indexByCharacter[c] = i
    }

    val customOrder: Comparator<Char> = compareBy { indexByCharacter.getOrDefault(it, Int.MAX_VALUE) }

    return s.toCharArray().sortedWith(customOrder).joinToString("")
}

fun minimumLength(s: String): Int {
    var start = 0
    var end = s.length - 1

    while (start < end && s[start] == s[end]) {
        val c = s[start]

        while (start <= end && s[start] == c) {
            start++
        }

        while (end > start && s[end] == c) {
            end--
        }
    }

    return end - start + 1
}

fun buddyStrings(s: String, goal: String): Boolean {
    if (s.length != goal.length) return false

    val sCountsByCharacter = s.asIterable().groupingBy { it }.eachCount()

    var firstMismatchedIndex = Optional.empty<Int>()
    var secondMismatchedIndex = Optional.empty<Int>()

    for ((i, c) in goal.withIndex()) {
        if (s[i] != c) {
            if (!firstMismatchedIndex.isPresent) {
                firstMismatchedIndex = Optional.of(i)
            } else if (!secondMismatchedIndex.isPresent) {
                secondMismatchedIndex = Optional.of(i)
            } else {
                return false
            }
        }
    }

    if (firstMismatchedIndex.isEmpty && secondMismatchedIndex.isEmpty) {
        return sCountsByCharacter.values.any { it > 1 }
    }

    if (secondMismatchedIndex.isEmpty) return false

    return s[firstMismatchedIndex.get()] == goal[secondMismatchedIndex.get()] &&
            s[secondMismatchedIndex.get()] == goal[firstMismatchedIndex.get()]
}

fun maximumOddBinaryNumber(s: String): String {
    val sb = StringBuilder()

    val countsByCharacter = s.groupingBy { it }.eachCount().toMutableMap()

    while (countsByCharacter['1']!! > 1) {
        sb.append('1')
        countsByCharacter['1'] = countsByCharacter['1']!! - 1
    }

    while (countsByCharacter.containsKey('0') && countsByCharacter['0']!! > 0) {
        sb.append('0')
        countsByCharacter['0'] = countsByCharacter['0']!! - 1
    }

    sb.append('1')

    return sb.toString()
}

// https://leetcode.com/problems/valid-word-square/description/?envType=study-plan-v2&envId=programming-skills
fun validWordSquare(words: List<String>): Boolean {
    for ((row, word) in words.withIndex()) {
        val columnarWord = getWordFromColumn(row, words)

        if (columnarWord != word) {
            return false
        }
    }

    return true
}

private fun getWordFromColumn(column: Int, words: List<String>): String {
    val sb = StringBuilder()

    for (word in words) {
        if (word.length > column) {
            sb.append(word[column])
        }
    }

    return sb.toString()
}

fun frequencySort(string: String): String {
    val countsByCharacter = string.groupingBy { it }.eachCount()

    val descendingCounts = countsByCharacter.entries.sortedBy { it.value }.reversed()

    val result = StringBuilder()

    for ((character, count) in descendingCounts) {
        for (i in 1..count) {
            result.append(character)
        }
    }

    return result.toString()
}

// https://leetcode.com/problems/find-first-palindromic-string-in-the-array/description/?envType=daily-question&envId=2024-02-13
fun firstPalindrome(words: Array<String>): String {
    return words.find { it.isPalindrome() } ?: ""
}

private fun String.isPalindrome(): Boolean {
    var (start, end) = 0 to this.length - 1

    while (start < end) {
        if (this[start] != this[end]) {
            return false
        }

        start++
        end--
    }

    return true
}

// https://leetcode.com/problems/first-unique-character-in-a-string/
fun firstUniqChar(word: String): Int {
    val countsByCharacter = word.groupingBy { it }.eachCount()

    for ((index, char) in word.withIndex()) {
        if (countsByCharacter[char] == 1) {
            return index
        }
    }

    return -1
}


fun tictactoe(moves: Array<IntArray>): String {
    val symbolsByLocation = mutableMapOf<Location, String>()

    for ((i, move) in moves.withIndex()) {
        val character = if (i % 2 == 0) "X" else "Y"
        symbolsByLocation[Location(move[0], move[1])] = character
    }

    for (row in 0..2) {
        val characters = mutableSetOf<String>()

        characters.add(symbolsByLocation.getOrDefault(Location(row, 0), ""))
        characters.add(symbolsByLocation.getOrDefault(Location(row, 1), ""))
        characters.add(symbolsByLocation.getOrDefault(Location(row, 2), ""))

        if (characters.size == 1 && !characters.contains("")) {
            return if (characters.first() == "X") "A" else "B"
        }
    }

    for (col in 0..2) {
        val characters = mutableSetOf<String>()

        characters.add(symbolsByLocation.getOrDefault(Location(0, col), ""))
        characters.add(symbolsByLocation.getOrDefault(Location(1, col), ""))
        characters.add(symbolsByLocation.getOrDefault(Location(2, col), ""))

        if (characters.size == 1 && !characters.contains("")) {
            return if (characters.first() == "X") "A" else "B"
        }
    }

    var characters = mutableSetOf<String>()

    characters.add(symbolsByLocation.getOrDefault(Location(0, 0), ""))
    characters.add(symbolsByLocation.getOrDefault(Location(1, 1), ""))
    characters.add(symbolsByLocation.getOrDefault(Location(2, 2), ""))

    if (characters.size == 1 && !characters.contains("")) {
        return if (characters.first() == "X") "A" else "B"
    }

    characters = mutableSetOf<String>()

    characters.add(symbolsByLocation.getOrDefault(Location(2, 0), ""))
    characters.add(symbolsByLocation.getOrDefault(Location(1, 1), ""))
    characters.add(symbolsByLocation.getOrDefault(Location(0, 2), ""))

    if (characters.size == 1 && !characters.contains("")) {
        return if (characters.first() == "X") "A" else "B"
    }

    return if (moves.size == 9) "Draw" else "Pending"
}


// https://leetcode.com/problems/robot-return-to-origin/
fun judgeCircle(moves: String): Boolean {
    val position = Point(0, 0)

    for (move in moves) {
        when (move) {
            'U' -> position.y += 1
            'D' -> position.y -= 1
            'L' -> position.x -= 1
            'R' -> position.x += 1
        }
    }

    return position.x == 0 && position.y == 0
}

// https://leetcode.com/problems/length-of-last-word/description/
fun lengthOfLastWord(word: String): Int = word.trim().split(" ").last().length

// https://leetcode.com/problems/count-vowel-strings-in-ranges/description/

private val lowerCasedVowels = setOf('a', 'e', 'i', 'o', 'u')
fun vowelStrings(words: Array<String>, queries: Array<IntArray>): IntArray {
    val answers = IntArray(queries.size)

    val vowelPrefixAndSuffix = mutableSetOf<Int>()

    for ((i, word) in words.withIndex()) {
        if (lowerCasedVowels.contains(word[0]) && lowerCasedVowels.contains(word[word.length - 1])) {
            vowelPrefixAndSuffix.add(i)
        }
    }

    for ((i, query) in queries.withIndex()) {
        val range = IntRange(query[0], query[1])
        answers[i] = vowelPrefixAndSuffix.count { range.contains(it) }
    }

    return answers
}


// https://leetcode.com/problems/find-the-difference/
fun findTheDifference(s: String, t: String): Char {
    val tCountsByCharacter = t.groupingBy { it }.eachCount()
    val sCountsByCharacter = s.groupingBy { it }.eachCount()

    return tCountsByCharacter.keys.first { sCountsByCharacter[it] != tCountsByCharacter[it] }
}

// https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string/
fun strStr(haystack: String, needle: String) = haystack.indexOf(needle)

// https://leetcode.com/problems/merge-strings-alternately/
fun mergeAlternately(word1: String, word2: String): String {
    val queue = ArrayDeque<String>()

    queue.add(word1)
    queue.add(word2)

    val merged = StringBuilder()

    while (queue.isNotEmpty()) {
        if (queue.size == 1) {
            merged.append(queue.pop())
            break
        }

        val word = queue.pop()

        val c = word.take(1)

        merged.append(c)

        val remaining = word.substring(1)

        if (remaining.isNotEmpty()) {
            queue.add(remaining)
        }
    }

    return merged.toString()
}

// https://leetcode.com/problems/repeated-substring-pattern/
fun repeatedSubstringPattern(s: String): Boolean {
    val key = s + s
    return key.substring(1, key.length - 1).contains(s)
}

// https://leetcode.com/problems/minimum-window-substring/
fun minWindow(source: String, target: String): String {
    val countsByCharacterInTarget = target.groupingBy { it }.eachCount().toMutableMap()

    var substringStart = 0
    var substringEnd = Int.MAX_VALUE

    var satisfiedCharacters = 0

    var start = 0

    for (end in source.indices) {
        val letter = source[end]

        if (countsByCharacterInTarget.containsKey(letter)) {
            val count = countsByCharacterInTarget[letter]!!
            countsByCharacterInTarget[letter] = count - 1

            if (countsByCharacterInTarget[letter] == 0) {
                satisfiedCharacters++
            }
        }

        while (satisfiedCharacters == countsByCharacterInTarget.size) {
            val startLetter = source[start]

            if (countsByCharacterInTarget.containsKey(startLetter)) {
                val count = countsByCharacterInTarget[startLetter]!!
                countsByCharacterInTarget[startLetter] = count + 1

                if (countsByCharacterInTarget[startLetter] == 1) {
                    satisfiedCharacters--
                }
            }

            if (end - start < substringEnd - substringStart) {
                substringEnd = end
                substringStart = start
            }

            start++
        }
    }

    if (substringEnd == Int.MAX_VALUE) {
        return ""
    }

    return source.substring(substringStart..substringEnd)
}

// https://leetcode.com/problems/determine-if-string-halves-are-alike/description/
fun halvesAreAlike(s: String): Boolean {
    val frontHalf = s.substring(0..<s.length / 2)
    val backHalf = s.substring(s.length / 2)

    return frontHalf.vowelCount() == backHalf.vowelCount()
}

private val vowels = setOf('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U')
private fun String.vowelCount(): Int {
    return count { it in vowels }
}

// https://leetcode.com/problems/minimum-number-of-steps-to-make-two-strings-anagram/
fun minSteps(s: String, t: String): Int {
    val counts = IntArray(26)

    for (i in s.indices) {
        counts[s[i] - 'a']++
        counts[t[i] - 'a']--
    }

    return counts.sumOf { it.coerceAtLeast(0) }
}

fun beautifulIndices(s: String, a: String, b: String, k: Int): List<Int> {
    val indices = mutableListOf<Int>()

    val jRange = IntRange(0, s.length - b.length)
    val iRange = IntRange(0, s.length - a.length)
    val jsMeetingCriteria = mutableSetOf<Int>()

    var found: Boolean
    var startIndex = 0

    do {
        val index = s.indexOf(b, startIndex)  // O(m*n)
        found = index >= 0
        startIndex = index + 1

        if (jRange.contains(index)) { // O(1)
            jsMeetingCriteria.add(index)
        }
    } while (found)

    startIndex = 0

    do {
        val index = s.indexOf(a, startIndex) // O(m*n)
        found = index >= 0
        startIndex = index + 1

        val hasJ = jsMeetingCriteria.any { abs(it - index) <= k } // O(n)

        if (iRange.contains(index) && hasJ) { // O(1)
            indices.add(index)
        }
    } while (found)

    indices.sort() // O(log(n))

    // O(n*M) + O(log(n)) + O(n)

    return indices
}

// https://leetcode.com/problems/ransom-note
fun canConstruct(note: String, magazine: String): Boolean {
    val characterCountsInNote = note.groupingBy { it }.eachCount()
    val characterCountsInMagazine = magazine.groupingBy { it }.eachCount()

    for (entry in characterCountsInNote) {
        if (characterCountsInMagazine.getOrDefault(entry.key, 0) < entry.value) {
            return false
        }
    }

    return true
}

// https://leetcode.com/problems/isomorphic-strings/?envType=daily-question&envId=2024-04-30
fun isIsomorphic(s: String, t: String): Boolean {
    val mappedCharacters = mutableMapOf<Char, Char>()
    val alreadyMapped = mutableSetOf<Char>()

    for ((i, c) in s.withIndex()) {
        if (mappedCharacters.containsKey(c) && mappedCharacters[c] != t[i]) {
            return false
        }

        if (!mappedCharacters.containsKey(c) && alreadyMapped.contains(t[i])) {
            return false
        }

        mappedCharacters[c] = t[i]
        alreadyMapped.add(t[i])
    }

    return true
}

// https://leetcode.com/problems/word-search/?envType=daily-question&envId=2024-04-30
private var puzzle: Array<CharArray> = emptyArray()

fun exist(board: Array<CharArray>, word: String): Boolean {
    puzzle = board
    val rows = board.size
    val columns = board[0].size
    for (row in board.indices) {
        for (col in board[0].indices) {
            if (backtrack(row, col, word, 0, rows, columns)) {
                return true
            }
        }
    }

    return false
}

fun backtrack(row: Int, col: Int, word: String, i: Int, rows: Int, columns: Int): Boolean {
    if (i >= word.length) return true

    if (row < 0 || row == rows || col < 0 ||
        col == columns || puzzle[row][col] != word[i]
    ) return false

    puzzle[row][col] = '#'

    val rowOffsets = arrayOf(0, 1, 0, -1)
    val columnOffsets = arrayOf(1, 0, -1, 0)

    for (j in rowOffsets.indices) {
        if (backtrack(row + rowOffsets[j], col + columnOffsets[j], word, i + 1, rows, columns))
            return true
    }

    puzzle[row][col] = word[i]

    return false
}

// https://leetcode.com/problems/maximum-nesting-depth-of-the-parentheses/?envType=daily-question&envId=2024-04-30
fun maxDepth(s: String): Int {
    var maxDepth = 0

    var depth = 0

    for (c in s) {
        when (c) {
            '(' -> {
                depth++
                maxDepth = maxOf(depth, maxDepth)
            }

            ')' -> depth--
        }
    }

    return maxDepth
}

// https://leetcode.com/problems/make-the-string-great/?envType=daily-question&envId=2024-04-30
fun makeGood(s: String): String {
    for (i in 0..<s.length - 1) {
        if (abs(s[i] - s[i + 1]) == 32) {
            return makeGood(s.substring(0..<i) + s.substring(i + 2))
        }
    }

    return s
}

// https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/?envType=daily-question&envId=2024-04-30
fun minRemoveToMakeValid(s: String): String {
    val unbalancedIndexes = mutableSetOf<Int>()
    val stack = ArrayDeque<Int>()

    for ((i, c) in s.withIndex()) {
        when (c) {
            '(' -> stack.push(i)
            ')' -> {
                if (stack.isEmpty()) {
                    unbalancedIndexes.add(i)
                } else {
                    stack.pop()
                }
            }
        }
    }

    while (stack.isNotEmpty()) unbalancedIndexes.add(stack.pop())

    val sb = StringBuilder()

    for ((i, c) in s.withIndex()) {
        if (!unbalancedIndexes.contains(i)) {
            sb.append(c)
        }
    }

    return sb.toString()
}

// https://leetcode.com/problems/valid-parenthesis-string/?envType=daily-question&envId=2024-04-30
fun checkValidString(s: String): Boolean {
    val openings = ArrayDeque<Int>()
    val asterisks = ArrayDeque<Int>()

    for ((i, c) in s.withIndex()) {
        when (c) {
            '(' -> openings.push(i)
            '*' -> asterisks.push(i)
            else -> {
                if (openings.isNotEmpty()) {
                    openings.pop()
                } else if (asterisks.isNotEmpty()) {
                    asterisks.pop()
                } else {
                    return false
                }
            }
        }
    }

    while (openings.isNotEmpty() && asterisks.isNotEmpty()) {
        if (openings.pop() > asterisks.pop()) {
            return false
        }
    }

    return openings.isEmpty()
}

// https://leetcode.com/problems/remove-k-digits/?envType=daily-question&envId=2024-04-30
fun removeKdigits(num: String, k: Int): String {
    val stack = ArrayDeque<Char>()
    var digitsToRemove = k

    for (digit in num) {
        while (stack.size > 0 && digitsToRemove > 0 && stack.peek() > digit) {
            stack.pop()
            digitsToRemove--
        }

        stack.push(digit)
    }

    while (digitsToRemove > 0) {
        stack.pop()
        digitsToRemove--
    }

    val sb = StringBuilder()
    var hasLeadingZeroes = true

    for (digit in stack.reversed()) {
        if (hasLeadingZeroes && digit == '0') continue
        hasLeadingZeroes = false
        sb.append(digit)
    }

    return if (sb.isEmpty()) "0" else sb.toString()
}

fun isNumber(s: String): Boolean {
    var sawDigit = false
    var sawExponent = false
    var sawDecimal = false

    for ((i, c) in s.withIndex()) {
        when {
            c.isDigit() -> sawDigit = true
            c == '+' || c == '-' -> {
                if (i > 0 && s[i - 1] != 'e' && s[i - 1] != 'E') {
                    return false
                }
            }

            c == 'e' || c == 'E' -> {
                if (sawExponent || !sawDigit) {
                    return false
                }
                sawExponent = true
                sawDigit = false
            }

            c == '.' -> {
                if (sawDecimal || sawExponent) {
                    return false
                }
                sawDecimal = true
            }

            else -> {
                return false
            }
        }
    }

    return sawDigit
}

fun myAtoi(input: String): Int {
    var sign = 1
    var result = 0
    var index = 0
    val n = input.length

    while (index < n && input[index] == ' ') {
        index++
    }

    if (index < n && input[index] == '+') {
        sign = 1
        index++
    } else if (index < n && input[index] == '-') {
        sign = -1
        index++
    }

    while (index < n && Character.isDigit(input[index])) {
        val digit = input[index].code - '0'.code

        if ((result > Int.MAX_VALUE / 10) ||
            (result == Int.MAX_VALUE / 10 && digit > Int.MAX_VALUE % 10)
        ) {
            return if (sign == 1) Int.MAX_VALUE else Int.MIN_VALUE
        }

        result = 10 * result + digit
        index++
    }

    return sign * result
}

// https://leetcode.com/problems/longest-ideal-subsequence/?envType=daily-question&envId=2024-04-30
fun longestIdealString(s: String, k: Int): Int {
    val dp = IntArray(26)

    var length = 0

    for (i in s.indices) {
        val current = s[i].code - 'a'.code

        var best = 0

        for (prev in 0 until 26) {
            if (abs(prev - current) <= k) {
                best = maxOf(best, dp[prev])
            }
        }

        dp[current] = maxOf(dp[current], best + 1)
        length = maxOf(length, dp[current])
    }

    return length
}

// https://leetcode.com/problems/freedom-trail/?envType=daily-question&envId=2024-04-30
fun findRotateSteps(ring: String, key: String): Int {
    val bestSteps = Array(ring.length) { IntArray(key.length + 1) }

    for (row in bestSteps) {
        Arrays.fill(row, Int.MAX_VALUE)
    }

    for (i in ring.indices) {
        bestSteps[i][key.length] = 0
    }

    for (keyIndex in key.length - 1 downTo 0) {
        for (ringIndex in ring.indices) {
            for (charIndex in ring.indices) {
                if (ring[charIndex] == key[keyIndex]) {
                    bestSteps[ringIndex][keyIndex] = minOf(
                        bestSteps[ringIndex][keyIndex],
                        1 + countSteps(ringIndex, charIndex, ring.length) +
                                bestSteps[charIndex][keyIndex + 1]
                    )
                }
            }
        }
    }

    return bestSteps[0][0]
}

fun countSteps(current: Int, next: Int, length: Int): Int {
    val stepsBetween = abs(current - next)
    val stepsAround = length - stepsBetween

    return minOf(stepsBetween, stepsAround)
}

// https://leetcode.com/problems/number-of-wonderful-substrings/description/?envType=daily-question&envId=2024-04-30
fun wonderfulSubstrings(word: String): Long {
    var wonderfuls: Long = 0
    val countsByBitmask = mutableMapOf(
        0 to 1
    )

    var mask = 0

    for (i in word.indices) {
        val c = word[i]
        val bit = c.code - 'a'.code

        mask = mask xor (1 shl bit)

        wonderfuls += countsByBitmask.getOrPut(mask) { 0 }.toLong()
        countsByBitmask[mask] = countsByBitmask.getOrElse(mask) { 0 } + 1

        for (oddCharacter in 0..9) {
            wonderfuls += countsByBitmask.getOrDefault(mask xor (1 shl oddCharacter), 0)
        }
    }

    return wonderfuls
}

// https://leetcode.com/problems/reverse-prefix-of-word/description/?envType=daily-question&envId=2024-05-01
fun reversePrefix(word: String, ch: Char): String {
    val end = word.indexOf(ch)

    if (end == -1) {
        return word
    }

    val prefix = word.substring(0..end)
    return prefix.reversed() + word.substring(end + 1)
}

// https://leetcode.com/problems/compare-version-numbers/description/?envType=daily-question&envId=2024-05-03
fun compareVersion(version1: String, version2: String): Int {
    return Version(version1).compareTo(Version(version2))
}

class Version(v: String) : Comparable<Version> {
    private val parts = v.split(".").map { it.trimStart('0') }.map { it.ifEmpty { "0" } }

    override fun compareTo(other: Version): Int {
        val longer = if (this.parts.size >= other.parts.size) this.parts else other.parts
        for (i in longer.indices) {
            val left = if (this.parts.size > i) this.parts[i].toInt() else 0
            val right = if (other.parts.size > i) other.parts[i].toInt() else 0

            val delta = left - right

            if (delta != 0) {
                return if (delta > 0) 1 else -1
            }
        }

        return 0
    }
}

fun gcdOfStrings(str1: String, str2: String): String {
    val lengthComparator: Comparator<String> = compareBy<String> { it.length }.reversed()
    val maxHeap = PriorityQueue(lengthComparator)

    val i = 0

    for (j in 0 until str1.length - 2) {
        val substring = str1.substring(i..j)

        if (str1.isDivisibleBy(substring) && str2.isDivisibleBy(substring)) {
            maxHeap.add(substring)
        }
    }

    return if (maxHeap.isNotEmpty()) maxHeap.remove() else ""
}

fun String.isDivisibleBy(substring: String) = this.replace(substring, "") == ""

// TODO: Study backtracking and DP
// https://leetcode.com/problems/palindrome-partitioning/editorial/?envType=daily-question&envId=2024-05-22
fun partition(s: String): List<List<String>> {
    val length = s.length
    val dp = Array(length) { Array(length) { false } }
    val result = mutableListOf<List<String>>()

    substringDFS(result, s, 0, mutableListOf(), dp)

    return result
}

fun substringDFS(
    result: MutableList<List<String>>,
    s: String,
    start: Int,
    current: MutableList<String>,
    dp: Array<Array<Boolean>>
) {
    if (start >= s.length) result.add(current.toMutableList())

    for (end in start until s.length) {
        if (s[start] == s[end] && (end - start <= 2 || dp[start + 1][end - 1])) {
            dp[start][end] = true
            current.add(s.substring(start, end + 1))
            substringDFS(result, s, end + 1, current, dp)
            current.removeLast()
        }
    }
}

// https://leetcode.com/problems/maximum-score-words-formed-by-letters/?envType=daily-question&envId=2024-05-24
fun maxScoreWords(words: Array<String>, letters: CharArray, scores: IntArray): Int {
    val characterFrequencies = IntArray(26) { 0 }
    val numWords = words.size

    for (letter in letters) {
        characterFrequencies[letter.code - 'a'.code]++
    }

    var maxScore = 0

    fun isValidWord(subsetLetters: IntArray): Boolean {
        for (c in 0 until 26) {
            if (characterFrequencies[c] < subsetLetters[c]) {
                return false
            }
        }

        return true
    }

    fun check(w: Int, words: Array<String>, scores: IntArray, subsetLetters: IntArray, totalScore: Int) {
        var score = totalScore

        if (w == -1) {
            maxScore = maxOf(maxScore, score)
            return
        }

        check(w - 1, words, scores, subsetLetters, score)

        val length = words[w].length

        for (i in 0 until length) {
            val c = words[w][i].code - 'a'.code
            subsetLetters[c]++
            score += scores[c]
        }

        if (isValidWord(subsetLetters)) {
            check(w - 1, words, scores, subsetLetters, score)
        }

        for (i in 0 until length) {
            val c = words[w][i].code - 'a'.code
            subsetLetters[c]--
            score -= scores[c]
        }
    }

    check(numWords - 1, words, scores, IntArray(26), 0)

    return maxScore
}

fun wordBreak(string: String, validWords: List<String>): List<String> {
    val uniqueWords = validWords.toSet()
    val results = mutableListOf<String>()

    fun backtrack(
        s: String, words: Set<String>,
        currentSentence: StringBuilder,
        sentences: MutableList<String>, startIndex: Int
    ) {
        if (startIndex == s.length) {
            sentences.add(currentSentence.toString().trim())
            return
        }

        for (endIndex in startIndex + 1..s.length) {
            val word = s.substring(startIndex, endIndex)

            if (words.contains(word)) {
                val currentLength = currentSentence.length
                currentSentence.append(word).append(" ")
                backtrack(s, words, currentSentence, sentences, endIndex)
                currentSentence.setLength(currentLength)
            }
        }

    }

    backtrack(string, uniqueWords, StringBuilder(), results, 0)

    return results
}

fun checkRecord(n: Int): Int {
    var MODULO = 1000000007

    val memo = Array(n + 1) { Array(2) { IntArray(3) { -1 } } }

    fun eligibleCombinations(
        recordLength: Int,
        totalAbsences: Int,
        consecutiveLates: Int
    ): Int {
        if (totalAbsences >= 2 || consecutiveLates >= 3) {
            return 0
        }

        if (recordLength == 0) return 1

        if (memo[recordLength][totalAbsences][consecutiveLates] != -1) {
            return memo[recordLength][totalAbsences][consecutiveLates]
        }

        var count = 0

        count = eligibleCombinations(recordLength - 1, totalAbsences, 0) % MODULO
        count = (count + eligibleCombinations(recordLength - 1, totalAbsences + 1, 0)) % MODULO
        count = (count + eligibleCombinations(recordLength - 1, totalAbsences, consecutiveLates + 1)) % MODULO

        memo[recordLength][totalAbsences][consecutiveLates] = count

        return count
    }

    return eligibleCombinations(n, 0, 0)
}

fun scoreOfString(s: String): Int {
    var score = 0

    for (i in 0..s.length - 2) {
        score += abs(s[i].code - s[i + 1].code)
    }

    return score
}

fun reverseString(s: CharArray): Unit {
    var start = 0
    var end = s.size - 1

    while (start <= end) {
        val temp = s[start]
        s[start] = s[end]
        s[end] = temp
        start++
        end--
    }
}

fun appendCharacters(s: String, t: String): Int {
    var first = 0
    var longestPrefix = 0

    while (first < s.length && longestPrefix < t.length) {
        if (s[first++] == t[longestPrefix]) longestPrefix++
    }

    return t.length - longestPrefix
}

fun commonChars(words: Array<String>): List<String> {
    val commonCharacters = mutableListOf<String>()
    val commonCharacterCounts = Array(26) { 0 }
    val currentCharacterCounts = Array(26) { 0 }

    for (c in words.first()) {
        commonCharacterCounts[c.code - 'a'.code]++
    }

    for (i in 1 until words.size) {
        Arrays.fill(currentCharacterCounts, 0)

        for (c in words[i]) {
            currentCharacterCounts[c.code - 'a'.code]++
        }

        for (letter in 0..25) {
            commonCharacterCounts[letter] = minOf(
                commonCharacterCounts[letter],
                currentCharacterCounts[letter]
            )
        }
    }

    for (letter in 0..25) {
        val instances = commonCharacterCounts[letter]

        if (instances > 0) {
            for (i in 1..instances) {
                commonCharacters.add(('a'.code + letter).toChar().toString())
            }
        }
    }

    return commonCharacters
}

fun replaceWords(dictionary: List<String>, sentence: String): String {
    val sb = StringBuilder()

    val roots = dictionary.sortedBy { it.length }

    for (word in sentence.split(" ")) {
        var found = false

        for (root in roots) {
            if (word.startsWith(root)) {
                found = true
                sb.append(root)
                break
            }
        }

        if (!found) {
            sb.append(word)
        }

        sb.append(" ")
    }

    return sb.toString().trim()
}
