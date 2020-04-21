package thirtydaychallenge

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestMinPathSum(t *testing.T) {
	cases := []struct {
		given    [][]int
		expected int
	}{
		{
			[][]int{
				{1, 3, 1},
				{1, 5, 1},
				{4, 2, 1},
			},
			7,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("minPathSum(%v)", tc.given), func(t *testing.T) {
			actual := minPathSum(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}
