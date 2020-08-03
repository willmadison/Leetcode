package april

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestHappyNumber(t *testing.T) {
	cases := []struct {
		given    int
		expected bool
	}{
		{
			20,
			false,
		},
		{
			23,
			true,
		},
		{
			2,
			false,
		},
		{
			1,
			true,
		},
		{
			1000,
			true,
		},
		{
			1234,
			false,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("isHappy(%v)", tc.given), func(t *testing.T) {
			actual := isHappy(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}
