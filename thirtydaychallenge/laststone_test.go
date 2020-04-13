package thirtydaychallenge

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestLastStoneWeight(t *testing.T) {
	cases := []struct {
		given    []int
		expected int
	}{
		{
			[]int{2, 7, 4, 1, 8, 1},
			1,
		},
		{
			[]int{2, 2},
			0,
		},
		{
			[]int{3, 2, 2},
			1,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("lastStoneWeight(%v)", tc.given), func(t *testing.T) {
			actual := lastStoneWeight(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}
