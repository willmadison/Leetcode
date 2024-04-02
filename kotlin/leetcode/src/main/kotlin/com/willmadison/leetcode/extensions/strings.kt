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

fun maximumWealth(accounts: Array<IntArray>): Int {
    val wealthByCustomer = mutableMapOf<Int, Int>()

    for ((customer, customerAccounts) in accounts.withIndex()) {
        wealthByCustomer[customer] = customerAccounts.sum()
    }

    return wealthByCustomer.values.max()
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

fun isIsomorphic(s: String, t: String): Boolean {
    val mappedCharacters = mutableMapOf<Char, Char>()
    val alreadyMapped = mutableSetOf<Char>()

    for ((i,c) in s.withIndex()) {
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
