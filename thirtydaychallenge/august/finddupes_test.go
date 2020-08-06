package august

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestFindDuplicates(t *testing.T) {
	cases := []struct {
		given    []int
		expected []int
	}{
		{
			[]int{1, 2, 2},
			[]int{2},
		},
		{
			[]int{4, 3, 2, 7, 8, 2, 3, 1},
			[]int{2, 3},
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("findDuplicates(%v)", tc.given), func(t *testing.T) {
			actual := findDuplicates(tc.given)
			assert.ElementsMatch(t, actual, tc.expected)
		})
	}
}
