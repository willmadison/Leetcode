package april

import "bytes"

type direction int

const (
	left direction = iota
	right
)

type transformation struct {
	direction direction
	magnitude int
}

type characters []rune

func (c *characters) shift(t transformation) {
	for i := 0; i < t.magnitude; i++ {
		switch t.direction {
		case left:
			c.shiftLeft()
		case right:
			c.shiftRight()
		}
	}
}

func (c *characters) shiftLeft() {
	first := (*c)[0]
	*c = (*c)[1:]
	*c = append(*c, first)
}

func (c *characters) shiftRight() {
	last := (*c)[len(*c)-1]
	old := (*c)[0 : len(*c)-1]
	*c = []rune{last}
	*c = append(*c, old...)
}

func (c characters) String() string {
	var buf bytes.Buffer

	for _, character := range c {
		buf.WriteRune(character)
	}

	return buf.String()
}

func stringShift(s string, transformations [][]int) string {
	var c characters

	for _, character := range s {
		c = append(c, character)
	}

	for _, t := range transformations {
		trans := transformation{direction(t[0]), t[1]}
		c.shift(trans)
	}

	return c.String()
}
