package thirtydaychallenge

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestFindMaxLength(t *testing.T) {
	cases := []struct {
		given    []int
		expected int
	}{
		{
			[]int{0, 1},
			2,
		},
		{
			[]int{0, 1, 0},
			2,
		},
		{
			[]int{0, 0, 1, 0, 0, 0, 1, 1},
			6,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("findMaxLength(%v)", tc.given), func(t *testing.T) {
			actual := findMaxLength(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}

}
