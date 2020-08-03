package april

import "bytes"

type keyboard struct {
	bytes.Buffer
}

func (k *keyboard) backspace() {
	if k.Len() > 0 {
		k.Truncate(k.Len() - 1)
	}
}

func (k *keyboard) keystroke(r rune) {
	k.WriteRune(r)
}

func backspaceCompare(s, t string) bool {
	return keystrokes(s) == keystrokes(t)
}

func keystrokes(word string) string {
	var k keyboard

	for _, char := range word {
		switch char {
		case '#':
			k.backspace()
		default:
			k.keystroke(char)
		}
	}

	return k.String()
}
