package leetcode

import (
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestMergeIntervals(t *testing.T) {
	cases := []struct {
		given    [][]int
		expected [][]int
	}{
		{
			given: [][]int{
				{1, 3},
				{2, 6},
				{8, 10},
				{15, 18},
			},
			expected: [][]int{
				{1, 6},
				{8, 10},
				{15, 18},
			},
		},
	}

	for _, tc := range cases {
		actual := merge(tc.given)
		assert.Equal(t, tc.expected, actual)
	}
}
