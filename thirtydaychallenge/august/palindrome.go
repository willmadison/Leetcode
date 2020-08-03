package august

import (
	"strings"
	"unicode"
)

func isPalindrome(s string) bool {
	given := strings.ToLower(s)

	left, right := 0, len(given)-1

	for left < right {
		leftCharacter := rune(given[left])

		for !unicode.IsDigit(leftCharacter) && !unicode.IsLetter(leftCharacter) && left < right {
			left++
			leftCharacter = rune(given[left])
		}

		rightCharacter := rune(given[right])

		for !unicode.IsDigit(rightCharacter) && !unicode.IsLetter(rightCharacter) && right > 0 {
			right--
			rightCharacter = rune(given[right])
		}

		if left < right && leftCharacter != rightCharacter {
			return false
		}

		left++
		right--
	}

	return true
}
