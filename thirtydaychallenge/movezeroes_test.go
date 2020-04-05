package thirtydaychallenge

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestMoveZeroes(t *testing.T) {
	cases := []struct {
		given    []int
		expected []int
	}{
		{
			[]int{0, 1, 0, 3, 12},
			[]int{1, 3, 12, 0, 0},
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("moveZeroes(%v)", tc.given), func(t *testing.T) {
			moveZeroes(tc.given)
			assert.Equal(t, tc.expected, tc.given)
		})
	}
}
