package thirtydaychallenge

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestProductExceptSelf(t *testing.T) {
	cases := []struct {
		given    []int
		expected []int
	}{
		{
			[]int{1, 2, 3, 4},
			[]int{24, 12, 8, 6},
		},
		{
			[]int{0, 0},
			[]int{0, 0},
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("productExceptSelf(%v)", tc.given), func(t *testing.T) {
			actual := productExceptSelf(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}
