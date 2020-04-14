package thirtydaychallenge

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestMaxProfit(t *testing.T) {
	cases := []struct {
		given    []int
		expected int
	}{
		{
			[]int{7, 1, 5, 3, 6, 4},
			7,
		},
		{
			[]int{1, 2, 3, 4, 5},
			4,
		},
		{
			[]int{7, 6, 4, 3, 1},
			0,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("maxProfit(%v)", tc.given), func(t *testing.T) {
			actual := maxProfit(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}
