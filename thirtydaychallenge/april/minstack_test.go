package april

import (
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestMinStack(t *testing.T) {
	s := Constructor()
	s.Push(1)
	assert.Equal(t, 1, s.GetMin())
	s.Push(0)
	assert.Equal(t, 0, s.GetMin())
	s.Push(-1)
	s.Push(0)
	assert.Equal(t, -1, s.GetMin())
	s.Pop()
	s.Pop()
	assert.Equal(t, 0, s.GetMin())
}
