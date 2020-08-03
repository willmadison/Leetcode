package april

import (
	"errors"
)

type runeLocale struct {
	r      rune
	locale int
}

type runeLocaleStack struct {
	data []runeLocale
	size int
}

func (r *runeLocaleStack) Push(value runeLocale) {
	r.data = append(r.data, value)
	r.size++
}

func (r *runeLocaleStack) Pop() (runeLocale, error) {
	if r.size > 0 {
		value := r.data[r.size-1]
		r.size--
		r.data = r.data[:r.size]
		return value, nil
	}

	return runeLocale{}, errors.New("No Such Element")
}

func (r *runeLocaleStack) Peek() (runeLocale, error) {
	if r.size > 0 {
		value := r.data[r.size-1]
		return value, nil
	}

	return runeLocale{}, errors.New("No Such Element")
}

func checkValidString(s string) bool {
	wildcards := runeLocaleStack{data: []runeLocale{}}
	openers := runeLocaleStack{data: []runeLocale{}}

	for i, r := range s {
		switch r {
		case '(':
			openers.Push(runeLocale{r: r, locale: i})
		case '*':
			wildcards.Push(runeLocale{r: r, locale: i})
		default:
			_, err := openers.Peek()

			if err != nil {
				// No openers available, try and use a wildcard
				_, err := wildcards.Peek()

				if err != nil {
					// No openers or wildcards available this string is invalid.
					return false
				}

				wildcards.Pop()
			} else {
				openers.Pop()
			}
		}
	}

	for openers.size > 0 && wildcards.size > 0 {
		//As long as the wildcard is to the right of the open parenthesis
		//we're good to use it as an closing parenthesis.
		open, _ := openers.Peek()
		wildcard, _ := wildcards.Peek()

		if open.locale > wildcard.locale {
			return false
		}

		openers.Pop()
		wildcards.Pop()
	}

	return openers.size == 0
}
