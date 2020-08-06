package august

import (
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestAddSearchWord(t *testing.T) {
	d := NewDictionary()
	d.AddWord("hello")
	assert.True(t, d.Search("hello"))
	assert.True(t, d.Search("...lo"))
	assert.False(t, d.Search("..lo"))
}
