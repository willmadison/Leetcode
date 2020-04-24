package thirtydaychallenge

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestRangeBitwiseAnd(t *testing.T) {
	cases := []struct {
		given struct {
			start, end int
		}
		expected int
	}{
		{
			struct {
				start int
				end   int
			}{5, 7},
			4,
		},
		{
			struct {
				start int
				end   int
			}{0, 1},
			0,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("rangeBitwiseAnd(%v, %v)", tc.given.start, tc.given.end), func(t *testing.T) {
			actual := rangeBitwiseAnd(tc.given.start, tc.given.end)
			assert.Equal(t, tc.expected, actual)
		})
	}
}
