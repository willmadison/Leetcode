package august

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestDetectCapitalUse(t *testing.T) {
	cases := []struct {
		given    string
		expected bool
	}{
		{
			"USA",
			true,
		},
		{
			"UsA",
			false,
		},
		{
			"Flag",
			true,
		},
		{
			"FlaG",
			false,
		},
		{
			"flag",
			true,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("detectCapitalUse(%v)", tc.given), func(t *testing.T) {
			actual := detectCapitalUse(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}
