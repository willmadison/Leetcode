package leetcode

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestNumTeams(t *testing.T) {
	cases := []struct {
		given    []int
		expected int
	}{
		{
			[]int{2, 5, 3, 4, 1},
			3,
		},
		{
			[]int{2, 1, 3},
			0,
		},
		{
			[]int{1, 2, 3, 4},
			4,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("numTeams(%v) = %v", tc.given, tc.expected), func(t *testing.T) {
			actual := numTeams(tc.given)
			assert.Equal(t, actual, tc.expected)
		})
	}
}
