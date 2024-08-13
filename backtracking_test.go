package leetcode

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestCombinationSum2(t *testing.T) {
	cases := []struct {
		given struct {
			candidates []int
			target     int
		}
		expecteed [][]int
	}{
		{
			struct {
				candidates []int
				target     int
			}{
				[]int{10, 1, 2, 7, 6, 1, 5},
				8,
			},
			[][]int{
				{1, 1, 6},
				{1, 2, 5},
				{1, 7},
				{2, 6},
			},
		},
		{
			struct {
				candidates []int
				target     int
			}{
				[]int{2, 5, 2, 1, 2},
				5,
			},
			[][]int{
				{1, 2, 2},
				{5},
			},
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("combinationSum2(%v, %v) = %v", tc.given.candidates, tc.given.target, tc.expecteed), func(t *testing.T) {
			actual := combinationSum2(tc.given.candidates, tc.given.target)
			assert.ElementsMatch(t, tc.expecteed, actual)
		})
	}

}
