package april

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestMaxSubArray(t *testing.T) {
	cases := []struct {
		given    []int
		expected int
	}{
		{
			[]int{-2, 1, -3, 4, -1, 2, 1, -5, 4},
			6,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("maxSubArray(%v)", tc.given), func(t *testing.T) {
			actual := maxSubArray(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}
