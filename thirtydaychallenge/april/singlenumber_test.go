package april

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestSingleNumber(t *testing.T) {
	cases := []struct {
		given    []int
		expected int
	}{
		{
			[]int{2, 2, 1},
			1,
		},
		{
			[]int{4, 1, 2, 1, 2},
			4,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("singleNumber(%v)", tc.given), func(t *testing.T) {
			actual := singleNumber(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}
