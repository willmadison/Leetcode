package august

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestValidPalindrome(t *testing.T) {
	cases := []struct {
		given    string
		expected bool
	}{
		{
			"USA",
			false,
		},
		{
			"ASA",
			true,
		},
		{
			".,",
			true,
		},
		{
			"A man, a plan, a canal: Panama",
			true,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("isPalindrome(%v)", tc.given), func(t *testing.T) {
			actual := isPalindrome(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}
