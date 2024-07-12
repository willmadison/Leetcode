package leetcode

import (
	"bytes"
	"sort"
	"strings"
)

// https://leetcode.com/problems/longest-substring-without-repeating-characters/description/
func lengthOfLongestSubstring(word string) int {
	lastIndexOf := map[byte]int{}

	currentLength, maxLength := 0, 0

	numCharacters := len(word)

	for i := 0; i < numCharacters; i++ {
		character := word[i]
		lastIndex, seen := lastIndexOf[character]

		if !seen || i-currentLength > lastIndex {
			currentLength++
		} else {
			if currentLength > maxLength {
				maxLength = currentLength
			}

			currentLength = i - lastIndex
		}

		lastIndexOf[character] = i
	}

	if currentLength > maxLength {
		maxLength = currentLength
	}

	return maxLength
}

type RuneLocale struct {
	Rune        rune
	Row, Column int
}

type Direction int

const (
	Downward = iota
	Diagonal
)

// https://leetcode.com/problems/zigzag-conversion/description/
func convert(s string, numRows int) string {
	if numRows == 1 {
		return s
	}

	var currentRow, currentColumn int

	direction := Downward

	var runeLocales []RuneLocale

	for _, r := range s {
		runeLocales = append(runeLocales, RuneLocale{Rune: r, Row: currentRow, Column: currentColumn})

		if currentRow == numRows-1 {
			direction = Diagonal
		} else if currentRow == 0 {
			direction = Downward
		}

		if direction == Downward {
			currentRow++
		} else {
			currentRow--
			currentColumn++
		}

	}

	sort.Slice(runeLocales, func(i, j int) bool {
		return runeLocales[i].Row < runeLocales[j].Row ||
			(runeLocales[i].Row == runeLocales[j].Row && runeLocales[i].Column < runeLocales[j].Column)
	})

	var buf bytes.Buffer

	for _, c := range runeLocales {
		buf.WriteRune(c.Rune)
	}

	return buf.String()
}

// https://leetcode.com/problems/regular-expression-matching/description/
func isMatch(s, pattern string) bool {
	if pattern == "" {
		return s == ""
	}

	firstMatches := s != "" && (s[0] == pattern[0] || pattern[0] == '.')

	if len(pattern) > 1 && pattern[1] == '*' {
		return isMatch(s, pattern[2:]) || (firstMatches && isMatch(s[1:], pattern))
	} else {
		return firstMatches && isMatch(s[1:], pattern[1:])
	}
}

// https://leetcode.com/problems/reverse-vowels-of-a-string/description/?envType=study-plan-v2&envId=leetcode-75
func reverseVowels(s string) string {
	vowels := []rune{}

	var buf bytes.Buffer

	for _, v := range s {
		switch v {
		case 'a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U':
			vowels = append(vowels, v)
		}
	}

	current := len(vowels) - 1

	for _, v := range s {
		switch v {
		case 'a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U':
			buf.WriteRune(vowels[current])
			current--
		default:
			buf.WriteRune(v)
		}
	}

	return buf.String()
}

// https://leetcode.com/problems/reverse-words-in-a-string/description/?envType=study-plan-v2&envId=leetcode-75

func reverseWords(s string) string {
	rawString := strings.TrimSpace(s)

	words := strings.Fields(rawString)

	var buf bytes.Buffer

	i := len(words) - 1

	for i >= 0 {
		buf.WriteString(words[i])

		if i > 0 {
			buf.WriteRune(' ')
		}

		i--
	}

	return buf.String()
}
