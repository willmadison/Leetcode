package august

type WordDictionary struct {
	root *trieNode
}

type trieNode struct {
	children  [26]*trieNode
	endOfWord bool
}

func NewDictionary() WordDictionary {
	return WordDictionary{root: &trieNode{}}
}

func (w *WordDictionary) AddWord(word string) {
	current := w.root

	for _, c := range word {
		i := asciiOffset(c)

		if current.children[i] == nil {
			current.children[i] = &trieNode{}
		}
		current = current.children[i]
	}

	current.endOfWord = true
}

func asciiOffset(b rune) int {
	return int(b - 'a')
}

func (w *WordDictionary) Search(word string) bool {
	return w.find(w.root, word)
}

func (w *WordDictionary) find(node *trieNode, word string) bool {
	if node == nil {
		return false
	}

	current := node

	for i, c := range word {
		switch {
		case c == '.':
			return w.broadSearch(current, word[i+1:])
		default:
			offset := asciiOffset(c)
			if current.children[offset] == nil {
				return false
			}

			current = current.children[offset]
		}
	}

	return current.endOfWord
}

func (w *WordDictionary) broadSearch(node *trieNode, word string) bool {
	var found bool
	for i := 0; i < 26; i++ {
		if found = w.find(node.children[i], word); !found {
			continue
		} else {
			break
		}
	}

	return found
}
