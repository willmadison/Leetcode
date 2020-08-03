package april

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestStringShift(t *testing.T) {
	cases := []struct {
		given struct {
			s               string
			transformations [][]int
		}
		expected string
	}{
		{
			struct {
				s               string
				transformations [][]int
			}{
				s: "abc",
				transformations: [][]int{
					{0, 1},
					{1, 2},
				},
			},
			"cab",
		},
		{
			struct {
				s               string
				transformations [][]int
			}{
				s: "abcdefg",
				transformations: [][]int{
					{1, 1},
					{1, 1},
					{0, 2},
					{1, 3},
				},
			},
			"efgabcd",
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("stringShift(%v, %v)", tc.given.s, tc.given.transformations), func(t *testing.T) {
			actual := stringShift(tc.given.s, tc.given.transformations)
			assert.Equal(t, tc.expected, actual)
		})
	}
}
