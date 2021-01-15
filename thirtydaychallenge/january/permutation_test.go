package january

import (
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestCanPermutePalindrome(t *testing.T) {
	cases := []struct {
		given    string
		expected bool
	}{
		{"code", false},
		{"aab", true},
		{"carerac", true},
	}

	for _, tc := range cases {
		actual := canPermutePalindrome(tc.given)
		assert.Equal(t, tc.expected, actual)
	}
}
