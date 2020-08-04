package august

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestIsPowerOfFour(t *testing.T) {
	cases := []struct {
		given    int
		expected bool
	}{
		{
			17,
			false,
		},
		{
			16,
			true,
		},
		{
			81,
			false,
		},
		{
			1,
			true,
		},
		{
			64,
			true,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("isPowerOfFour(%v)", tc.given), func(t *testing.T) {
			actual := isPowerOfFour(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}
