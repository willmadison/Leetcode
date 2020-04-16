package thirtydaychallenge

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestCheckValidString(t *testing.T) {
	cases := []struct {
		given    string
		expected bool
	}{
		{
			"()",
			true,
		},
		{
			"(*)",
			true,
		},
		{
			"(*))",
			true,
		},
		{
			"(*)))",
			false,
		},
		{
			"(*)*))",
			true,
		},
		{
			"******",
			true,
		},
		{
			"(******",
			true,
		},
		{
			"(())((())()()(*)(*()(())())())()()((()())((()))(*",
			false,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("checkValidString('%v')", tc.given), func(t *testing.T) {
			actual := checkValidString(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}
