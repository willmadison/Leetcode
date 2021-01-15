package thirtydaychallenge

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestCanJump(t *testing.T) {
	cases := []struct {
		given    []int
		expected bool
	}{
		{
			[]int{2, 3, 1, 1, 4},
			true,
		},
		{
			[]int{3, 2, 1, 0, 4},
			false,
		},
		{
			[]int{1},
			true,
		},
		{
			[]int{0, 2, 3},
			false,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("canJump(%v)", tc.given), func(t *testing.T) {
			actual := canJump(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}
