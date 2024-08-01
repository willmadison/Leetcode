package leetcode

import (
	"bytes"
	"sort"
	"strconv"
	"strings"
	"unicode"
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

func isSubsequence(s, t string) bool {
	indicesByCharacter := map[rune][]int{}

	for i, c := range t {
		if _, present := indicesByCharacter[c]; !present {
			indicesByCharacter[c] = []int{}
		}

		indicesByCharacter[c] = append(indicesByCharacter[c], i)
	}

	sIndices := []int{}

	for _, c := range s {
		if indices, present := indicesByCharacter[c]; !present {
			return false
		} else {
			if len(indices) == 1 || len(sIndices) == 0 {
				sIndices = append(sIndices, indices[0])

				if len(indices) == 1 {
					delete(indicesByCharacter, c)
				}
			} else {
				var found bool
				index := -1

				for _, i := range indices {
					if i > sIndices[len(sIndices)-1] {
						index = i
						found = true
						break
					}
				}

				if !found {
					return false
				}

				sIndices = append(sIndices, index)
			}
		}
	}

	return sort.IntsAreSorted(sIndices)
}

func reverseParentheses(s string) string {
	result := s

	parenLocaleStack := NewStack[int]()

	for i, c := range s {
		switch c {
		case '(':
			parenLocaleStack.Push(i)
		case ')':
			openingLocale, _ := parenLocaleStack.Pop()
			target := result[openingLocale+1 : i]
			reversed := reverse(target)
			result = result[0:openingLocale+1] + reversed + result[i:]
		default:
		}
	}

	result = strings.ReplaceAll(result, "(", "")
	result = strings.ReplaceAll(result, ")", "")

	return result
}

func reverse(s string) string {
	var buf bytes.Buffer

	for i := len(s) - 1; i >= 0; i-- {
		buf.WriteByte(s[i])
	}

	return buf.String()
}

func countOfAtoms(formula string) string {
	var parseFormula func(string) map[string]int

	var i int
	parseFormula = func(f string) map[string]int {
		currentCountsByAtom := map[string]int{}

		for i < len(f) && f[i] != ')' {
			if f[i] == '(' {
				i++
				nestedCount := parseFormula(f)

				for atom, count := range nestedCount {
					if _, present := currentCountsByAtom[atom]; !present {
						currentCountsByAtom[atom] = 0
					}

					currentCountsByAtom[atom] += count
				}
			} else {
				var currentAtomSb strings.Builder
				currentAtomSb.WriteByte(f[i])
				i++

				for i < len(f) && unicode.IsLower(rune(f[i])) {
					currentAtomSb.WriteByte(f[i])
					i++
				}

				var currentCountSb strings.Builder

				for i < len(f) && unicode.IsDigit(rune(f[i])) {
					currentCountSb.WriteByte(f[i])
					i++
				}

				currentAtom := currentAtomSb.String()
				currentCount := 1

				if currentCountSb.Len() > 0 {
					currentCount, _ = strconv.Atoi(currentCountSb.String())
				}

				if _, present := currentCountsByAtom[currentAtom]; !present {
					currentCountsByAtom[currentAtom] = 0
				}
				currentCountsByAtom[currentAtom] += currentCount
			}
		}

		i++
		var multiplierSb strings.Builder

		for i < len(f) && unicode.IsDigit(rune(f[i])) {
			multiplierSb.WriteByte(f[i])
			i++
		}

		if multiplierSb.Len() > 0 {
			multiplier, _ := strconv.Atoi(multiplierSb.String())

			for atom := range currentCountsByAtom {
				currentCountsByAtom[atom] *= multiplier
			}
		}

		return currentCountsByAtom
	}

	atoms := []string{}

	countsByAtom := parseFormula(formula)

	for atom := range countsByAtom {
		atoms = append(atoms, atom)
	}

	sort.Slice(atoms, func(i, j int) bool {
		return atoms[i] < atoms[j]
	})

	var sb strings.Builder

	for _, atom := range atoms {
		count := countsByAtom[atom]

		sb.WriteString(atom)

		if count > 1 {
			sb.WriteString(strconv.Itoa(count))
		}
	}

	return sb.String()
}

func minimumDeletions(s string) int {
	n := len(s)
	runeStack := NewStack[rune]()

	var deletions int

	for i := 0; i < n; i++ {
		top, err := runeStack.Peek()

		if err == nil && top == 'b' && s[i] == 'a' {
			runeStack.Pop()
			deletions++
		} else {
			runeStack.Push(rune(s[i]))
		}
	}

	return deletions
}

func countSeniors(details []string) int {
	var numSeniors int

	var extractAge = func(s string) int {
		rawAge := s[11:13]
		i, _ := strconv.Atoi(rawAge)
		return i
	}

	for _, detail := range details {
		age := extractAge(detail)

		if age > 60 {
			numSeniors++
		}
	}

	return numSeniors
}
