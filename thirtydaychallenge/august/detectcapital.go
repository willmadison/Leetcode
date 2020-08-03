package august

import (
	"strings"
	"unicode"
)

func detectCapitalUse(word string) bool {
	first := rune(word[0])
	rest := word[1:]

	if unicode.IsUpper(first) {
		return rest == strings.ToLower(rest) || rest == strings.ToUpper(rest)
	}

	return rest == strings.ToLower(rest)
}
