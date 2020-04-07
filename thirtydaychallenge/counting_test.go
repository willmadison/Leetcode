package thirtydaychallenge

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestCountElements(t *testing.T) {
	cases := []struct {
		given    []int
		expected int
	}{
		{
			[]int{1, 2, 3},
			2,
		},
		{
			[]int{1, 1, 3, 3, 5, 5, 7, 7},
			0,
		},
		{
			[]int{1, 3, 2, 3, 5, 0},
			3,
		},
		{
			[]int{1, 1, 2, 2},
			2,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("countElements(%v)", tc.given), func(t *testing.T) {
			actual := countElements(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}

}
