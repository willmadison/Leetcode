package com.willmadison.leetcode

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class StringsTests {

    companion object {
        @JvmStatic
        fun magazineSourceProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("a", "b", false),
                Arguments.of("aa", "ab", false),
                Arguments.of("aa", "aab", true),
            )
        }

        @JvmStatic
        fun palindromeProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("abccccdd", 7),
                Arguments.of("a", 1),
            )
        }

        @JvmStatic
        fun reversePrefixProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "abcdefd",
                    'd',
                    "dcbaefd",
                ),
                Arguments.of(
                    "xyxzxe",
                    'z',
                    "zxyxxe",
                ),
                Arguments.of(
                    "abcd",
                    'z',
                    "abcd",
                ),
            )
        }

        @JvmStatic
        fun substringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "abcabcbb",
                    3,
                ),
                Arguments.of(
                    "bbbbb",
                    1,
                ),
                Arguments.of(
                    "pwwkew",
                    3,
                ),
            )
        }

        @JvmStatic
        fun prefixProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        "flower",
                        "flow",
                        "flight"
                    ),
                    "fl",
                ),
                Arguments.of(
                    arrayOf(
                        "dog",
                        "racecar",
                        "car"
                    ),
                    "",
                ),
            )
        }

        @JvmStatic
        fun keyboardProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "abcdefghijklmnopqrstuvwxyz",
                    "cba",
                    4,
                ),
                Arguments.of(
                    "pqrstuvwxyzabcdefghijklmno",
                    "leetcode",
                    73,
                ),
            )
        }

        @JvmStatic
        fun parenthesisProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "()",
                    true,
                ),
                Arguments.of(
                    "()[]{}",
                    true,
                ),
                Arguments.of(
                    "(]",
                    false,
                ),
                Arguments.of(
                    "]",
                    false,
                ),
            )
        }

        @JvmStatic
        fun palindromicSubstringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "babad",
                    "aba",
                ),
                Arguments.of(
                    "",
                    "",
                ),
            )
        }

        @JvmStatic
        fun anagramProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "anagram",
                    "nagaram",
                    true,
                ),
                Arguments.of(
                    "rat",
                    "car",
                    false,
                ),
            )
        }

        @JvmStatic
        fun windowProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "ADOBECODEBANC",
                    "ABC",
                    "BANC",
                ),
                Arguments.of(
                    "a",
                    "a",
                    "a",
                ),
                Arguments.of(
                    "a",
                    "aa",
                    "",
                ),
            )
        }

        @JvmStatic
        fun halveProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "book",
                    true,
                ),
                Arguments.of(
                    "textbook",
                    false,
                ),
            )
        }

        @JvmStatic
        fun stepProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "bab",
                    "aba",
                    1,
                ),
                Arguments.of(
                    "leetcode",
                    "practice",
                    5,
                ),
                Arguments.of(
                    "anagram",
                    "mangaar",
                    0,
                ),
            )
        }

        @JvmStatic
        fun beautyProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "isawsquirrelnearmysquirrelhouseohmy",
                    "my",
                    "squirrel",
                    15,
                    listOf(16, 33),
                ),
                Arguments.of(
                    "abcd",
                    "a",
                    "a",
                    4,
                    listOf(0),
                ),
                Arguments.of(
                    "eueuau",
                    "u",
                    "e",
                    3,
                    listOf(1, 3, 5),
                ),
            )
        }

        @JvmStatic
        fun differenceProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "abcd",
                    "abcde",
                    'e'
                ),
                Arguments.of(
                    "",
                    "y",
                    'y'
                ),
                Arguments.of(
                    "a",
                    "aa",
                    'a'
                ),
            )
        }

        @JvmStatic
        fun needleProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "sadbutsad",
                    "sad",
                    0,
                ),
                Arguments.of(
                    "leetcode",
                    "leeto",
                    -1,
                ),
            )
        }

        @JvmStatic
        fun mergeProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "abc",
                    "pqr",
                    "apbqcr",
                ),
                Arguments.of(
                    "ab",
                    "pqrs",
                    "apbqrs",
                ),
                Arguments.of(
                    "abcd",
                    "pq",
                    "apbqcd",
                ),
            )
        }

        @JvmStatic
        fun repeatedSubstringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "abab",
                    true,
                ),
                Arguments.of(
                    "aba",
                    false,
                ),
                Arguments.of(
                    "abcabcabcabc",
                    true,
                ),
                Arguments.of(
                    "bb",
                    true,
                ),
            )
        }

        @JvmStatic
        fun vowelStringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        "aba",
                        "bcb",
                        "ece",
                        "aa",
                        "e"
                    ),
                    arrayOf(
                        intArrayOf(0, 2),
                        intArrayOf(1, 4),
                        intArrayOf(1, 1),
                    ),
                    intArrayOf(2, 3, 0),
                ),
            )
        }
    }

    @ParameterizedTest(name = "canConstruct({0}, {1}) = {2}")
    @MethodSource("magazineSourceProvider")
    fun canConstruct(note: String, magazine: String, expected: Boolean) {
        val actual = com.willmadison.leetcode.extensions.canConstruct(note, magazine)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "longestPalindrome({0}) = {1}")
    @MethodSource("palindromeProvider")
    fun longestPalindrome(value: String, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.longestPalindrome(value)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "wonderfulSubstrings({0}, {1}) = {2}")
    @MethodSource("reversePrefixProvider")
    fun reversePrefix(word: String, ch: Char, expected: String) {
        val actual = com.willmadison.leetcode.extensions.reversePrefix(word, ch)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "lengthOfLongestSubstring({0}) = {1}")
    @MethodSource("substringProvider")
    fun lengthOfLongestSubstring(s: String, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.lengthOfLongestSubstring(s)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "longestCommonPrefix({0}) = {1}")
    @MethodSource("prefixProvider")
    fun longestCommonPrefix(words: Array<String>, expected: String) {
        val actual = com.willmadison.leetcode.extensions.longestCommonPrefix(words)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "calculateTime({0}, {1}) = {2}")
    @MethodSource("keyboardProvider")
    fun calculateTime(keyboard: String, word: String, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.calculateTime(keyboard, word)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "isValid({0}) = {1}")
    @MethodSource("parenthesisProvider")
    fun isValid(given: String, expected: Boolean) {
        val actual = com.willmadison.leetcode.extensions.isValid(given)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "longestPalindromicSubstring({0}) = {1}")
    @MethodSource("palindromicSubstringProvider")
    fun longestPalindromicSubstring(s: String, expected: String) {
        val actual = com.willmadison.leetcode.extensions.longestPalindromicSubstring(s)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "isAnagram({0}, {1}) = {2}")
    @MethodSource("anagramProvider")
    fun isAnagram(s: String, t: String, expected: Boolean) {
        val actual = com.willmadison.leetcode.extensions.isAnagram(s, t)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "minWindow({0}, {1}) = {2}")
    @MethodSource("windowProvider")
    fun minWindow(source: String, target: String, expected: String) {
        val actual = com.willmadison.leetcode.extensions.minWindow(source, target)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "havlesAreAlike({0}) = {1}")
    @MethodSource("halveProvider")
    fun halvesAreAlike(s: String, expected: Boolean) {
        val actual = com.willmadison.leetcode.extensions.halvesAreAlike(s)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "minSteps({0}, {1}) = {2}")
    @MethodSource("stepProvider")
    fun minSteps(s: String, t: String, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.minSteps(s, t)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "beautifulIndices({0}, {1}, {2}, {3}) = {4}")
    @MethodSource("beautyProvider")
    fun beautifulIndices(s: String, a: String, b: String, k: Int, expected: List<Int>) {
        val actual = com.willmadison.leetcode.extensions.beautifulIndices(s, a, b, k)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "findTheDifference({0}, {1}) = {2}")
    @MethodSource("differenceProvider")
    fun findTheDifference(s: String, t: String, expected: Char) {
        val actual = com.willmadison.leetcode.extensions.findTheDifference(s, t)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "strStr({0}, {1}) = {2}")
    @MethodSource("needleProvider")
    fun strStr(haystack: String, needle: String, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.strStr(haystack, needle)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "mergeAlternately({0}, {1}) = {2}")
    @MethodSource("mergeProvider")
    fun mergeAlternately(word1: String, word2: String, expected: String) {
        val actual = com.willmadison.leetcode.extensions.mergeAlternately(word1, word2)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "repeatedSubstringPattern({0}) = {1}")
    @MethodSource("repeatedSubstringProvider")
    fun repeatedSubstringPattern(s: String, expected: Boolean) {
        val actual = com.willmadison.leetcode.extensions.repeatedSubstringPattern(s)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "findMin({0}, {1}) = {2}")
    @MethodSource("vowelStringProvider")
    fun vowelStrings(words: Array<String>, queries: Array<IntArray>, expected: IntArray) {
        val actual = com.willmadison.leetcode.extensions.vowelStrings(words, queries)
        assertArrayEquals(expected, actual)
    }

}