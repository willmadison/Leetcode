package com.willmadison.leetcode

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
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

        @JvmStatic
        fun wordProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "Hello World",
                    5,
                ),
                Arguments.of(
                    "   fly me   to   the moon  ",
                    4,
                ),
                Arguments.of(
                    "luffy is still joyboy",
                    6,
                ),
            )
        }

        @JvmStatic
        fun moveProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "UD",
                    true,
                ),
                Arguments.of(
                    "LL",
                    false,
                ),
            )
        }

        @JvmStatic
        fun ticTacToeProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        intArrayOf(0, 0),
                        intArrayOf(2, 0),
                        intArrayOf(1, 1),
                        intArrayOf(2, 1),
                        intArrayOf(2, 2)
                    ),
                    "A"
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(0, 0),
                        intArrayOf(1, 1),
                        intArrayOf(0, 1),
                        intArrayOf(0, 2),
                        intArrayOf(1, 0),
                        intArrayOf(2, 0)
                    ),
                    "B"
                ),
            )
        }

        @JvmStatic
        fun uniqueCharProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "leetcode",
                    0,
                ),
                Arguments.of(
                    "loveleetcode",
                    2,
                ),
                Arguments.of(
                    "aabb",
                    -1,
                ),
            )
        }

        @JvmStatic
        fun anagramGroupProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf("eat", "tea", "tan", "ate", "nat", "bat"),
                    listOf(
                        listOf("bat"),
                        listOf("tan", "nat"),
                        listOf("eat", "tea", "ate"),
                    )
                ),
                Arguments.of(
                    arrayOf(""),
                    listOf(
                        listOf(""),
                    )
                ),
            )
        }

        @JvmStatic
        fun palindromeListProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf("abc", "car", "ada", "racecar", "cool"),
                    "ada",
                ),
                Arguments.of(
                    arrayOf("def", "ghi"),
                    "",
                ),
            )
        }

        @JvmStatic
        fun frequencySortProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "leetcode",
                    "eeeltcod",
                ),
                Arguments.of(
                    "tree",
                    "eetr",
                ),
                Arguments.of(
                    "aaaccc",
                    "aaaccc",
                ),
            )
        }

        @JvmStatic
        fun wordSquareProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    listOf(
                        "abcd", "bnrt", "crmy", "dtye"
                    ),
                    true,
                ),
                Arguments.of(
                    listOf(
                        "ball", "area", "read", "lady"
                    ),
                    false,
                ),
            )
        }

        @JvmStatic
        fun binaryNumberProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "010",
                    "001",
                ),
                Arguments.of(
                    "0101",
                    "1001",
                ),
            )
        }

        @JvmStatic
        fun buddyStringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "ab",
                    "ba",
                    true,
                ),
                Arguments.of(
                    "ab",
                    "ab",
                    false,
                ),
                Arguments.of(
                    "aa",
                    "aa",
                    true,
                ),
            )
        }

        @JvmStatic
        fun minimumLengthStringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "ca",
                    2,
                ),
                Arguments.of(
                    "cabaabac",
                    0,
                ),
                Arguments.of(
                    "aabccabba",
                    3,
                ),
            )
        }

        @JvmStatic
        fun customStringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "cba",
                    "abcd",
                    "cbad",
                ),
                Arguments.of(
                    "bcafg",
                    "abcd",
                    "bcad",
                ),
            )
        }

        @JvmStatic
        fun isomorphicStringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "egg",
                    "add",
                    true,
                ),
                Arguments.of(
                    "foo",
                    "bar",
                    false,
                ),
                Arguments.of(
                    "paper",
                    "title",
                    true,
                ),
            )
        }

        @JvmStatic
        fun rotateStepProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "godding",
                    "gd",
                    4,
                ),
                Arguments.of(
                    "godding",
                    "godding",
                    13,
                ),
            )
        }

        @JvmStatic
        fun wonderfulSubstringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "aba",
                    4L,
                ),
                Arguments.of(
                    "aabb",
                    9L,
                ),
                Arguments.of(
                    "he",
                    2L,
                ),
            )
        }

        @JvmStatic
        fun crosswordProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        charArrayOf('A', 'B', 'C', 'E'),
                        charArrayOf('S', 'F', 'C', 'S'),
                        charArrayOf('A', 'D', 'E', 'E'),
                    ),
                    "ABCCED",
                    true,
                ),
                Arguments.of(
                    arrayOf(
                        charArrayOf('A', 'B', 'C', 'E'),
                        charArrayOf('S', 'F', 'C', 'S'),
                        charArrayOf('A', 'D', 'E', 'E'),
                    ),
                    "SEE",
                    true,
                ),
                Arguments.of(
                    arrayOf(
                        charArrayOf('A', 'B', 'C', 'E'),
                        charArrayOf('S', 'F', 'C', 'S'),
                        charArrayOf('A', 'D', 'E', 'E'),
                    ),
                    "ABCB",
                    false,
                )
            )
        }

        @JvmStatic
        fun maxDepthProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "(1+(2*3)+((8)/4))+1",
                    3,
                ),
                Arguments.of(
                    "(1)+((2))+(((3)))",
                    3,
                ),
            )
        }

        @JvmStatic
        fun goodStringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "leEeetcode",
                    "leetcode",
                ),
                Arguments.of(
                    "abBAcC",
                    "",
                ),
            )
        }

        @JvmStatic
        fun removeToMakeValidProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "lee(t(c)o)de)",
                    "lee(t(c)o)de",
                ),
                Arguments.of(
                    "a)b(c)d",
                    "ab(c)d",
                ),
                Arguments.of(
                    "))((",
                    "",
                ),
            )
        }

        @JvmStatic
        fun wildcardValidStringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "()",
                    true,
                ),
                Arguments.of(
                    "(*)",
                    true,
                ),
                Arguments.of(
                    "(*))",
                    true,
                ),
            )
        }

        @JvmStatic
        fun kdigitsProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "1432219",
                    3,
                    "1219",
                ),
                Arguments.of(
                    "10200",
                    1,
                    "200",
                ),
                Arguments.of(
                    "10",
                    2,
                    "0",
                ),
            )
        }

        @JvmStatic
        fun numberProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "0",
                    true,
                ),
                Arguments.of(
                    "e",
                    false,
                ),
                Arguments.of(
                    ".",
                    false,
                ),
            )
        }

        @JvmStatic
        fun idealStringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "acfgbd",
                    2,
                    4,
                ),
                Arguments.of(
                    "abcd",
                    3,
                    4,
                ),
            )
        }

        @JvmStatic
        fun versionComparisonProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "1.01",
                    "1.0001",
                    0,
                ),
                Arguments.of(
                    "1.0",
                    "1.0.0",
                    0,
                ),
                Arguments.of(
                    "0.1",
                    "1.0.0",
                    -1,
                ),
                Arguments.of(
                    "1",
                    "1.1",
                    -1,
                ),
            )
        }

        @JvmStatic
        fun gcdStringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "ABCABC",
                    "ABC",
                    "ABC",
                ),
                Arguments.of(
                    "ABABAB",
                    "ABAB",
                    "AB",
                ),
                Arguments.of(
                    "LEET",
                    "CODE",
                    "",
                ),
            )
        }

        @JvmStatic
        fun partitioningProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "aab",
                    listOf(
                        listOf("a", "a", "b"),
                        listOf("aa", "b"),
                    )
                ),
                Arguments.of(
                    "a",
                    listOf(
                        listOf("a"),
                    )
                ),
            )
        }

        @JvmStatic
        fun maxScoreWordsProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf("dog", "cat", "dad", "good"),
                    charArrayOf('a', 'a', 'c', 'd', 'd', 'd', 'g', 'o', 'o'),
                    intArrayOf(1, 0, 9, 5, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                    23,
                ),
                Arguments.of(
                    arrayOf("xxxz", "ax", "bx", "cx"),
                    charArrayOf('z', 'a', 'b', 'c', 'x', 'x', 'x'),
                    intArrayOf(4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 10),
                    27,
                ),
            )
        }

        @JvmStatic
        fun wordBreakProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "catsanddog",
                    listOf("cat", "cats", "and", "sand", "dog"),
                    listOf("cats and dog", "cat sand dog"),
                ),
                Arguments.of(
                    "pineapplepenapple",
                    listOf("apple", "pen", "applepen", "pine", "pineapple"),
                    listOf("pine apple pen apple", "pineapple pen apple", "pine applepen apple"),
                ),
            )
        }

        @JvmStatic
        fun characterAppendProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "coaching",
                    "coding",
                    4,
                ),
                Arguments.of(
                    "abcde",
                    "a",
                    0,
                ),
                Arguments.of(
                    "z",
                    "abcde",
                    5,
                ),
            )
        }

        @JvmStatic
        fun commonCharWordProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf("bella", "label", "roller"),
                    listOf("e", "l", "l"),
                ),
                Arguments.of(
                    arrayOf("cool", "lock", "cook"),
                    listOf("c", "o"),
                ),
            )
        }

        @JvmStatic
        fun replaceWordsProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    listOf("cat", "bat", "rat"),
                    "the cattle was rattled by the battery",
                    "the cat was rat by the bat",
                ),
                Arguments.of(
                    listOf("a", "b", "c"),
                    "aadsfasf absbs bbab cadsfafs",
                    "a a b c",
                ),
                Arguments.of(
                    listOf("catt", "cat", "bat", "rat"),
                    "the cattle was rattled by the battery",
                    "the cat was rat by the bat",
                ),
            )
        }

        @JvmStatic
        fun longestDiverseStringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    1,
                    1,
                    7,
                    listOf("ccaccbcc", "ccbccacc"),
                ),
                Arguments.of(
                    7,
                    1,
                    0,
                    listOf("aabaa"),
                ),
            )
        }

        @JvmStatic
        fun folderProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf("/a","/a/b","/c/d","/c/d/e","/c/f"),
                    listOf("/a","/c/d","/c/f"),
                ),
                Arguments.of(
                    arrayOf("/a","/a/b/c","/a/b/d"),
                    listOf("/a"),
                ),
                Arguments.of(
                    arrayOf("/a/b/c","/a/b/ca","/a/b/d"),
                    listOf("/a/b/c","/a/b/ca","/a/b/d"),
                ),
            )
        }

        @JvmStatic
        fun maxVowelsProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "abciiidef",
                    3,
                    3,
                ),
                Arguments.of(
                    "aeiou",
                    2,
                    2,
                ),
                Arguments.of(
                    "leetcode",
                    3,
                    2,
                ),
                Arguments.of(
                    "a",
                    1,
                    1,
                ),
            )
        }

        @JvmStatic
        fun fancyStringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "leeetcode",
                    "leetcode",
                ),
                Arguments.of(
                    "aaabaaaa",
                    "aabaa",
                ),
                Arguments.of(
                    "aab",
                    "aab",
                ),
            )
        }

        @JvmStatic
        fun circularSentenceProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "leeetcode",
                    false,
                ),
                Arguments.of(
                    "leetcode exercises sound delightful",
                    true,
                ),
                Arguments.of(
                    "eetcode",
                    true,
                ),
            )
        }

        @JvmStatic
        fun stringRotationProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "abcde",
                    "cdeab",
                    true,
                ),
                Arguments.of(
                    "abcde",
                    "abced",
                    false,
                ),
                Arguments.of(
                    "aa",
                    "a",
                    false,
                ),
            )
        }

        @JvmStatic
        fun compressedStringProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "abcde",
                    "1a1b1c1d1e",
                ),
                Arguments.of(
                    "aaaaaaaaaaaaaabb",
                    "9a5a2b",
                ),
            )
        }

        @JvmStatic
        fun minChangesProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "1001",
                    2,
                ),
                Arguments.of(
                    "10",
                    1,
                ),
            )
        }

        @JvmStatic
        fun isPrefixOfWordProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "i love eating burger",
                    "burg",
                    4,
                ),
                Arguments.of(
                    "this problem is an easy problem",
                    "pro",
                    2,
                ),
                Arguments.of(
                    "I am tired",
                    "you",
                    -1,
                ),
            )
        }

        @JvmStatic
        fun almostEqualProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "bank",
                    "kanb",
                    true,
                ),
                Arguments.of(
                    "attack",
                    "defend",
                    false,
                ),
                Arguments.of(
                    "kelb",
                    "kelb",
                    true,
                ),
            )
        }

        @JvmStatic
        fun isValid2Provider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "234Adas",
                    true,
                ),
                Arguments.of(
                    "b3",
                    false,
                ),
                Arguments.of(
                    "aya",
                    true,
                ),
                Arguments.of(
                    "IMG",
                    true,
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

    @ParameterizedTest(name = "lengthOfLastWord({0}) = {1}")
    @MethodSource("wordProvider")
    fun lengthOfLastWord(word: String, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.lengthOfLastWord(word)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "judgeCircle({0}) = {1}")
    @MethodSource("moveProvider")
    fun judgeCircle(moves: String, expected: Boolean) {
        val actual = com.willmadison.leetcode.extensions.judgeCircle(moves)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "tictactoe({0}) = {1}")
    @MethodSource("ticTacToeProvider")
    fun tictactoe(moves: Array<IntArray>, expected: String) {
        val actual = com.willmadison.leetcode.extensions.tictactoe(moves)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "firstUniqChr({0}) = {1}")
    @MethodSource("uniqueCharProvider")
    fun firstUniqChar(word: String, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.firstUniqChar(word)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "groupAnagrams({0}) = {1}")
    @MethodSource("anagramGroupProvider")
    fun groupAnagrams(words: Array<String>, expected: List<List<String>>) {
        val actual = com.willmadison.leetcode.extensions.groupAnagrams(words)
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected)
    }

    @ParameterizedTest(name = "firstPalindrome({0}) = {1}")
    @MethodSource("palindromeListProvider")
    fun firstPalindrome(words: Array<String>, expected: String) {
        val actual = com.willmadison.leetcode.extensions.firstPalindrome(words)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "frequencySort({0}) = {1}")
    @MethodSource("frequencySortProvider")
    @Disabled
    fun frequencySort(string: String, expected: String) {
        val actual = com.willmadison.leetcode.extensions.frequencySort(string)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "validWordSquare({0}) = {1}")
    @MethodSource("wordSquareProvider")
    fun validWordSquare(words: List<String>, expected: Boolean) {
        val actual = com.willmadison.leetcode.extensions.validWordSquare(words)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "maximumOddBinaryNumber({0}) = {1}")
    @MethodSource("binaryNumberProvider")
    fun maximumOddBinaryNumber(s: String, expected: String) {
        val actual = com.willmadison.leetcode.extensions.maximumOddBinaryNumber(s)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "buddyStrings({0}, {1}) = {2}")
    @MethodSource("buddyStringProvider")
    fun buddyStrings(s: String, goal: String, expected: Boolean) {
        val actual = com.willmadison.leetcode.extensions.buddyStrings(s, goal)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "minimumLength({0}) = {1}")
    @MethodSource("minimumLengthStringProvider")
    fun minimumLength(s: String, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.minimumLength(s)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "customSortString({0}, {1}) = {2}")
    @MethodSource("customStringProvider")
    fun customSortString(order: String, s: String, expected: String) {
        val actual = com.willmadison.leetcode.extensions.customSortString(order, s)
        val range = 0..minOf(order.length - 1, s.length - 1)
        assertThat(actual.substring(range)).isEqualTo(expected.substring(range))
    }

    @ParameterizedTest(name = "isIsomorphic({0}, {1}) = {2}")
    @MethodSource("isomorphicStringProvider")
    fun isIsomorphic(s: String, t: String, expected: Boolean) {
        val actual = com.willmadison.leetcode.extensions.isIsomorphic(s, t)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "findRotateSteps({0}, {1}) = {2}")
    @MethodSource("rotateStepProvider")
    fun findRotateSteps(ring: String, key: String, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.findRotateSteps(ring, key)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "wonderfulSubstrings({0}) = {1}")
    @MethodSource("wonderfulSubstringProvider")
    fun wonderfulSubstrings(word: String, expected: Long) {
        val actual = com.willmadison.leetcode.extensions.wonderfulSubstrings(word)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "exist({0}, {1}) = {2}")
    @MethodSource("crosswordProvider")
    fun exist(board: Array<CharArray>, word: String, expected: Boolean) {
        val actual = com.willmadison.leetcode.extensions.exist(board, word)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "maxDepth({0}) = {1}")
    @MethodSource("maxDepthProvider")
    fun maxDepth(s: String, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.maxDepth(s)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "makeGood({0}) = {1}")
    @MethodSource("goodStringProvider")
    fun makeGood(s: String, expected: String) {
        val actual = com.willmadison.leetcode.extensions.makeGood(s)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "minRemoveToMakeValid({0}) = {1}")
    @MethodSource("removeToMakeValidProvider")
    fun minRemoveToMakeValid(s: String, expected: String) {
        val actual = com.willmadison.leetcode.extensions.minRemoveToMakeValid(s)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "checkValidString({0}) = {1}")
    @MethodSource("wildcardValidStringProvider")
    fun checkValidString(s: String, expected: Boolean) {
        val actual = com.willmadison.leetcode.extensions.checkValidString(s)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "removeKdigits({0}, {1}) = {2}")
    @MethodSource("kdigitsProvider")
    fun removeKdigits(num: String, k: Int, expected: String) {
        val actual = com.willmadison.leetcode.extensions.removeKdigits(num, k)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "isNumber{0}) = {1}")
    @MethodSource("numberProvider")
    fun isNumber(s: String, expected: Boolean) {
        val actual = com.willmadison.leetcode.extensions.isNumber(s)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "longestIdealString({0}, {1}) = {2}")
    @MethodSource("idealStringProvider")
    fun longestIdealString(s: String, k: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.longestIdealString(s, k)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "compareVersion({0}, {1}) = {2}")
    @MethodSource("versionComparisonProvider")
    fun compareVersion(a: String, b: String, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.compareVersion(a, b)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "gcdOfStrings({0}, {1}) = {2}")
    @MethodSource("gcdStringProvider")
    fun gcdOfStrings(str1: String, str2: String, expected: String) {
        val actual = com.willmadison.leetcode.extensions.gcdOfStrings(str1, str2)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "partition({0}) = {1}")
    @MethodSource("partitioningProvider")
    fun partition(s: String, expected: List<List<String>>) {
        val actual = com.willmadison.leetcode.extensions.partition(s)
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected)
    }

    @ParameterizedTest(name = "maxScoreWords({0}, {1}, {2}) = {3}")
    @MethodSource("maxScoreWordsProvider")
    fun maxScoreWords(words: Array<String>, letters: CharArray, score: IntArray, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.maxScoreWords(words, letters, score)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "wordBreak({0}, {1}) = {2}")
    @MethodSource("wordBreakProvider")
    fun wordBreak(s: String, dictionary: List<String>, expected: List<String>) {
        val actual = com.willmadison.leetcode.extensions.wordBreak(s, dictionary)
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected)
    }

    @ParameterizedTest(name = "appendCharacters({0}, {1}) = {2}")
    @MethodSource("characterAppendProvider")
    fun appendCharacters(s: String, t: String, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.appendCharacters(s, t)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "commonChars({0}) = {1}")
    @MethodSource("commonCharWordProvider")
    fun commonChars(words: Array<String>, expected: List<String>) {
        val actual = com.willmadison.leetcode.extensions.commonChars(words)
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected)
    }

    @ParameterizedTest(name = "replaceWords({0}, {1}) = {2}")
    @MethodSource("replaceWordsProvider")
    fun replaceWords(dictionary: List<String>, sentence: String, expected: String) {
        val actual = com.willmadison.leetcode.extensions.replaceWords(dictionary, sentence)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "longestDiverseString({0}, {1}, {2}) = anyOf({3})")
    @MethodSource("longestDiverseStringProvider")
    fun longestDiverseString(a: Int, b: Int, c: Int, expected: Collection<String>) {
        val actual = com.willmadison.leetcode.extensions.longestDiverseString(a, b, c)
        assertThat(listOf(actual)).containsAnyOf(*expected.toTypedArray())
    }

    @ParameterizedTest(name = "removeSubfolders({0}) = {1}")
    @MethodSource("folderProvider")
    fun removeSubfolders(folders: Array<String>, expected: List<String>) {
        val actual = com.willmadison.leetcode.extensions.removeSubfolders(folders)
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected)
    }

    @ParameterizedTest(name = "maxVowels({0}, {1}) = {2}")
    @MethodSource("maxVowelsProvider")
    fun maxVowels(s: String, k: Int, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.maxVowels(s, k)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "makeFancyString({0}) = {1}")
    @MethodSource("fancyStringProvider")
    fun makeFancyString(s: String, expected: String) {
        val actual = com.willmadison.leetcode.extensions.makeFancyString(s)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "isCircularSentence({0}) = {1}")
    @MethodSource("circularSentenceProvider")
    fun isCircularSentence(s: String, expected: Boolean) {
        val actual = com.willmadison.leetcode.extensions.isCircularSentence(s)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "rotateString({0}, {1}) = {2}")
    @MethodSource("stringRotationProvider")
    fun rotateString(s: String, goal: String, expected: Boolean) {
        val actual = com.willmadison.leetcode.extensions.rotateString(s, goal)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "compressedString({0}) = {1}")
    @MethodSource("compressedStringProvider")
    fun compressedString(s: String, expected: String) {
        val actual = com.willmadison.leetcode.extensions.compressedString(s)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "minChanges({0}) = {1}")
    @MethodSource("minChangesProvider")
    fun minChanges(s: String, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.minChanges(s)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "isPrefixOfWord({0}, {1}) = {2}")
    @MethodSource("isPrefixOfWordProvider")
    fun minChanges(sentence: String, needle: String, expected: Int) {
        val actual = com.willmadison.leetcode.extensions.isPrefixOfWord(sentence, needle)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "areAlmostEqual({0}, {1}) = {2}")
    @MethodSource("almostEqualProvider")
    fun areAlmostEqual(s1: String, s2: String, expected: Boolean) {
        val actual = com.willmadison.leetcode.extensions.areAlmostEqual(s1, s2)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "isValid({0}) = {1}")
    @MethodSource("isValid2Provider")
    fun isValid2(s: String, expected: Boolean) {
        val actual = com.willmadison.leetcode.extensions.isValid2(s)
        assertThat(actual).isEqualTo(expected)
    }
}